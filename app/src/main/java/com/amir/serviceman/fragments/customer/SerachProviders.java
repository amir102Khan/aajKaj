package com.amir.serviceman.fragments.customer;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.amir.serviceman.Model.GetJobProviders;
import com.amir.serviceman.Model.LogoutModel;
import com.amir.serviceman.R;
import com.amir.serviceman.activities.WelcomeScreen;
import com.amir.serviceman.adapter.JobProviderAdapter;
import com.amir.serviceman.core.BaseFragment;
import com.amir.serviceman.databinding.FragmentSerachProvidersBinding;
import com.amir.serviceman.interfaces.OnAdapterItemClick;
import com.amir.serviceman.util.Dialogs;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.time.Year;
import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SerachProviders extends BaseFragment implements View.OnClickListener , OnAdapterItemClick {

    private JobProviderAdapter adapter;
    private ArrayList<GetJobProviders.Datum> list  ;
    private FragmentSerachProvidersBinding binding;
    private boolean filter = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_serach_providers, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        implementListner();
        loader = (ConstraintLayout) binding.loader.getRoot();
        setEmptyAdapter();
        jobList();
    }

    private void implementListner(){
        binding.imgFilter.setOnClickListener(this);
    }

    public void dialogSelectImage() {
        final Dialog dialog = new Dialog(mContext);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        if (dialog.getWindow() != null)
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setContentView(R.layout.diaolog_filter);
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);
        dialog.show();

        Button btnApply;
        TextView tvCancel;

        btnApply = dialog.findViewById(R.id.btnApply);
        tvCancel = dialog.findViewById(R.id.tvBack);

        btnApply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                filter = false;
            }
        });

        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                filter = false;
            }
        });


    }
    @Override
    public void onClick(View v) {
        if (v == binding.imgFilter){
            if (!filter){
                dialogSelectImage();
            }
        }
    }


    private void jobList() {
        String apiTOken;
        if (sp.getBoolean(SIGNUP)) {
            apiTOken = getUserModelFromSharedPreference(sp).getApiToken();
        } else {
            apiTOken = getLoginUserModelFromSharedPreference(sp).getApiToken();
        }
        showLoader();
        call = api.getJobProviders(apiTOken,"sads",4);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    Type type = new TypeToken<GetJobProviders>() {
                    }.getType();
                    if (response.code() == 200) {
                        GetJobProviders data = gson.fromJson(response.body().string(), type);
                        if (data.getType().equals("success")) {
                            sp.clearData();
                              list.addAll(data.getData());
                              adapter.notifyDataSetChanged();

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

    private void setEmptyAdapter(){
        list = new ArrayList<>();
         adapter = new JobProviderAdapter(list, getActivity(),this);
        binding.rvJobProvider.setLayoutManager(new LinearLayoutManager(getActivity()));
        binding.rvJobProvider.setAdapter(adapter);
    }


    @Override
    public void onClick(int position, boolean data) {

    }
}
