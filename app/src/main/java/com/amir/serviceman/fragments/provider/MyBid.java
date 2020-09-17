package com.amir.serviceman.fragments.provider;

import android.content.Context;
import android.content.Intent;
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

import com.amir.serviceman.Model.CancelProviderProjectBid;
import com.amir.serviceman.Model.ProjectForProviderModel;
import com.amir.serviceman.Model.ProviderBiddedProjectsModel;
import com.amir.serviceman.R;
import com.amir.serviceman.activities.ProjectDetails;
import com.amir.serviceman.adapter.JobListForContractor;
import com.amir.serviceman.adapter.MyBidAdapter;
import com.amir.serviceman.core.BaseFragment;
import com.amir.serviceman.databinding.FragmentMyBidBinding;
import com.amir.serviceman.databinding.FragmentSearchJobBinding;
import com.amir.serviceman.interfaces.OnAdapterItemClick;
import com.amir.serviceman.util.Dialogs;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MyBid extends BaseFragment implements OnAdapterItemClick {
    private FragmentMyBidBinding binding;
    private MyBidAdapter adapter;
    private ArrayList<ProviderBiddedProjectsModel.Datum> projects = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_my_bid, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        loader = (ConstraintLayout) binding.loader.getRoot();

        setEmptyAdapter();
        if (checkInternetConnection()){
            getMyBiddedProjects();
        }
        else {
            showToast(getString(R.string.no_internet));
        }
    }

    private void getMyBiddedProjects(){
        showLoader();
        String apiTOken;
        if (sp.getBoolean(SIGNUP)) {
            apiTOken = getUserModelFromSharedPreference(sp).getApiToken();
        } else {
            apiTOken = getLoginUserModelFromSharedPreference(sp).getApiToken();
        }

        call = api.getMyBid(apiTOken);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    Type type = new TypeToken<ProviderBiddedProjectsModel>() {
                    }.getType();
                    if (response.code() == 200) {
                        ProviderBiddedProjectsModel data = gson.fromJson(response.body().string(), type);
                        if (data.getType().equals("success")) {
                            projects.clear();
                            projects.addAll(data.getData());
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
    private void setEmptyAdapter() {

        adapter = new MyBidAdapter( getActivity(),this,projects);
        binding.rvListMyBid.setLayoutManager(new LinearLayoutManager(getActivity()));
        binding.rvListMyBid.setAdapter(adapter);
    }

    @Override
    public void onClick(int position, boolean data,int type) {
        if (type == 1) {
            startActivity(new Intent(getActivity(), ProjectDetails.class));
        }
        else if (type == 2){
            if (checkInternetConnection()){
                cancelBid(projects.get(position).getBid().getBId(), position);
            }
            else {
                showToast(getString(R.string.no_internet));
            }
        }
    }

    private void cancelBid(int bidId, final int position){
        showLoader();
        String apiTOken;
        if (sp.getBoolean(SIGNUP)) {
            apiTOken = getUserModelFromSharedPreference(sp).getApiToken();
        } else {
            apiTOken = getLoginUserModelFromSharedPreference(sp).getApiToken();
        }

        call = api.cancelProviderBid(apiTOken, bidId);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    Type type = new TypeToken<CancelProviderProjectBid>() {
                    }.getType();
                    if (response.code() == 200) {
                        CancelProviderProjectBid data = gson.fromJson(response.body().string(), type);
                        if (data.getType().equals("success")) {
                            projects.remove(position);
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
}
