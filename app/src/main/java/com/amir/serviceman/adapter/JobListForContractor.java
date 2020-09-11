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

import com.amir.serviceman.Model.ProjectForProviderModel;
import com.amir.serviceman.R;
import com.amir.serviceman.interfaces.OnAdapterItemClick;

import java.util.ArrayList;

public class JobListForContractor extends RecyclerView.Adapter<JobListForContractor.JobListHolder> {


    private Context context;
    private OnAdapterItemClick click;
    private ArrayList<ProjectForProviderModel.Datum> projects;

    public JobListForContractor(Context context, OnAdapterItemClick click,ArrayList<ProjectForProviderModel.Datum> projects) {
        this.context = context;
        this.click = click;
        this.projects = projects;
    }

    @NonNull
    @Override
    public JobListHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.project_list_adapter,parent,false);
        return new JobListHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final JobListHolder holder, final int position) {

        holder.tvProjectName.setText(projects.get(position).getProjectName());
        holder.tvAddress.setText(projects.get(position).getLocation());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                click.onClick(position,true,1);
            }
        });

        holder.btnApply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                click.onClick(position,true,2);
            }
        });
    }

    @Override
    public int getItemCount() {
        return projects.size();
    }

    class JobListHolder extends RecyclerView.ViewHolder {
        private ImageView imgProject;
        private RelativeLayout layout;
        private TextView tvAddress;
        private Button btnApply;
        private TextView tvProjectName;
        public JobListHolder(@NonNull View itemView) {
            super(itemView);
            imgProject = itemView.findViewById(R.id.projectImg);
            tvProjectName = itemView.findViewById(R.id.tvRvProjectName);
            btnApply = itemView.findViewById(R.id.btnRvApply);
            tvAddress = itemView.findViewById(R.id.tvRvLocation);
            layout = itemView.findViewById(R.id.layout_rv_list);

        }
    }
}
