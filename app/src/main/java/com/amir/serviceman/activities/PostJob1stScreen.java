package com.amir.serviceman.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.amir.serviceman.Model.CategoriesModel;
import com.amir.serviceman.Model.CreateJobModel;
import com.amir.serviceman.Model.LogoutModel;
import com.amir.serviceman.R;
import com.amir.serviceman.adapter.CategotyTypeAdapter;
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
    private ArrayList<CategoriesModel.Datum> categories = new ArrayList<>();
    private List<String> categoriesName = new ArrayList<>();
    private CategotyTypeAdapter categotyTypeAdapter ;

    private boolean isChecking = true;
    private int mCheckedId;

    private int selectedCategoryId = -1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_post_job1st_screen);
        Common.setToolbarWithBackAndTitle(mContext, "Create a job", true, R.drawable.ic_arrow_back_black_24dp);
        loader = loader = (ConstraintLayout) binding.loader.getRoot();
        clickRadioBtnToGetVal();
        checkLocationPermission();
        implementListener();

        setEmptyAddapter();
        if (checkInternetConnection()){
            getCategories();
        }
        else {
            showToast(getString(R.string.no_internet));
        }
    }

    private void setEmptyAddapter(){
        // set a GridLayoutManager with default vertical orientation and 2 number of columns
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(),2);
        binding.rvJTYpe.setLayoutManager(gridLayoutManager);
      //  binding.rvJTYpe.setLayoutManager(new LinearLayoutManager(mContext,LinearLayoutManager.VERTICAL,true));
        categotyTypeAdapter = new CategotyTypeAdapter(categories,mContext);

        adapter = new RadioButtonAdapter(categories,mContext);
        binding.rvJTYpe.setAdapter(adapter);

    }
    private void implementListener() {

        binding.btnNext.setOnClickListener(this);
    }

    private void getCategories(){
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
                            setSpinnerAdapter();
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

    private void setSpinnerAdapter(){
        for (int i = 0; i < categories.size() ; i ++){
            categoriesName.add(categories.get(i).getCategory());
            radios.add(categories.get(i).getCategory());
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View v) {
        if (v == binding.btnNext) {
            validation();
          //createJobForContractor();
        }
    }

    private void validation (){
        projectName = binding.edProjectName.getText().toString();
        conAddress = binding.edLocation.getText().toString();
        //selecJType = adapter.getSeletedPosition();
        selectedCategoryId = adapter.getSeletedCategoryId();
        if (!Common.validateEditText(projectName)){
            showToast("please enter project name");
        }
        else if (!Common.validateEditText(selecBType)){
            showToast("please select where do you require job");
        }
        else if (selectedCategoryId == -1){
            showToast("please select the job type");
        }
        else if (!Common.validateEditText(selecEmpType)){
            showToast("please select employee type");
        }
        else if (!Common.validateEditText(conAddress)){
            showToast("please select project address");
        }
        else {
            startActivity(new Intent(mContext, PostJob2ndScreen.class)
                    .putExtra(PROJECT_NAME, projectName)
                    .putExtra(BUISNES_TYPE,selecBType)
                    .putExtra(JOB_TYPE,selecJType)
                    .putExtra(EMPLOYEE_TYPE,selecEmpType)
                    .putExtra(PROJECT_ADDRESS,conAddress)
                    .putExtra(LAT,latitude)
                    .putExtra(LNG,longitude));
        }
    }

    private void clickRadioBtnToGetVal(){
        // business type radio buttons
        binding.rdBTypeGrp.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if(i== R.id.rdIndpendent){
                    selecBType= "1";

                }else if(i==R.id.rdCompany){
                    selecBType= "2";

                }
                //Toast.makeText(mContext, selecBType, Toast.LENGTH_SHORT).show();
            }
        });

        // employee type radio buttons
        binding.rdETypeGrp.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if(i== R.id.rdMonthly){
                    selecEmpType = "1";

                }else if(i==R.id.rdHourly){
                    selecEmpType = "2";

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
