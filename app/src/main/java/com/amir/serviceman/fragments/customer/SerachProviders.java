package com.amir.serviceman.fragments.customer;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.amir.serviceman.R;
import com.amir.serviceman.core.BaseFragment;
import com.amir.serviceman.databinding.FragmentSerachProvidersBinding;

import java.time.Year;
import java.util.ArrayList;


public class SerachProviders extends BaseFragment implements View.OnClickListener {



    private FragmentSerachProvidersBinding binding;
    private boolean filter = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_serach_providers, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        implementListner();
    }

    private void implementListner(){
        binding.imgFilter.setOnClickListener(this);
    }

    public void dialogSelectImage() {
        final Dialog dialog = new Dialog(mContext);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        if (dialog.getWindow() != null)
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setContentView(R.layout.diaolog_filter);
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);
        dialog.show();

        Button btnApply;
        TextView tvCancel;

        btnApply = dialog.findViewById(R.id.btnApply);
        tvCancel = dialog.findViewById(R.id.tvBack);

        btnApply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                filter = false;
            }
        });

        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                filter = false;
            }
        });


    }

    @Override
    public void onClick(View v) {
        if (v == binding.imgFilter){
            if (!filter){
                dialogSelectImage();
            }
        }
    }
}
