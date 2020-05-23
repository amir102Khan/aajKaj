package com.amir.serviceman.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.DataBindingUtil;

import com.amir.serviceman.Model.LogoutModel;
import com.amir.serviceman.R;
import com.amir.serviceman.activities.WelcomeScreen;
import com.amir.serviceman.core.BaseFragment;
import com.amir.serviceman.databinding.FragmentProfileBinding;
import com.amir.serviceman.util.Dialogs;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Profile extends BaseFragment implements View.OnClickListener {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    private FragmentProfileBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_profile, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        loader = (ConstraintLayout) binding.loader.getRoot();
        implementListener();
    }

    private void implementListener() {
        binding.logout.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == binding.logout) {
            if (checkInternetConnection()) {
                logout();
            } else {
                showToast(getString(R.string.no_internet));
            }
        }
    }

    private void logout() {
        String apiTOken;
        if (sp.getBoolean(SIGNUP)) {
            apiTOken = getUserModelFromSharedPreference(sp).getApiToken();
        } else {
            apiTOken = getLoginUserModelFromSharedPreference(sp).getApiToken();
        }
        showLoader();
        call = api.logout(apiTOken);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    Type type = new TypeToken<LogoutModel>() {
                    }.getType();
                    if (response.code() == 200) {
                        LogoutModel data = gson.fromJson(response.body().string(), type);
                        if (data.getType().equals("success")) {
                            sp.clearData();
                            startActivity(new Intent(mContext, WelcomeScreen.class));
                            mContext.finish();
                        } else {
                            Dialogs.alertDialog(data.getMessage(), mContext);
                        }
                    } else {
                        Dialogs.alertDialog(getString(R.string.SERVER_ERROR_MSG), mContext);
                    }
                } catch (Exception ex) {
                    Dialogs.alertDialog(ex.getMessage(), mContext);
                }
                hideLoader();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                hideLoader();
                Dialogs.alertDialog(t.getMessage(), mContext);
            }
        });
    }
}
