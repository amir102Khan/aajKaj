package com.amir.serviceman.fragments.provider;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.amir.serviceman.R;
import com.amir.serviceman.activities.ProjectDetails;
import com.amir.serviceman.adapter.JobListForContractor;
import com.amir.serviceman.adapter.JobProviderAdapter;
import com.amir.serviceman.core.BaseFragment;
import com.amir.serviceman.databinding.FragmentSearchJobBinding;
import com.amir.serviceman.interfaces.OnAdapterItemClick;

import java.util.ArrayList;


public class   SearchJob extends BaseFragment implements OnAdapterItemClick {
    private FragmentSearchJobBinding binding;
    private JobListForContractor adapter;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_search_job, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setEmptyAdapter();
    }

    private void setEmptyAdapter() {
        adapter = new JobListForContractor( getActivity(),this);
        binding.rvJobList.setLayoutManager(new LinearLayoutManager(getActivity()));
        binding.rvJobList.setAdapter(adapter);
    }

    @Override
    public void onClick(int position, boolean data,int type) {
        if (type == 1){
       startActivity(new Intent(getActivity(), ProjectDetails.class));}
    }
}
