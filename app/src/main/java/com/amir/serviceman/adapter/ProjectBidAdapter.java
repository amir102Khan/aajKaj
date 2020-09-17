package com.amir.serviceman.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.amir.serviceman.Model.ProjectDetailCustomerModel;
import com.amir.serviceman.R;
import com.amir.serviceman.interfaces.OnAdapterItemClick;

import java.util.ArrayList;

public class ProjectBidAdapter extends RecyclerView.Adapter<ProjectBidAdapter.MyViewHolder>{

    private ArrayList<ProjectDetailCustomerModel.Bid> bids;
    private Context context;
    private OnAdapterItemClick onAdapterItemClick;
    public ProjectBidAdapter(Context context,ArrayList<ProjectDetailCustomerModel.Bid> bids , OnAdapterItemClick onAdapterItemClick ){
        this.context = context;
        this.bids = bids;
        this.onAdapterItemClick = onAdapterItemClick;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.projects_bid_adapter,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.tvNAme.setText(bids.get(position).getContractorName());
        holder.tvAmount.setText("Rs : " + bids.get(position).getAmount());
        if (bids.get(position).getBidAccepted() == 1){
            holder.tvHire.setTextColor(ContextCompat.getColor(context,R.color.red));
            holder.tvHire.setText("Hired");
        }
        else {
            holder.tvHire.setTextColor(ContextCompat.getColor(context,R.color.taxyYellow));

            holder.tvHire.setText("Hire");
        }
        holder.ratingBar.setRating(bids.get(position).getTotalreview());
        if (position == bids.size() - 1){
            holder.view.setVisibility(View.GONE);
        }
        else {
            holder.view.setVisibility(View.VISIBLE);
        }

        if (holder.tvHire.getText().equals("Hire")){
            onAdapterItemClick.onClick(position,true,1);
        }
    }

    @Override
    public int getItemCount() {
        return bids.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView tvNAme;
        private TextView tvAmount;
        private TextView tvHire;
        private RatingBar ratingBar;
        private View view;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNAme = itemView.findViewById(R.id.tvName);
            tvAmount = itemView.findViewById(R.id.tvBidAmount);
            tvHire = itemView.findViewById(R.id.tvHire);
            ratingBar = itemView.findViewById(R.id.pRating);
            view = itemView.findViewById(R.id.viewB);
        }
    }
}
