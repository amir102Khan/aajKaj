package com.amir.serviceman.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.DataBindingUtil;

import com.amir.serviceman.Model.LoginModel;
import com.amir.serviceman.Model.RegisterModel;
import com.amir.serviceman.Model.SignUpUserModel;
import com.amir.serviceman.R;
import com.amir.serviceman.core.BaseActivity;
import com.amir.serviceman.databinding.ActivityPhoneVerificationBinding;
import com.amir.serviceman.util.Common;
import com.amir.serviceman.util.Dialogs;
import com.google.gson.reflect.TypeToken;

import java.io.FileOutputStream;
import java.lang.reflect.Type;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PhoneVerification extends BaseActivity implements View.OnClickListener, TextWatcher {

    private ActivityPhoneVerificationBinding binding;
    private int[] otp = new int[]{-1, -1, -1, -1, -1, -1};
    private String verificationId;
    //    private FirebaseAuth firebaseAuth;
    private SignUpUserModel signUpUserModel;
    private boolean isRegister;
    private String uType;
    private String phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_phone_verification);
        Common.setToolbarWithBackAndTitle(mContext, "", true, R.drawable.ic_arrow_back_black_24dp);
        loader = (ConstraintLayout) binding.loader.getRoot();
        /*firebaseAuth = FirebaseAuth.getInstance();
         */
        getData();
        implementListener();
    }

    private void getData() {
        if (getIntent().getSerializableExtra("userData")!= null){
            signUpUserModel = (SignUpUserModel) getIntent().getSerializableExtra("userData");
        }

        if (getIntent().getStringExtra("type") != null){
            uType = getIntent().getStringExtra("type");
        }
        if (getIntent().getStringExtra("phone") != null){
            phone = getIntent().getStringExtra("phone");
        }

        isRegister = getIntent().getBooleanExtra(IS_REGISTER, false);
    }

    private void implementListener() {
        binding.btnVerify.setOnClickListener(this);


        binding.edOtp1.requestFocus();
        binding.edOtp1.addTextChangedListener(this);
        binding.edOtp2.addTextChangedListener(this);
        binding.edOtp3.addTextChangedListener(this);
        binding.edOtp4.addTextChangedListener(this);
        binding.edOtp5.addTextChangedListener(this);
        binding.edOtp6.addTextChangedListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == binding.btnVerify) {

            validateData();
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        if (s.toString().hashCode() == binding.edOtp1.getText().toString().hashCode()) {
            binding.edOtp2.requestFocus();
        } else if (s.toString().hashCode() == binding.edOtp2.getText().toString().hashCode()) {
            binding.edOtp3.requestFocus();
        } else if (s.toString().hashCode() == binding.edOtp3.getText().toString().hashCode()) {
            binding.edOtp4.requestFocus();
        } else if (s.toString().hashCode() == binding.edOtp4.getText().toString().hashCode()) {
            binding.edOtp5.requestFocus();
        } else if (s.toString().hashCode() == binding.edOtp5.getText().toString().hashCode()) {
            binding.edOtp6.requestFocus();
        } else if (s.toString().hashCode() == binding.edOtp6.getText().toString().hashCode()) {
            hideKeyboard();
        }
    }

    private void validateData() {
        try {
            otp[0] = Integer.parseInt(binding.edOtp1.getText().toString());
            otp[1] = Integer.parseInt(binding.edOtp2.getText().toString());
            otp[2] = Integer.parseInt(binding.edOtp3.getText().toString());
            otp[3] = Integer.parseInt(binding.edOtp4.getText().toString());
            otp[4] = Integer.parseInt(binding.edOtp5.getText().toString());
            otp[5] = Integer.parseInt(binding.edOtp6.getText().toString());
        } catch (Exception e) {
            e.printStackTrace();
            otp = new int[]{-1, -1, -1, -1, -1, -1};
        }

        String otpString = "";
        for (int value : otp) {
            if (value == -1) {
                showToast(getString(R.string.invalidOtp));
                return;
            } else {
                otpString = otpString + value;
            }
        }

        if (checkInternetConnection()) {
            if (otpString.equals("123456")) {
               /* startActivity(new Intent(mContext, CustomerDashboard.class));
                finishAffinity();*/
               if (isRegister){
                   signUp();
               }
               else {
                login();
               }

            } else {
                showToast("OTP is not valid");
            }

            // verifyVerificationCode(otpString);

        } else {
            showToast(getString(R.string.no_internet));
        }
    }


    private void verifyVerificationCode(String otp) {
        //creating the credential
//        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, otp);

        //signing the user
        // signInWithPhoneAuthCredential(credential);
    }


   /* private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        showLoader();
        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(mContext, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            //verification successful we will start the profile activity

                            signup();
                        } else {

                            //verification unsuccessful.. display an error message

                            String message = "Somthing is wrong, we will fix it soon...";

                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                message = "Invalid code entered...";
                            }
                            showSnackBar(binding.getRoot(), message);
                        }

                        hideLoader();
                    }
                });

    }*/
