package com.amir.serviceman.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;

import com.amir.serviceman.R;
import com.amir.serviceman.databinding.ActivityProjectDetailsBinding;
import com.amir.serviceman.util.Common;

public class ProjectDetails extends AppCompatActivity {
    private ActivityProjectDetailsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_project_details);
       // Common.setToolbarWithBackAndTitle(ProjectDetails.this,"Project Details",true,R.drawable.ic_arrow_back_black_24dp);
    }
}
