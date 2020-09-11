package com.amir.serviceman.activities;

import android.Manifest;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.GridLayoutManager;

import com.amir.serviceman.Model.CreateJobModel;
import com.amir.serviceman.Model.DayModel;
import com.amir.serviceman.R;
import com.amir.serviceman.adapter.DayAdapter;
import com.amir.serviceman.core.BaseActivity;
import com.amir.serviceman.databinding.ActivityPostJob2ndScreenBinding;
import com.amir.serviceman.interfaces.OnAdapterItemClick;
import com.amir.serviceman.util.Common;
import com.amir.serviceman.util.Dialogs;
import com.amir.serviceman.util.PermissionHelper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.FileOutputStream;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import pl.aprilapps.easyphotopicker.DefaultCallback;
import pl.aprilapps.easyphotopicker.EasyImage;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class PostJob2ndScreen extends BaseActivity implements View.OnClickListener, OnAdapterItemClick {

    private ActivityPostJob2ndScreenBinding binding;
    private DayAdapter dayAdapter;
    private ArrayList<DayModel> dayModels = new ArrayList<>();
    private ArrayList<String> days = new ArrayList<>();
    private ArrayList<File> images = new ArrayList<>();
    private String selectedDate = "";
    private DatePickerDialog pickerDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_post_job2nd_screen);
        Common.setToolbarWithBackAndTitle(mContext, "Create a job", true, R.drawable.ic_arrow_back_black_24dp);
        loader = loader = (ConstraintLayout) binding.loader.getRoot();
        implementListener();

    }




    private void implementListener() {
        binding.tvFrom.setOnClickListener(this);
        binding.tvTo.setOnClickListener(this);
        binding.btnPostJob.setOnClickListener(this);
        binding.imgProduct.setOnClickListener(this);
        binding.edDAte.setOnClickListener(this);
    }

    private void getValFromPost1() {
        String lat = getIntent().getStringExtra(LAT);
        String lng = getIntent().getStringExtra(LNG);
        String address = getIntent().getStringExtra(PROJECT_ADDRESS);
        String jType = getIntent().getStringExtra(JOB_TYPE);
        String empType = getIntent().getStringExtra(EMPLOYEE_TYPE);
        String bType = getIntent().getStringExtra(BUISNES_TYPE);
        String projectName = getIntent().getStringExtra(PROJECT_NAME);
        String startWork = binding.tvFrom.getText().toString();
        String endWork = binding.tvTo.getText().toString();
        String salaryHr = binding.edHourly.getText().toString();
        String salaryMonth = binding.edMonthly.getText().toString();
        String val = " ";
        if (binding.switchImmediatelyStart.isChecked()) {
            val = String.valueOf(true);
        } else {
            val = String.valueOf(false);
        }

        if (!Common.validateEditText(startWork)){
            showToast("please select start time");
        }
        else if (!Common.validateEditText(endWork)){
            showToast("please select end time");
        }
        else if (!Common.validateEditText(salaryHr)){
            showToast("please mention hourly salary");
        }
        else if (!Common.validateEditText(salaryMonth)){
            showToast("please mention monthly salary");
        }
        else if (!Common.validateEditText(selectedDate)){
            showToast("please select date");
        }
        else {
            createJobForContractor(projectName, bType, jType, empType, address, lat, lng, startWork, endWork, salaryHr, salaryMonth, val, days);
        }

    }

    @Override
    public void onClick(View v) {
        if (v == binding.tvFrom) {
            openTimePicker(true);
        } else if (v == binding.tvTo) {
            openTimePicker(false);
        } else if (v == binding.btnPostJob) {
            getValFromPost1();
        }
        else if (v == binding.imgProduct){
            checkPermission();
        }
        else if (v == binding.edDAte){
            getDate();
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
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        EasyImage.handleActivityResult(requestCode, resultCode, data, this, new DefaultCallback() {
            @Override
            public void onImagesPicked(@NonNull List<File> imageFiles, EasyImage.ImageSource source, int type) {
                binding.imgProduct.setImageURI(Uri.fromFile(imageFiles.get(0)));
                images.clear();
                images.add(imageFiles.get(0));
            }

            @Override
            public void onCanceled(EasyImage.ImageSource source, int type) {
                super.onCanceled(source, type);
            }

            @Override
            public void onImagePickerError(Exception e, EasyImage.ImageSource source, int type) {
                super.onImagePickerError(e, source, type);
            }
        });
    }

    private void getDate(){
        final Calendar cldr = Calendar.getInstance();
        int day = cldr.get(Calendar.DAY_OF_MONTH);
        int month = cldr.get(Calendar.MONTH);
        int year = cldr.get(Calendar.YEAR);
        pickerDialog = new DatePickerDialog(mContext, R.style.DialogTheme, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                binding.edDAte.setText(day + "/" + (month + 1) + "/" + year);
                selectedDate = year + "-" + (month + 1) + "-" + day;
            }
        },year,month,day);
        pickerDialog.getDatePicker().setMinDate(cldr.getTimeInMillis());
        pickerDialog.show();
    }

    private void openTimePicker(final boolean isFromTime) {
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
                            Date toDate = inputFormat.parse("" + hour + ":" + min);
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

                if (isFromTime) {
                    binding.tvFrom.setText(generatedTime);
                } else {
                    binding.tvTo.setText(generatedTime);
                }

            }
        }, 0, 0, true);
        mTimePicker.setTitle(isFromTime ? getString(R.string.from) : getString(R.string.to));

        mTimePicker.show();
    }

    @Override
    public void onClick(int position, boolean data, int type) {
        if (data) {
            dayModels.get(position).setSelected(false);
        } else {
            dayModels.get(position).setSelected(true);
            String selcDay = dayModels.get(position).getDayName();
            days.add(selcDay);
        }

        dayAdapter.notifyDataSetChanged();
    }


    private void createJobForContractor(String name, String bt, String jt, String et,
                                        String loc, String lat,
                                        String lng, String from, String to,
                                        String hrSal, String monthSal,
                                        String immi_start, ArrayList<String> days) {

        String workStartTime = selectedDate + " " + from + ":00";

        String workEndTime = selectedDate + " " + to + ":00";
        MultipartBody.Part[] parts = new MultipartBody.Part[images.size()];

        for (int i = 0;i < images.size();i ++){
            File imageFile = new File(images.get(i).getPath());
            try {
                Bitmap bitmap = BitmapFactory.decodeFile(imageFile.getPath());
                bitmap.compress(Bitmap.CompressFormat.JPEG, 50, new FileOutputStream(imageFile));
            } catch (Throwable t) {
                Log.e("ERROR", "Error compressing file." + t.toString());
                t.printStackTrace();
            }
            RequestBody requestImageFile = RequestBody.create(MediaType.parse("multipart/form-data"),imageFile);
            parts[i]=MultipartBody.Part.createFormData("image[]",imageFile.getName(),requestImageFile);
        }


        String apiTOken;
        if (sp.getBoolean(SIGNUP)) {
            apiTOken = getUserModelFromSharedPreference(sp).getApiToken();
        } else {
            apiTOken = getLoginUserModelFromSharedPreference(sp).getApiToken();
        }
        showLoader();
        call = api.createJob(apiTOken,
                Common.getRequestBodyOfString(name) ,
               Common.getRequestBodyOfString(bt),
               Common.getRequestBodyOfString(jt) ,
               Common.getRequestBodyOfString(et),
                Common.getRequestBodyOfString(loc),
                Common.getRequestBodyOfString(lat),
                Common.getRequestBodyOfString(lng),
                Common.getRequestBodyOfString(workStartTime),
                Common.getRequestBodyOfString(workEndTime),
                Common.getRequestBodyOfString(hrSal),
                Common.getRequestBodyOfString(monthSal),
                Common.getRequestBodyOfString(immi_start),
                Common.getRequestBodyOfString(selectedDate),
                parts);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    Type type = new TypeToken<CreateJobModel>() {
                    }.getType();
                    if (response.code() == 200) {
                       Gson gson1 = new GsonBuilder().setLenient().create();

                        CreateJobModel data = gson1.fromJson(response.body().string(), type);
                        if (data.getType().equals("success")) {
                           // startActivity(new Intent(mContext, Dashboard.class));
                            Toast.makeText(mContext, "successfully submitted", Toast.LENGTH_SHORT).show();
                            //mContext.finish();
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
                Log.d("just1", t.getMessage());
            }
        });
    }
}
