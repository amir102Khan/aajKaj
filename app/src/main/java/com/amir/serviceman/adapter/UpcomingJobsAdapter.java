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
import com.amir.serviceman.interfaces.OnAdapterItemClick;

public class UpcomingJobsAdapter extends RecyclerView.Adapter< UpcomingJobsAdapter.UpcomingJobHolder> {

    private Context context;
    private OnAdapterItemClick click;

    public  UpcomingJobsAdapter(Context context, OnAdapterItemClick click) {
        this.context = context;
        this.click = click;
    }

    @NonNull
    @Override
    public UpcomingJobsAdapter.UpcomingJobHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.upcoming_jobs_adapter,parent,false);
        return new UpcomingJobsAdapter.UpcomingJobHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final UpcomingJobsAdapter.UpcomingJobHolder holder, final int position) {

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                click.onClick(position,true,1);
            }
        });
    }

    @Override
    public int getItemCount() {
        return 8;
    }

    class UpcomingJobHolder extends RecyclerView.ViewHolder {
        private ImageView imgProject;
        private RelativeLayout layout;
        private TextView tvAddress,tvProjectName,contact,date;

        public UpcomingJobHolder(@NonNull View itemView) {
            super(itemView);
            imgProject = itemView.findViewById(R.id.projectImageUpcmng);
            tvProjectName = itemView.findViewById(R.id.tvRvProjectNameUpcmg);
            contact = itemView.findViewById(R.id.tvRvContactUpcmg);
            tvAddress = itemView.findViewById(R.id.tvRvLocationUpcmg);
            layout = itemView.findViewById(R.id.layout_rv_list);
            date = itemView.findViewById(R.id.tvRvDateUpcmg);

        }
    }
}
