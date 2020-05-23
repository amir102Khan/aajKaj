package com.amir.serviceman.util;

import android.app.Activity;
import android.content.Context;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.amir.serviceman.R;
import com.amir.serviceman.interfaces.Constants;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import okhttp3.MediaType;
import okhttp3.RequestBody;


public class Common implements Constants {


    /**
     * get unique id of the device
     *
     * @param ctx context
     * @return device id
     */
    public static String deviceId(Context ctx) {
        return Settings.Secure.getString(ctx.getContentResolver(), Settings.Secure.ANDROID_ID);
    }

    public static RequestBody getRequestBodyOfString(String string) {
        return RequestBody.create(MediaType.parse("multipart/form-data"), string);
    }

    public static void registerFCM(final SharedPref sp, Activity context) {
        if (sp.getString(USER_PUSH_TOKEN) == null || sp.getString(USER_PUSH_TOKEN).trim().equalsIgnoreCase("")) {
           /* FirebaseInstanceId.getInstance().getInstanceId()
                    .addOnSuccessListener(context, new OnSuccessListener<InstanceIdResult>() {
                        @Override
                        public void onSuccess(InstanceIdResult instanceIdResult) {
                            Log.e("instanceIdResult: ", instanceIdResult.getToken());
                            sp.setString(USER_PUSH_TOKEN, instanceIdResult.getToken());
                        }
                    });*/
        }
    }

    public static boolean validateEditText(String text) {
        return !TextUtils.isEmpty(text) && text.trim().length() > 0;
    }

    public static boolean isValidEmail(String email) {
        return !TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    public static void setToolbarWithBackAndTitle(final Context ctx, String title, Boolean value, int backResource) {
        Toolbar toolbar = ((AppCompatActivity) ctx).findViewById(R.id.toolbar);
        ((AppCompatActivity) ctx).setSupportActionBar(toolbar);
        TextView title_tv = toolbar.findViewById(R.id.title_tv);
        ActionBar actionBar = ((AppCompatActivity) ctx).getSupportActionBar();
        if (actionBar != null) {
            if (backResource != 0){
                toolbar.setNavigationIcon(backResource);
                toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ((AppCompatActivity) ctx).onBackPressed();
                    }
                });
            }

            actionBar.setDisplayShowHomeEnabled(value);
            actionBar.setDisplayShowTitleEnabled(false);
            title_tv.setText(title);
        }
    }

    /**
     * get required date
     * @param inputFormat
     * @param outputFormat
     * @param selectedDate
     * @return date
     */
    public static String getDate(String inputFormat,String outputFormat,String selectedDate){
        SimpleDateFormat parseDateFormat = new SimpleDateFormat(inputFormat);
        Date date = null;
        try {
            date = parseDateFormat.parse(selectedDate);
        } catch (ParseException e) {

            e.printStackTrace();
        }
        SimpleDateFormat requiredDateFormat = new SimpleDateFormat(outputFormat);
        String value = requiredDateFormat.format(date);
        return  value;
    }
}
