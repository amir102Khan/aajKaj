package com.amir.serviceman.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.amir.serviceman.R;
import com.amir.serviceman.core.BaseActivity;
import com.amir.serviceman.databinding.ActivityWelcomeScreenBinding;

public class WelcomeScreen extends BaseActivity implements View.OnClickListener {

    private ActivityWelcomeScreenBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_welcome_screen);
        binding.btnGetJob.setOnClickListener(this);
        binding.btnHire.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        String type = "";
        if (v == binding.btnGetJob){
            type = PROVIDER;
        }
        else if (v == binding.btnHire){
            type = CUSTOMER;
        }

        startActivity(new Intent(mContext,Login.class)
        .putExtra("type",type));
    }
}
