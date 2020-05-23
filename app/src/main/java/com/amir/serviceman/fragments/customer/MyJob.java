package com.amir.serviceman.fragments.customer;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.amir.serviceman.R;
import com.amir.serviceman.activities.PostJob1stScreen;
import com.amir.serviceman.core.BaseFragment;
import com.amir.serviceman.databinding.FragmentMyJobBinding;
import com.google.android.material.tabs.TabLayout;


public class MyJob extends BaseFragment implements View.OnClickListener {


    private FragmentMyJobBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_my_job, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setTabs();
        setSelectedTab();
        implementListener();
    }

    private void setTabs() {
        setCustomTabView("Active");
        setCustomTabView("Closed");
    }

    private void implementListener(){
        binding.btnPostNewJob.setOnClickListener(this);
    }

    /**
     * setup custom tabs
     *
     * @param text text
     */
    private void setCustomTabView(String text) {
        LinearLayout customView = (LinearLayout) LayoutInflater.from(getActivity())
                .inflate(R.layout.item_custom_tab_listings_chat, null);

        ((TextView) customView.findViewById(R.id.tvText)).setText(text);

        binding.tabLayout.addTab(binding.tabLayout.newTab().setCustomView(customView));
    }

    private void setSelectedTab(){
        binding.tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()){
                    case 0:
                        setVIsibility(View.VISIBLE);
                        break;
                    case 1:
                        setVIsibility(View.GONE);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private void setVIsibility(int view){
        binding.btnPostNewJob.setVisibility(view);
    }

    @Override
    public void onClick(View v) {
        if (v == binding.btnPostNewJob){
          startActivity(new Intent(mContext, PostJob1stScreen.class));
        }
    }
}
