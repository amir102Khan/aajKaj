package com.amir.serviceman.activities;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.amir.serviceman.Model.AcceptBidModel;
import com.amir.serviceman.Model.CancelProviderProjectBid;
import com.amir.serviceman.Model.ProjectDetailCustomerModel;
import com.amir.serviceman.R;
import com.amir.serviceman.adapter.ProjectBidAdapter;
import com.amir.serviceman.adapter.ProjectUpcomingBidAdapter;
import com.amir.serviceman.core.BaseActivity;
import com.amir.serviceman.databinding.ActivityCustomerProjectDetailsBinding;
import com.amir.serviceman.interfaces.OnAdapterItemClick;
import com.amir.serviceman.util.Common;
import com.amir.serviceman.util.Dialogs;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CustomerProjectDetails extends BaseActivity implements OnAdapterItemClick {

    private ActivityCustomerProjectDetailsBinding binding;
    private int projectId;
    private ProjectBidAdapter bidAdapter;
    private ArrayList<ProjectDetailCustomerModel.Bid> bids = new ArrayList<>();
    private ProjectUpcomingBidAdapter projectUpcomingBidAdapter ;
    private String from;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_customer_project_details);
        Common.setToolbarWithBackAndTitle(mContext, "Project Details", true, R.drawable.ic_arrow_back_black_24dp);
        loader = (ConstraintLayout) binding.loader.getRoot();

        projectId = getIntent().getIntExtra("projectId", -1);
        from = getIntent().getStringExtra(FROM);
        setEmptyAdapter();

        if (checkInternetConnection()) {
            getProjectDetails();
        } else {
            showToast(getString(R.string.no_internet));
        }

    }

    private void setEmptyAdapter() {
        binding.rv.setLayoutManager(new LinearLayoutManager(mContext));
        bidAdapter = new ProjectBidAdapter(mContext, bids, this);
        projectUpcomingBidAdapter = new ProjectUpcomingBidAdapter(mContext,bids,this);
        if (from.equals(MY_JOB)){
            binding.rv.setAdapter(bidAdapter);
        }
        else if (from.equals(UPCOMING_JOB)){
            binding.rv.setAdapter(projectUpcomingBidAdapter);
        }
    }

    @Override
    public void onClick(int position, boolean data, int type) {
        if (type == 1){
            acceptBid(bids.get(position).getBId(), position);
        }
        else if (type == 3){
            cancelBid(bids.get(position).getBId() , position);
        }
    }


    private void acceptBid(int bidId, final int position){
        showLoader();
        String apiTOken;
        if (sp.getBoolean(SIGNUP)) {
            apiTOken = getUserModelFromSharedPreference(sp).getApiToken();
        } else {
            apiTOken = getLoginUserModelFromSharedPreference(sp).getApiToken();
        }

        call = api.acceptBid(apiTOken,bidId);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    Type type = new TypeToken<AcceptBidModel>() {
                    }.getType();
                    if (response.code() == 200) {
                        Gson gson1 = new GsonBuilder().setLenient().create();

                        AcceptBidModel data = gson1.fromJson(response.body().string(), type);
                        if (data.getType().equals("success")) {
                            if (from.equals(MY_JOB)){
                                bids.get(position).setBidAccepted(1);
                                bidAdapter.notifyDataSetChanged();
                            }
                        } else {
                            Dialogs.alertDialog(data.getMessage(), mContext);

                        }
                    } else {
                        Dialogs.alertDialog(getString(R.string.SERVER_ERROR_MSG), mContext);
                    }
                } catch (Exception ex) {
                    Dialogs.alertDialog(ex.getMessage(), mContext);
                    Log.d("just", ex.getMessage());

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

    private void cancelBid(int bidId , final int position){
        showLoader();
        String apiTOken;
        if (sp.getBoolean(SIGNUP)) {
            apiTOken = getUserModelFromSharedPreference(sp).getApiToken();
        } else {
            apiTOken = getLoginUserModelFromSharedPreference(sp).getApiToken();
        }

        call = api.cancelProviderBidByCustomer(apiTOken, bidId);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    Type type = new TypeToken<CancelProviderProjectBid>() {
                    }.getType();
                    if (response.code() == 200) {
                        CancelProviderProjectBid data = gson.fromJson(response.body().string(), type);
                        if (data.getType().equals("success")) {
                            if (from.equals(UPCOMING_JOB)){
                                bids.get(position).setBidAccepted(0);
                                projectUpcomingBidAdapter.notifyDataSetChanged();
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

    private void getProjectDetails() {
        showLoader();
        String apiTOken;
        if (sp.getBoolean(SIGNUP)) {
            apiTOken = getUserModelFromSharedPreference(sp).getApiToken();
        } else {
            apiTOken = getLoginUserModelFromSharedPreference(sp).getApiToken();
        }

        call = api.customerProjectDetails(apiTOken, projectId);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    Type type = new TypeToken<ProjectDetailCustomerModel>() {
                    }.getType();
                    if (response.code() == 200) {
                        Gson gson1 = new GsonBuilder().setLenient().create();

                        ProjectDetailCustomerModel data = gson1.fromJson(response.body().string(), type);
                        if (data.getType().equals("success")) {
                            setData(data.getData());
                        } else {
                            Dialogs.alertDialog(data.getMessage(), mContext);

                        }
                    } else {
                        Dialogs.alertDialog(getString(R.string.SERVER_ERROR_MSG), mContext);
                    }
                } catch (Exception ex) {
                    Dialogs.alertDialog(ex.getMessage(), mContext);
                    Log.d("just", ex.getMessage());

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

    private void setData(ProjectDetailCustomerModel.Data data) {
        if (data.getOtp() != null) {
            binding.tvOtp.setVisibility(View.VISIBLE);
            binding.tvOtp.setText("OTP : " + data.getOtp());
        } else {
            binding.tvOtp.setVisibility(View.GONE);
        }

        binding.tvCustomerName.setText(data.getProjectName());

        binding.tvCstmrAddress.setText(data.getLocation());

        binding.tvCstmrTime.setText(data.getDate());

        binding.tvCstmrJobtype.setText(data.getJobType());

        binding.tvCstmrDays.setText(data.getDate());

        binding.tvCstmrSalary.setText("Hourly -Rs : " + data.getSalaryHourly() + " &  Monthly -Rs : " + data.getSalaryMonthly());

        binding.tvBidsNo.setText(data.getTotalbids() + " bids on this project");

        if (data.getBids().size() > 0) {
            bids.clear();
            bids.addAll(data.getBids());
            if (from.equals(MY_JOB)){
                bidAdapter.notifyDataSetChanged();
            }
            else if (from.equals(UPCOMING_JOB)){
                projectUpcomingBidAdapter.notifyDataSetChanged();
            }
        }
    }
}