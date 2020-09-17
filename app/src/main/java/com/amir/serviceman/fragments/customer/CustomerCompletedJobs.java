package com.amir.serviceman.fragments.customer;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.amir.serviceman.Model.AcceptBidModel;
import com.amir.serviceman.R;
import com.amir.serviceman.core.BaseFragment;
import com.amir.serviceman.databinding.FragmentCustomerCompletedJobsBinding;


public class CustomerCompletedJobs extends BaseFragment {


    public CustomerCompletedJobs() {
        // Required empty public constructor
    }

    private FragmentCustomerCompletedJobsBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_customer_completed_jobs,container,false);
        return binding.getRoot();
    }
}