/*
    private void sendVerificationCode(String mobile) {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                "+91" + mobile,
                60,
                TimeUnit.SECONDS,
                TaskExecutors.MAIN_THREAD,
                mCallbacks);
    }

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        @Override
        public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
            //Getting the code sent by SMS
            String code = phoneAuthCredential.getSmsCode();

            //sometime the code is not detected automatically
            //in this case the code will be null
            //so user has to manually enter the code

        }

        @Override
        public void onVerificationFailed(FirebaseException e) {

            showToast(e.getMessage());
        }

        @Override
        public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);
            verificationId = s;

        }
    };*/


    private void login() {
        showLoader();
        call = api.login(phone,Common.deviceId(mContext),"",ANDROID_DEVICE_TYPE);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    Type type = new TypeToken<LoginModel>(){}.getType();
                    if (response.code() == 200){
                        LoginModel data = gson.fromJson(response.body().string(),type);
                        if (data.getType().equals("success")){
                            sp.setBoolean(IS_LOGIN, true);
                            sp.setString(USER_DATA, gson.toJson(data.getData()));
                            sp.setString(USER_TYPE, uType);
                            sp.setBoolean(SIGNUP,false);
                            startActivity(new Intent(mContext, Dashboard.class));
                            finishAffinity();
                        }
                        else {
                            Dialogs.alertDialog(data.getMessage(), mContext);
                        }
                    }
                    else {
                        Dialogs.alertDialog(getString(R.string.SERVER_ERROR_MSG), mContext);
                    }
                }
                catch (Exception ex){
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

    private void signUp() {
        MultipartBody.Part profile_image = null;
        MultipartBody.Part id_Proof = null;
        if (signUpUserModel.getProfileImage() != null) {
            try {
                Bitmap bitmap = BitmapFactory.decodeFile(signUpUserModel.getProfileImage().getPath());
                bitmap.compress(Bitmap.CompressFormat.JPEG, 50, new FileOutputStream(signUpUserModel.getProfileImage()));
            } catch (Throwable t) {
                Log.e("ERROR", "Error compressing file." + t.toString());
                t.printStackTrace();
            }
            RequestBody photo_req = RequestBody.create(MediaType.parse("multipart/form-data"), signUpUserModel.getProfileImage());
            profile_image = MultipartBody.Part.createFormData("profile_image", signUpUserModel.getProfileImage().getName(), photo_req);
        }

        if (signUpUserModel.getProfileId() != null) {
            try {
                Bitmap bitmap = BitmapFactory.decodeFile(signUpUserModel.getProfileId().getPath());
                bitmap.compress(Bitmap.CompressFormat.JPEG, 50, new FileOutputStream(signUpUserModel.getProfileId()));
            } catch (Throwable t) {
                Log.e("ERROR", "Error compressing file." + t.toString());
                t.printStackTrace();
            }
            RequestBody photo_req = RequestBody.create(MediaType.parse("multipart/form-data"), signUpUserModel.getProfileId());
            id_Proof = MultipartBody.Part.createFormData("idproof", signUpUserModel.getProfileId().getName(), photo_req);
        }

        String role;
        String cId;
        if (uType.equals(PROVIDER)) {
            role = "2";
            cId = signUpUserModel.getCategoryId();
        } else {
            role = "1";
            cId = "";
        }


        showLoader();
        call = api.registerUser(Common.getRequestBodyOfString(signUpUserModel.getFirstName()),
                Common.getRequestBodyOfString(signUpUserModel.getLastName()),
                Common.getRequestBodyOfString(signUpUserModel.getPhone()),
                Common.getRequestBodyOfString(signUpUserModel.getLat()),
                Common.getRequestBodyOfString(signUpUserModel.getLng()),
                Common.getRequestBodyOfString(cId),
                Common.getRequestBodyOfString(ANDROID_DEVICE_TYPE),
                Common.getRequestBodyOfString(Common.deviceId(mContext)),
                Common.getRequestBodyOfString(""),
                Common.getRequestBodyOfString(role),
                id_Proof,
                profile_image);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    Type type = new TypeToken<RegisterModel>() {
                    }.getType();
                    if (response.code() == 200) {
                        RegisterModel data = gson.fromJson(response.body().string(), type);
                        if (data.getType().equals("success")) {
                            sp.setBoolean(IS_LOGIN, true);
                            sp.setString(USER_DATA, gson.toJson(data.getData()));
                            sp.setString(USER_TYPE, uType);
                            sp.setBoolean(SIGNUP,true);
                            startActivity(new Intent(mContext, Dashboard.class));
                            finishAffinity();
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
