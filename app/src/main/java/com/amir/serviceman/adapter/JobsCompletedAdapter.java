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

import com.amir.serviceman.R;
import com.amir.serviceman.fragments.provider.JobsCompleted;
import com.amir.serviceman.interfaces.OnAdapterItemClick;



public class JobsCompletedAdapter extends RecyclerView.Adapter<JobsCompletedAdapter.JobHolder> {

    private Context context;
    private OnAdapterItemClick click;

    public JobsCompletedAdapter(Context context, OnAdapterItemClick click) {
        this.context = context;
        this.click = click;
    }

    @NonNull
    @Override
    public JobsCompletedAdapter.JobHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.jobs_complete_adapter,parent,false);
        return new JobsCompletedAdapter.JobHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final JobsCompletedAdapter.JobHolder holder, final int position) {

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                click.onClick(position,true);
            }
        });
    }

    @Override
    public int getItemCount() {
        return 8;
    }

    class JobHolder extends RecyclerView.ViewHolder {
        private ImageView imgProject;
        private RelativeLayout layout;
        private TextView tvAddress, date, tvProjectName,time;


        public JobHolder(@NonNull View itemView) {
            super(itemView);
            imgProject = itemView.findViewById(R.id.projectImageJobCmplt);
            tvProjectName = itemView.findViewById(R.id.tvRvProjectNameJobCmplt);
            date = itemView.findViewById(R.id.tvTimeJobCmplt);
            tvAddress = itemView.findViewById(R.id.tvRvLocationJobCmplt);
            date = itemView.findViewById(R.id.tvRvDatejobCmplt);

        }
    }
}
