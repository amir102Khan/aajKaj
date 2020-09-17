package com.amir.serviceman.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.amir.serviceman.Model.CustomerProjectsList;
import com.amir.serviceman.R;
import com.amir.serviceman.interfaces.OnAdapterItemClick;

import java.util.ArrayList;

public class CustomerProjectsAdapter extends RecyclerView.Adapter<CustomerProjectsAdapter.MyViewHolder>{

    private ArrayList<CustomerProjectsList.Datum> projectList;
    private Context context;
    private OnAdapterItemClick onAdapterItemClick;
    public CustomerProjectsAdapter(ArrayList<CustomerProjectsList.Datum> projectList, Context context, OnAdapterItemClick onAdapterItemClick){
        this.projectList = projectList;
        this.context = context;
        this.onAdapterItemClick = onAdapterItemClick;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.customer_project_list_adapter,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        holder.tvProjectName.setText(projectList.get(position).getProjectName());
        holder.tvAddress.setText(projectList.get(position).getLocation());
        if (projectList.get(position).getDate() != null){
            holder.tvProjectDate.setText("Date : " + projectList.get(position).getDate());
        }
        holder.tvNoOfBids.setText("No. of bids : " + projectList.get(position).getBids().size());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onAdapterItemClick.onClick(position,true,1);
            }
        });
    }

    @Override
    public int getItemCount() {
        return projectList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        private ImageView imgProject;
        private RelativeLayout layout;
        private TextView tvAddress;
        private TextView tvProjectName;
        private TextView tvProjectDate;
        private TextView tvNoOfBids;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imgProject = itemView.findViewById(R.id.projectImg);
            tvProjectName = itemView.findViewById(R.id.tvRvProjectName);
            tvAddress = itemView.findViewById(R.id.tvRvLocation);
            layout = itemView.findViewById(R.id.layout_rv_list);
            tvProjectDate = itemView.findViewById(R.id.tvProjectDate);
            tvNoOfBids = itemView.findViewById(R.id.tvNoOfBids);

        }
    }
}
