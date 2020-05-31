package com.amir.serviceman.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.GridLayoutManager;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.GridLayout;
import android.widget.TimePicker;
import android.widget.Toast;

import com.amir.serviceman.Model.CreateJobModel;
import com.amir.serviceman.Model.DayModel;
import com.amir.serviceman.R;
import com.amir.serviceman.adapter.DayAdapter;
import com.amir.serviceman.core.BaseActivity;
import com.amir.serviceman.databinding.ActivityPostJob2ndScreenBinding;
import com.amir.serviceman.interfaces.OnAdapterItemClick;
import com.amir.serviceman.util.Common;
import com.amir.serviceman.util.Dialogs;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class PostJob2ndScreen extends BaseActivity implements View.OnClickListener, OnAdapterItemClick {

    private ActivityPostJob2ndScreenBinding binding;
    private DayAdapter dayAdapter;
    private ArrayList<DayModel> dayModels = new ArrayList<>();
    private ArrayList<String> days = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_post_job2nd_screen);
        Common.setToolbarWithBackAndTitle(mContext,"Create a job",true,R.drawable.ic_arrow_back_black_24dp);
        loader = loader = (ConstraintLayout) binding.loader.getRoot();
        implementListener();
        setDayAdapter();
    }


    private void setDayAdapter(){
        dayModels.add(new DayModel("Monday",false));
        dayModels.add(new DayModel("Tuesday",false));
        dayModels.add(new DayModel("Wednesday",false));
        dayModels.add(new DayModel("Thursday",false));
        dayModels.add(new DayModel("Friday",false));
        dayModels.add(new DayModel("Saturday",false));
        dayModels.add(new DayModel("Sunday",false));

        GridLayoutManager gridLayoutManager = new GridLayoutManager(mContext,3);
        binding.rvDay.setLayoutManager(gridLayoutManager);

        dayAdapter = new DayAdapter(mContext,dayModels,this);
        binding.rvDay.setAdapter(dayAdapter);
        
    }
    private void implementListener(){
        binding.tvFrom.setOnClickListener(this);
        binding.tvTo.setOnClickListener(this);
        binding.btnPostJob.setOnClickListener(this);
    }

    private  void getValFromPost1(){
        String lat = getIntent().getStringExtra("lat");
        String lng = getIntent().getStringExtra("lng");
        String address= getIntent().getStringExtra("contrAddress");
        String jType =  getIntent().getStringExtra("jobType");
        String empType = getIntent().getStringExtra("empType");
        String bType = getIntent().getStringExtra("businessType");
        String projectName = getIntent().getStringExtra("projectName");
        String startWork = binding.tvFrom.getText().toString();
        String endWork = binding.tvTo.getText().toString();
        String salaryHr = binding.edHourly.getText().toString();
        String salaryMonth = binding.edMonthly.getText().toString();
        String val = " ";
        if (binding.switchImmediatelyStart.isChecked()){
             val = String.valueOf(true);
        }else{
            val = String.valueOf(false);
        }
    createJobForContractor(projectName,bType,jType,empType,address,lat,lng,startWork,endWork,salaryHr,salaryMonth,val,days);

    }

    @Override
    public void onClick(View v) {
       if (v == binding.tvFrom){
           openTimePicker(true);
       }
       else if (v == binding.tvTo){
           openTimePicker(false);
       }
       else if (v == binding.btnPostJob){
           getValFromPost1();
       }
    }

    private void openTimePicker( final boolean isFromTime) {
        TimePickerDialog mTimePicker;
        mTimePicker = new TimePickerDialog(mContext, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                String hour = selectedHour < 10 ? ("0" + selectedHour) : (selectedHour + "");
                String min = selectedMinute < 10 ? ("0" + selectedMinute) : (selectedMinute + "");

                String generatedTime = "";
                SimpleDateFormat inputFormat = new SimpleDateFormat("HH:mm");
                SimpleDateFormat outputFormat = new SimpleDateFormat("hh:mm aa");

                try {
                    Date date = inputFormat.parse(hour + ":" + min);
                    generatedTime = inputFormat.format(date);
                    if (!isFromTime) {
                        if (Common.validateEditText(binding.tvFrom.getText().toString())) {
                            // Date fromDate = outputFormat.parse(data.get(position).getFromtime());
                            Date fromDate = inputFormat.parse(binding.tvFrom.getText().toString());
                            Date toDate = inputFormat.parse(""+hour+ ":" + min);
                            if (fromDate.after(toDate)) {
                                binding.tvTo.setText("");
                                Dialogs.alertDialog(getString(R.string.from_time_more_than_to_time), mContext);
                                return;
                            }
                        } else {
                            Dialogs.alertDialog(getString(R.string.select_from_time_first), mContext);
                            return;
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                if (isFromTime){
                    binding.tvFrom.setText(generatedTime);
                }
                else {
                    binding.tvTo.setText(generatedTime);
                }

            }
        }, 0, 0, true);
        mTimePicker.setTitle(isFromTime ? getString(R.string.from) : getString(R.string.to));

        mTimePicker.show();
    }

    @Override
    public void onClick(int position, boolean data,int type) {
        if (data){
            dayModels.get(position).setSelected(false);
        }
        else {
            dayModels.get(position).setSelected(true);
            String selcDay= dayModels.get(position).getDayName();
            days.add(selcDay);
        }

        dayAdapter.notifyDataSetChanged();
    }


     private void createJobForContractor(String name,String bt,String jt,String et,
                                         String loc, String lat,
                                         String lng,String from,String to,
                                         String hrSal,String monthSal,
                                         String immi_start,ArrayList<String> days ) {
     String apiTOken;
     if (sp.getBoolean(SIGNUP)) {
     apiTOken = getUserModelFromSharedPreference(sp).getApiToken();
     } else {
     apiTOken = getLoginUserModelFromSharedPreference(sp).getApiToken();
     }
     showLoader();
     call = api.createJob(apiTOken,name,bt,jt,et,loc,lat,lng,from,to,hrSal,monthSal,immi_start,days);
     call.enqueue(new Callback<ResponseBody>() {
    @Override
    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
    try {
    Type type = new TypeToken<CreateJobModel>() {
    }.getType();
    if (response.code() == 200) {
    CreateJobModel data = gson.fromJson(response.body().string(), type);
    if (data.getType().equals("success")) {
        startActivity(new Intent(mContext,Dashboard.class));
        Toast.makeText(mContext, "successfully submitted", Toast.LENGTH_SHORT).show();
    mContext.finish();
    } else {
    Dialogs.alertDialog(data.getMessage(), mContext);

    }
    } else {
    Dialogs.alertDialog(getString(R.string.SERVER_ERROR_MSG), mContext);
    }
    } catch (Exception ex) {
    Dialogs.alertDialog(ex.getMessage(), mContext);
        Log.d("just",ex.getMessage());

    }
    hideLoader();
    }

    @Override
    public void onFailure(Call<ResponseBody> call, Throwable t) {
    hideLoader();
    Dialogs.alertDialog(t.getMessage(), mContext);
    Log.d("just1",t.getMessage());
    }
    });
     }
}
