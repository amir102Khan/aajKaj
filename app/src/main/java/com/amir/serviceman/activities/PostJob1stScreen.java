package com.amir.serviceman.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.databinding.DataBindingUtil;

import com.amir.serviceman.R;
import com.amir.serviceman.adapter.RadioButtonAdapter;
import com.amir.serviceman.core.BaseActivity;
import com.amir.serviceman.databinding.ActivityPostJob1stScreenBinding;
import com.amir.serviceman.util.Common;

import java.util.ArrayList;

public class PostJob1stScreen extends BaseActivity implements View.OnClickListener {

    private ActivityPostJob1stScreenBinding binding;
    private RadioButtonAdapter adapter;
    private ArrayList<String> radios = new ArrayList<>();


    private boolean isChecking = true;
    private int mCheckedId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_post_job1st_screen);
        Common.setToolbarWithBackAndTitle(mContext, "Create a job", true, R.drawable.ic_arrow_back_black_24dp);

        implementListener();
    }

    private void implementListener() {
        binding.btnNext.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == binding.btnNext) {
            startActivity(new Intent(mContext, PostJob2ndScreen.class));
        }
    }
}
