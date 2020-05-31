package com.amir.serviceman.fragments.provider;

import android.content.Intent;
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
import com.amir.serviceman.adapter.UpcomingJobsAdapter;
import com.amir.serviceman.databinding.FragmentSearchJobBinding;
import com.amir.serviceman.databinding.FragmentUpcomingJobsBinding;
import com.amir.serviceman.interfaces.OnAdapterItemClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class UpcomingJobs extends Fragment implements OnAdapterItemClick {
    private FragmentUpcomingJobsBinding binding;
    private UpcomingJobsAdapter adapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_upcoming_jobs, container, false);
        return  binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setEmptyAdapter();
    }

    private void setEmptyAdapter() {
        adapter = new UpcomingJobsAdapter( getActivity(),this);
        binding.rvUpcmgJob.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,true));
        binding.rvUpcmgJob.setAdapter(adapter);
    }

    @Override
    public void onClick(int position, boolean data,int type) {
        if (type == 1){
            startActivity(new Intent(getActivity(), ProjectDetails.class));}
    }
}
