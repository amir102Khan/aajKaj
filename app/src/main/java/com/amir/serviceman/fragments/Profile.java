package com.amir.serviceman.fragments;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.databinding.DataBindingUtil;

import com.amir.serviceman.Model.LogoutModel;
import com.amir.serviceman.R;
import com.amir.serviceman.activities.WelcomeScreen;
import com.amir.serviceman.core.BaseFragment;
import com.amir.serviceman.databinding.FragmentProfileBinding;
import com.amir.serviceman.util.Dialogs;
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


public class Profile extends BaseFragment implements View.OnClickListener, AdapterView.OnItemSelectedListener {
    private ArrayList<String> experience = new ArrayList<>();
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    private FragmentProfileBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_profile, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        loader = (ConstraintLayout) binding.loader.getRoot();
        setSpinnerAdapter();
        implementListener();
    }

    private void implementListener() {

        binding.logout.setOnClickListener(this);
        binding.imgId.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == binding.logout) {
            if (checkInternetConnection()) {
                logout();
            } else {
                showToast(getString(R.string.no_internet));
            }
        }else if (v == binding.imgId){
            checkPermission();
        }
    }

    private void setSpinnerAdapter(){
        ArrayAdapter<CharSequence> arrayAdapterExp = ArrayAdapter.createFromResource(mContext,
                R.array.experience_in_years, android.R.layout.simple_spinner_item);
        ArrayAdapter<CharSequence> arrayAdapterId = ArrayAdapter.createFromResource(mContext,
                R.array.id_proof_type, android.R.layout.simple_spinner_item);
        arrayAdapterExp.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spinrExpPro.setAdapter(arrayAdapterExp);
        binding.spinrIdProof.setAdapter(arrayAdapterId);
        binding.spinrExpPro.setOnItemSelectedListener(this);
        binding.spinrIdProof.setOnItemSelectedListener(this);
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



    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        EasyImage.handleActivityResult(requestCode, resultCode, data, getActivity(), new DefaultCallback() {
            @Override
            public void onImagesPicked(@NonNull List<File> imageFiles, EasyImage.ImageSource source, int type) {
                File Image = imageFiles.get(0);
                binding.imgId.setImageURI(Uri.fromFile(Image));


            }
        });

    }

    private void logout() {
        String apiTOken;
        if (sp.getBoolean(SIGNUP)) {
            apiTOken = getUserModelFromSharedPreference(sp).getApiToken();
        } else {
            apiTOken = getLoginUserModelFromSharedPreference(sp).getApiToken();
        }
        showLoader();
        call = api.logout(apiTOken);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    Type type = new TypeToken<LogoutModel>() {
                    }.getType();
                    if (response.code() == 200) {
                        LogoutModel data = gson.fromJson(response.body().string(), type);
                        if (data.getType().equals("success")) {
                            sp.clearData();
                            startActivity(new Intent(mContext, WelcomeScreen.class));
                            mContext.finish();
                        } else {
                            Dialogs.alertDialog(data.getMessage(), mContext);
                        }
                    }
                    else if (response.code() == 403) {
                        sp.clearData();
                        startActivity(new Intent(mContext, WelcomeScreen.class));
                        mContext.finish();
                    }
                    else {
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
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if (view == binding.spinrExpPro){

        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
