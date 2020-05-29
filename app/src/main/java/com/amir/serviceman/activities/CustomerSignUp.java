package com.amir.serviceman.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.amir.serviceman.Model.CategoriesModel;
import com.amir.serviceman.Model.SignUpUserModel;
import com.amir.serviceman.R;
import com.amir.serviceman.core.BaseActivity;
import com.amir.serviceman.databinding.ActivityCustomerSignUpBinding;
import com.amir.serviceman.interfaces.GetLocationListener;
import com.amir.serviceman.util.Common;
import com.amir.serviceman.util.Dialogs;
import com.amir.serviceman.util.LocationHelper;
import com.amir.serviceman.util.PermissionHelper;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import pl.aprilapps.easyphotopicker.DefaultCallback;
import pl.aprilapps.easyphotopicker.EasyImage;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CustomerSignUp extends BaseActivity  implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    private ActivityCustomerSignUpBinding binding;
    private String latitude = "",longitude = "";
    private ArrayList<CategoriesModel.Datum> categories = new ArrayList<>();
    private String type;
    private String selctedCtgId;
    private File profileImage;
    private File imgIdProof;
    private boolean isProfile = false;
    private boolean isIdProof = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_customer_sign_up);
        Common.setToolbarWithBackAndTitle(mContext,getString(R.string.create_an_account),true,R.drawable.ic_arrow_back_black_24dp);
        decorateText();
        loader =  (ConstraintLayout) binding.loader.getRoot();
        implementListener();
        getData();
        checkLocationPermission();
        if (checkInternetConnection()){
            getCategories();
        }
        else {
            showToast(getString(R.string.no_internet));
        }
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
            }
        });

        locationHelper.getLocation();
    }

    private void getData(){
         type = getIntent().getStringExtra("type");
        if (type.equals(PROVIDER)){
           setVisibility(View.VISIBLE);
        }
        else {
            setVisibility(View.GONE);
        }
    }

    private void setVisibility(int view){
        binding.imgId.setVisibility(view);
        binding.tvIdProof.setVisibility(view);
        binding.tvSelectCtg.setVisibility(view);
        binding.categoryLayout.setVisibility(view);
    }

    private void implementListener(){
        binding.btnSignUp.setOnClickListener(this);
        binding.profileImage.setOnClickListener(this);
        binding.imgId.setOnClickListener(this);
    }

    private void decorateText() {
        SpannableString ss = new SpannableString(getString(R.string.already_have_an_account_sign_in));
        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(@NonNull View widget) {
                onBackPressed();
            }

            @Override
            public void updateDrawState(@NonNull TextPaint ds) {
                super.updateDrawState(ds);
                ds.setUnderlineText(false);
            }
        };

        ss.setSpan(clickableSpan, 25, ss.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        ss.setSpan(new ForegroundColorSpan(ContextCompat.getColor(mContext, R.color.lightGreen)), 25,
                ss.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        binding.tvAlreadyHaveAccount.setText(ss);
        binding.tvAlreadyHaveAccount.setMovementMethod(LinkMovementMethod.getInstance());
        binding.tvAlreadyHaveAccount.setHighlightColor(Color.TRANSPARENT);
    }


    @Override
    public void onClick(View v) {
        if (v == binding.btnSignUp){
            validation();
        }
        else if (v == binding.profileImage){
            isProfile = false;
            isIdProof = false;
            if (!isProfile){
                isProfile = true;
                isIdProof = false;
                checkPermission();
            }
        }
        else if (v == binding.imgId){
            isProfile = false;
            isIdProof = false;
            if (!isIdProof){
                isIdProof = true;
                isProfile = false;
                checkPermission();
            }
        }
    }

    private void checkPermission(){
        if (PermissionHelper.checkPermissionCG(mContext)){
            EasyImage.openCamera(mContext, 0);
        }
        else {
            PermissionHelper.requestPermissionCG(mContext);
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == PERMISSION_REQUEST_CODE_CG) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                EasyImage.openCamera(mContext, 0);
            }
            else {
                if (!ActivityCompat.shouldShowRequestPermissionRationale(mContext, Manifest.permission.CAMERA) ||
                        !ActivityCompat.shouldShowRequestPermissionRationale(mContext, Manifest.permission.READ_EXTERNAL_STORAGE) ||
                        !ActivityCompat.shouldShowRequestPermissionRationale(mContext, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                    Dialogs.alertDialogDeny(getString(R.string.camera), mContext);
                } else {
                    final AlertDialog alert = Dialogs.alertDialogWithTwoButtons(
                            getString(R.string.camera),
                            getString(R.string.proceed),
                            getString(R.string.exit),
                            mContext
                    );
                    alert.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            alert.dismiss();
                            PermissionHelper.requestPermissionCG(mContext);
                        }
                    });
                    alert.getButton(AlertDialog.BUTTON_NEGATIVE).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            alert.dismiss();
                        }
                    });
                }
            }
        }
        else if (requestCode == PERMISSION_REQUEST_CODE_LOCATION) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED
                    && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                getCurrentLocation();
            }
            else {
                if (!ActivityCompat.shouldShowRequestPermissionRationale(mContext, Manifest.permission.ACCESS_COARSE_LOCATION) ||
                        !ActivityCompat.shouldShowRequestPermissionRationale(mContext, Manifest.permission.ACCESS_FINE_LOCATION)) {
                    Dialogs.alertDialogDeny(getString(R.string.location_permission_error), mContext);
                }
                else {
                    final AlertDialog alert = Dialogs.alertDialogWithTwoButtons(
                            getString(R.string.location),
                            getString(R.string.proceed),
                            getString(R.string.exit),
                            mContext
                    );
                    alert.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            alert.dismiss();
                            PermissionHelper.requestLocationPermission(mContext);
                        }
                    });
                    alert.getButton(AlertDialog.BUTTON_NEGATIVE).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            alert.dismiss();
                            mContext.finish();
                        }
                    });
                }
            }
        }
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
                            setAdapter();
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


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        EasyImage.handleActivityResult(requestCode, resultCode, data, this, new DefaultCallback() {
            @Override
            public void onImagesPicked(@NonNull List<File> imageFiles, EasyImage.ImageSource source, int type) {
                if (isProfile){
                    profileImage = imageFiles.get(0);
                    binding.profileImage.setImageURI(Uri.fromFile(profileImage));
                    isProfile = false;
                    isIdProof = false;
                }
                else if (isIdProof){
                    imgIdProof = imageFiles.get(0);
                    binding.imgId.setImageURI(Uri.fromFile(imgIdProof));
                    isProfile = false;
                    isIdProof = false;
                }

            }

            @Override
            public void onCanceled(EasyImage.ImageSource source, int type) {
                super.onCanceled(source, type);
                isIdProof = false;
                isProfile = false;
            }

            @Override
            public void onImagePickerError(Exception e, EasyImage.ImageSource source, int type) {
                super.onImagePickerError(e, source, type);

                isIdProof = false;
                isProfile = false;
            }


        });

    }
    private void setAdapter(){
        ArrayList<String> ctg = new ArrayList<>();
        for (int i =0; i < categories.size() ; i++){
            ctg.add(categories.get(i).getCategory());
        }
        ArrayAdapter arrayAdapter = new ArrayAdapter(mContext, android.R.layout.simple_spinner_item, ctg);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spnCategory.setAdapter(arrayAdapter);

        binding.spnCategory.setOnItemSelectedListener(this);
    }

    private void validation(){
        String fNAme = binding.edtFirstName.getText().toString();
        String lName = binding.edtLastName.getText().toString();
        String phone = binding.edtPhone.getText().toString();

        if (!Common.validateEditText(fNAme)){
            showSnackBar(binding.getRoot(),"First name is empty");
        }
        else if (!Common.validateEditText(lName)){
            showSnackBar(binding.getRoot(),"Last name is empty");
        }
        else if (!Common.validateEditText(phone)){
            showSnackBar(binding.getRoot(),"Phone number is empty");
        }
        else if (phone.length() < 10){
            showSnackBar(binding.getRoot(),"Please enter 10 digit phone number");
        }
        else if (profileImage == null){
            showSnackBar(binding.getRoot(),"Please select profile image");
        }
        else if (type.equals(PROVIDER)){
            if (imgIdProof == null){
                showSnackBar(binding.getRoot(),"Please provide your id proof");
            }
            else {

                SignUpUserModel signUpUserModel = new SignUpUserModel(fNAme,
                        lName,phone,selctedCtgId,profileImage,imgIdProof,latitude,longitude);

                startActivity(new Intent(mContext,PhoneVerification.class)
                        .putExtra("userData",signUpUserModel)
                        .putExtra("type",type)
                        .putExtra(IS_REGISTER,true)
                        .putExtra("phone",phone));
            }
        }
        else {
            String id = "ss";
            SignUpUserModel signUpUserModel = new SignUpUserModel(fNAme,
                    lName,phone,selctedCtgId,profileImage,imgIdProof,latitude,longitude);

            startActivity(new Intent(mContext,PhoneVerification.class)
            .putExtra("userData",signUpUserModel)
            .putExtra("type",type)
            .putExtra(IS_REGISTER,true)
            .putExtra("phone",phone));
        }

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        selctedCtgId = String.valueOf(categories.get(position).getCId());
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
