package com.amir.serviceman.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.amir.serviceman.R;
import com.amir.serviceman.activities.Login;
import com.amir.serviceman.core.BaseActivity;

public class MainActivity extends BaseActivity {


    private Handler handler;
    private static final int TIMER = 2000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (sp.getBoolean(IS_LOGIN)){
                    startActivity(new Intent(mContext,Dashboard.class));
                }
                else {
                    startActivity(new Intent(mContext, WelcomeScreen.class));
                }
                finish();
            }
        },TIMER);
    }
}
