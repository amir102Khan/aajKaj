package com.amir.serviceman.core;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.amir.serviceman.Model.Data;
import com.amir.serviceman.Model.LoginUserData;
import com.amir.serviceman.interfaces.Constants;
import com.amir.serviceman.interfaces.RetrofitInterface;
import com.amir.serviceman.util.Common;
import com.amir.serviceman.util.SharedPref;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Retrofit;

public class BaseFragment extends Fragment implements Constants {

    public static Activity mContext;
    public String TAG = "Error";
    public LinearLayoutManager layoutManager;
    public SharedPref sp;
    public Call<ResponseBody> call = null;
    public Gson gson;
    public RetrofitInterface api;
    public ConstraintLayout loader;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initializeVariable();
    }


    private void initializeVariable() {
        mContext = getActivity();
        layoutManager = new LinearLayoutManager(getContext());
        sp = new SharedPref(mContext);
        gson = new GsonBuilder().setLenient().create();
        api = retrofitCall().create(RetrofitInterface.class);
    }

    public void showToast(String message) {
        Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show();
    }

    public void hideKeyboard() {
        InputMethodManager inputMethodManager = (InputMethodManager) mContext.getSystemService(Activity.INPUT_METHOD_SERVICE);
        if (inputMethodManager != null && mContext.getCurrentFocus() != null)
            inputMethodManager.hideSoftInputFromWindow(mContext.getCurrentFocus().getWindowToken(), 0);
    }

    public void showSnackBar(View v, String message) {
        Snackbar.make(v, message, Snackbar.LENGTH_SHORT).show();
    }

    public boolean checkInternetConnection() {
        ConnectivityManager conMgr = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        return conMgr != null && (conMgr.getActiveNetworkInfo() != null && conMgr.getActiveNetworkInfo().isAvailable() && conMgr.getActiveNetworkInfo().isConnected());
    }

    public void showLoader() {
        loader.setVisibility(View.VISIBLE);
    }

    public void hideLoader() {
        loader.setVisibility(View.GONE);
    }

    public Retrofit retrofitCall() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.readTimeout(120, TimeUnit.SECONDS);
        builder.connectTimeout(120, TimeUnit.SECONDS);
        OkHttpClient okHttpClient = builder
                .build();
        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(okHttpClient)
                .build();
    }

    public static Data getUserModelFromSharedPreference(SharedPref sp) {
        String userString = sp.getString(USER_DATA);
        if (Common.validateEditText(userString)) {
            Gson gson = new Gson();

            Type baseType = new TypeToken<Data>() {
            }.getType();
            return gson.fromJson(userString, baseType);
        } else
            return new Data();
    }

    public static LoginUserData getLoginUserModelFromSharedPreference(SharedPref sp) {
        String userString = sp.getString(USER_DATA);
        if (Common.validateEditText(userString)) {
            Gson gson = new Gson();

            Type baseType = new TypeToken<LoginUserData>() {
            }.getType();
            return gson.fromJson(userString, baseType);
        } else
            return new LoginUserData();
    }
}
