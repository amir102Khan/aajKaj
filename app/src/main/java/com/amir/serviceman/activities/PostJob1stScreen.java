package com.amir.serviceman.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.DataBindingUtil;

import com.amir.serviceman.Model.CreateJobModel;
import com.amir.serviceman.Model.LogoutModel;
import com.amir.serviceman.R;
import com.amir.serviceman.adapter.RadioButtonAdapter;
import com.amir.serviceman.core.BaseActivity;
import com.amir.serviceman.databinding.ActivityPostJob1stScreenBinding;
import com.amir.serviceman.interfaces.GetLocationListener;
import com.amir.serviceman.util.Common;
import com.amir.serviceman.util.Dialogs;
import com.amir.serviceman.util.LocationHelper;
import com.amir.serviceman.util.PermissionHelper;
import com.google.gson.reflect.TypeToken;
import com.llollox.androidprojects.compoundbuttongroup.CompoundButtonGroup;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PostJob1stScreen extends BaseActivity implements View.OnClickListener {
    private String latitude = "",longitude = "",conAddress = "";
    private ActivityPostJob1stScreenBinding binding;
    private RadioButtonAdapter adapter;
    private ArrayList<String> radios = new ArrayList<>();
    private String selecBType,selecJType,selecEmpType,projectName;


    private boolean isChecking = true;
    private int mCheckedId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_post_job1st_screen);
        Common.setToolbarWithBackAndTitle(mContext, "Create a job", true, R.drawable.ic_arrow_back_black_24dp);
        loader = loader = (ConstraintLayout) binding.loader.getRoot();
        clickRadioBtnToGetVal();
        checkLocationPermission();
        implementListener();
    }

    private void implementListener() {

        binding.btnNext.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == binding.btnNext) {
            startActivity(new Intent(mContext, PostJob2ndScreen.class)
                    .putExtra("projectName", projectName)
            .putExtra("businessType",selecBType)
            .putExtra("jobType",selecJType)
            .putExtra("EmpType",selecEmpType)
            .putExtra("contrAddress",conAddress)
            .putExtra("lat",latitude)
            .putExtra("lng",longitude));
          //createJobForContractor();
        }
    }

    private void clickRadioBtnToGetVal(){
        projectName = binding.edProjectName.getText().toString();
        // business type radio buttons
        binding.rdBTypeGrp.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if(i== R.id.rdIndpendent){
                    selecBType= binding.rdIndpendent.getText().toString();

                }else if(i==R.id.rdCompany){
                    selecBType= binding.rdCompany.getText().toString();

                }
                //Toast.makeText(mContext, selecBType, Toast.LENGTH_SHORT).show();
            }
        });

        // employee type radio buttons
        binding.rdETypeGrp.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if(i== R.id.rdMonthly){
                    selecEmpType = binding.rdMonthly.getText().toString();

                }else if(i==R.id.rdHourly){
                    selecEmpType = binding.rdHourly.getText().toString();

                }
                //Toast.makeText(mContext, selecEmpType, Toast.LENGTH_SHORT).show();
            }
        });

        // job type radio buttons
        binding.rdJType.setOnButtonSelectedListener(new CompoundButtonGroup.OnButtonSelectedListener() {
            @Override
            public void onButtonSelected(int position, String value, boolean isChecked) {
                selecJType = value;
                //Toast.makeText(mContext, value, Toast.LENGTH_SHORT).show();
            }
        });

    }
    private void checkLocationPermission(){
        if (PermissionHelper.checkLocationPermission(mContext)) {
            getCurrentLocation();

        } else {
            PermissionHelper.requestLocationPermission(mContext);
        }
    }

    private void getCurrentLocation(){
        LocationHelper locationHelper = new LocationHelper(mContext, new GetLocationListener() {
            @Override
            public void onLocationFound(String lat, String lng, String address) {
                Log.e("location: ", lat + " : " + lng + " : " + address);
                latitude = lat;
                longitude = lng;
                conAddress = address;
                binding.edLocation.setText(address);
            }
        });

        locationHelper.getLocation();
    }


}
