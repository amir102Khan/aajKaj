package com.amir.serviceman.fragments.customer;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.amir.serviceman.Model.CategoriesModel;
import com.amir.serviceman.Model.GetJobProviders;
import com.amir.serviceman.R;
import com.amir.serviceman.adapter.JobProviderAdapter;
import com.amir.serviceman.core.BaseFragment;
import com.amir.serviceman.databinding.FragmentSerachProvidersBinding;
import com.amir.serviceman.interfaces.OnAdapterItemClick;
import com.amir.serviceman.util.Dialogs;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.net.Inet4Address;
import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SerachProviders extends BaseFragment implements View.OnClickListener, OnAdapterItemClick {

    private JobProviderAdapter adapter;
    private ArrayList<GetJobProviders.Datum> list;
    private FragmentSerachProvidersBinding binding;
    private boolean filter = false;
    private boolean isFilterApplied = false;
    private ArrayList<CategoriesModel.Datum> categories = new ArrayList<>();
    private String search = "";
    private Integer selctedCtgId = null;
    private int selectedPosition;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_serach_providers, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        implementListner();
        loader = (ConstraintLayout) binding.loader.getRoot();
        setEmptyAdapter();
        checkInternet("",null);
        getSearchEditTextValue();
        setVisibility(View.GONE,View.VISIBLE);
    }

    private void checkInternet(String search,Integer ctgId){
        if (checkInternetConnection()) {
            jobList(search, ctgId);
        } else {
            showToast(getString(R.string.no_internet));
        }
    }

    private void implementListner() {
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

        Button btnApply,btnReset;
        TextView tvCancel;

        Spinner spinner;
        spinner = dialog.findViewById(R.id.spnCategory);
        btnApply = dialog.findViewById(R.id.btnApply);
        tvCancel = dialog.findViewById(R.id.tvBack);
        btnReset = dialog.findViewById(R.id.btnReset);

        if (checkInternetConnection()) {
            getCategories(spinner);
        }
        else {
            showToast(getString(R.string.no_internet));
            dialog.dismiss();
        }
        btnApply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                filter = false;
                isFilterApplied = true;
                checkInternet(search,selctedCtgId);
            }
        });

        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                filter = false;
                isFilterApplied = false;
            }
        });

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                checkInternet("",null);
                filter = false;
                isFilterApplied = false;
            }
        });


    }

    private void getSearchEditTextValue(){
        binding.edSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                search = s.toString();
                if (call != null){
                    call.cancel();
                }
                checkInternet(search,selctedCtgId);


            }
        });
    }
    @Override
    public void onClick(View v) {
        if (v == binding.imgFilter) {
            if (!filter) {
                dialogSelectImage();
            }
        }
    }


    private void jobList(String search, Integer categoryId) {
        String apiTOken;
        if (sp.getBoolean(SIGNUP)) {
            apiTOken = getUserModelFromSharedPreference(sp).getApiToken();
        } else {
            apiTOken = getLoginUserModelFromSharedPreference(sp).getApiToken();
        }
        showLoader();
        call = api.getJobProviders(apiTOken, search, categoryId);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    Type type = new TypeToken<GetJobProviders>() {
                    }.getType();
                    if (response.code() == 200) {
                        GetJobProviders data = gson.fromJson(response.body().string(), type);
                        if (data.getType().equals("success")) {
                            list.clear();
                            list.addAll(data.getData());
                            adapter.notifyDataSetChanged();
                            if (data.getData().size() == 0){
                                setVisibility(View.VISIBLE,View.GONE);
                            }
                            else {
                                setVisibility(View.GONE,View.VISIBLE);
                            }

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

    private void setEmptyAdapter() {
        list = new ArrayList<>();
        adapter = new JobProviderAdapter(list, getActivity(), this);
        binding.rvJobProvider.setLayoutManager(new LinearLayoutManager(getActivity()));
        binding.rvJobProvider.setAdapter(adapter);
    }

    private void getCategories(final Spinner spinner){
        showLoader();
        call = api.getCategories();
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    Type type = new TypeToken<CategoriesModel>(){}.getType();
                    if (response.code() == 200){
                        CategoriesModel data = gson.fromJson(response.body().string(),type);
                        if (data.getType().equals("success")){
                            categories.clear();
                            categories.addAll(data.getData());
                            setSpinnerAdapter(spinner);
                        }
                        else {
                            Dialogs.alertDialog(data.getMessage(),mContext);
                        }
                    }
                    else {
                        Dialogs.alertDialog(getString(R.string.SERVER_ERROR_MSG),mContext);
                    }
                }
                catch (Exception ex){
                    Dialogs.alertDialog(ex.getMessage(),mContext);
                }
                hideLoader();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                hideLoader();
                Dialogs.alertDialog(t.getMessage(),mContext);

            }
        });
    }

    private void setSpinnerAdapter(Spinner spinner){
        ArrayList<String> ctg = new ArrayList<>();
        for (int i =0; i < categories.size() ; i++){
            ctg.add(categories.get(i).getCategory());
        }
        ArrayAdapter arrayAdapter = new ArrayAdapter(mContext, android.R.layout.simple_spinner_item, ctg);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
       spinner.setAdapter(arrayAdapter);

       if (isFilterApplied){
           spinner.setSelection(selectedPosition);
       }

       spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
           @Override
           public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
               selctedCtgId = categories.get(position).getCId();
               selectedPosition = position;
           }

           @Override
           public void onNothingSelected(AdapterView<?> parent) {

           }
       });

    }

    @Override
    public void onClick(int position, boolean data,int type) {

    }

    private void setVisibility(int noItemVisibility,int rvVisibilty){
        binding.tvNoItem.setVisibility(noItemVisibility);
        binding.rvJobProvider.setVisibility(rvVisibilty);
    }
}
