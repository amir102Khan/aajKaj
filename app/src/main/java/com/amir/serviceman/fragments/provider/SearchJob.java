package com.amir.serviceman.fragments.provider;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.amir.serviceman.Model.ApplyBid;
import com.amir.serviceman.Model.ProjectForProviderModel;
import com.amir.serviceman.R;
import com.amir.serviceman.activities.ProjectDetails;
import com.amir.serviceman.adapter.JobListForContractor;
import com.amir.serviceman.core.BaseFragment;
import com.amir.serviceman.databinding.FragmentSearchJobBinding;
import com.amir.serviceman.interfaces.GetLocationListener;
import com.amir.serviceman.interfaces.OnAdapterItemClick;
import com.amir.serviceman.util.Dialogs;
import com.amir.serviceman.util.LocationHelper;
import com.amir.serviceman.util.PermissionHelper;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

import okhttp3.ResponseBody;
import pl.aprilapps.easyphotopicker.EasyImage;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SearchJob extends BaseFragment implements OnAdapterItemClick {
    private FragmentSearchJobBinding binding;
    private JobListForContractor adapter;
    private String latitude = "", longitude = "";
    private ArrayList<ProjectForProviderModel.Datum> projects = new ArrayList<>();


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_search_job, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        loader = (ConstraintLayout) binding.loader.getRoot();

        setEmptyAdapter();
        setVisibility(View.GONE, View.VISIBLE);
        checkLocationPermission();

    }

    private void setEmptyAdapter() {
        adapter = new JobListForContractor(getActivity(), this, projects);
        binding.rvJobList.setLayoutManager(new LinearLayoutManager(getActivity()));
        binding.rvJobList.setAdapter(adapter);
    }

    private void getProjects() {
        String apiTOken;
        if (sp.getBoolean(SIGNUP)) {
            apiTOken = getUserModelFromSharedPreference(sp).getApiToken();
        } else {
            apiTOken = getLoginUserModelFromSharedPreference(sp).getApiToken();
        }
        showLoader();
        latitude = "30.746444";
        longitude = "76.669731";
        call = api.getNearbyProject(apiTOken, latitude, longitude);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    Type type = new TypeToken<ProjectForProviderModel>() {
                    }.getType();
                    if (response.code() == 200) {
                        ProjectForProviderModel data = gson.fromJson(response.body().string(), type);
                        if (data.getType().equals("success")) {
                            projects.clear();
                            projects.addAll(data.getData());
                            adapter.notifyDataSetChanged();
                            if (data.getData().size() == 0) {
                                setVisibility(View.VISIBLE, View.GONE);
                            } else {
                                setVisibility(View.GONE, View.VISIBLE);
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

    @Override
    public void onClick(int position, boolean data, int type) {
        if (type == 1) {
            startActivity(new Intent(getActivity(), ProjectDetails.class));
        }
        else if (type == 2){
            dialogApplyBid(projects.get(position).getPId());
        }
    }


    private void checkLocationPermission() {
        if (PermissionHelper.checkLocationPermission(mContext)) {
            getCurrentLocation();

        } else {
            PermissionHelper.requestLocationPermission(mContext);
        }
    }

    private void setVisibility(int noItemVisibility, int rvVisibilty) {
        binding.tvNoItem.setVisibility(noItemVisibility);
        binding.rvJobList.setVisibility(rvVisibilty);
    }

    private void getCurrentLocation() {
        LocationHelper locationHelper = new LocationHelper(mContext, new GetLocationListener() {
            @Override
            public void onLocationFound(String lat, String lng, String address) {
                Log.e("location: ", lat + " : " + lng + " : " + address);
                latitude = "30.746444";
                longitude = "76.669731";
                if (checkInternetConnection()) {
                    getProjects();
                } else {
                    showToast(getString(R.string.no_internet));
                }
            }
        });

        locationHelper.getLocation();
    }

    public void dialogApplyBid(final int projectId) {
        final Dialog dialog = new Dialog(mContext);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        if (dialog.getWindow() != null)
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setContentView(R.layout.diaog_add_bid);
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);
        dialog.show();

        final EditText edAMount;
        Button btApply;

        edAMount = dialog.findViewById(R.id.edAmount);
        btApply = dialog.findViewById(R.id.btApply);


        btApply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String amount  = edAMount.getText().toString();
                applyBid(projectId,amount);
                dialog.dismiss();
            }
        });

    }


    private void applyBid(int projctID, String amount){
        if (checkInternetConnection()){
            String apiTOken;
            if (sp.getBoolean(SIGNUP)) {
                apiTOken = getUserModelFromSharedPreference(sp).getApiToken();
            } else {
                apiTOken = getLoginUserModelFromSharedPreference(sp).getApiToken();
            }
            showLoader();
            call = api.bidOnProject(apiTOken,projctID,amount);
            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    try {
                        Type type = new TypeToken<ApplyBid>() {
                        }.getType();
                        if (response.code() == 200) {
                            ApplyBid data = gson.fromJson(response.body().string(), type);
                            if (data.getType().equals("success")) {
                                showToast(data.getMessage());

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
        else {
            showToast(getString(R.string.no_internet));
        }
    }
}
