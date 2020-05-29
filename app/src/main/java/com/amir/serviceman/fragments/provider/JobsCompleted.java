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
import com.amir.serviceman.adapter.JobsCompletedAdapter;
import com.amir.serviceman.adapter.MyBidAdapter;
import com.amir.serviceman.core.BaseFragment;
import com.amir.serviceman.databinding.FragmentJobsCompletedBinding;
import com.amir.serviceman.interfaces.OnAdapterItemClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class JobsCompleted extends BaseFragment  implements OnAdapterItemClick {
private JobsCompletedAdapter adapter;
private FragmentJobsCompletedBinding binding;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_jobs_completed, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setEmptyAdapter();
    }

    private void setEmptyAdapter() {

        adapter = new JobsCompletedAdapter( getActivity(),this);
        binding.rvListJobsCmplt.setLayoutManager(new LinearLayoutManager(getActivity()));
        binding.rvListJobsCmplt.setAdapter(adapter);
    }

    @Override
    public void onClick(int position, boolean data) {
        startActivity(new Intent(getActivity(), ProjectDetails.class));
    }
}
