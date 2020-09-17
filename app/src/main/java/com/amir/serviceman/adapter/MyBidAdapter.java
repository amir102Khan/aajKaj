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

import com.amir.serviceman.Model.ProviderBiddedProjectsModel;
import com.amir.serviceman.R;
import com.amir.serviceman.interfaces.OnAdapterItemClick;

import java.util.ArrayList;

public class MyBidAdapter extends RecyclerView.Adapter<MyBidAdapter.MyBidHolder> {

    private Context context;
    private OnAdapterItemClick click;
    private ArrayList<ProviderBiddedProjectsModel.Datum> projects;

    public MyBidAdapter(Context context, OnAdapterItemClick click ,ArrayList<ProviderBiddedProjectsModel.Datum> projects) {
        this.context = context;
        this.click = click;
        this.projects = projects;
    }

    @NonNull
    @Override
    public MyBidAdapter.MyBidHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_bid_adapter,parent,false);
        return new MyBidAdapter.MyBidHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyBidAdapter.MyBidHolder holder, final int position) {

        holder.tvProjectName.setText(projects.get(position).getProjectName());
        holder.tvAddress.setText(projects.get(position).getLocation());
        holder.date.setText(projects.get(position).getDate());
        holder.btnApply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                click.onClick(position,true,2);
            }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                click.onClick(position,true,1);
            }
        });
    }

    @Override
    public int getItemCount() {
        return projects.size();
    }

    class MyBidHolder extends RecyclerView.ViewHolder {
        private ImageView imgProject;
        private RelativeLayout layout;
        private TextView tvAddress,date,tvProjectName;
        private Button btnApply;

        public MyBidHolder(@NonNull View itemView) {
            super(itemView);
            imgProject = itemView.findViewById(R.id.projectImgMyBid);
            tvProjectName = itemView.findViewById(R.id.tvRvProjectNameMyBid);
            btnApply = itemView.findViewById(R.id.btnRvRejectMyBid);
            tvAddress = itemView.findViewById(R.id.tvRvLocationMyBid);
            date = itemView.findViewById(R.id.tvRvDateMyBid);

        }
    }
}
