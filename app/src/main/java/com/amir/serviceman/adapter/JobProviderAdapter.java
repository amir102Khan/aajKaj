package com.amir.serviceman.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.amir.serviceman.Model.DayModel;
import com.amir.serviceman.Model.GetJobProviders;
import com.amir.serviceman.R;
import com.amir.serviceman.interfaces.OnAdapterItemClick;
import com.bumptech.glide.Glide;

import java.util.ArrayList;


public class JobProviderAdapter extends RecyclerView.Adapter<JobProviderAdapter.JobViewHolder> {

    private ArrayList<GetJobProviders.Datum> providersList;
    private Context context;
    private OnAdapterItemClick onAdapterItemClick;

    public JobProviderAdapter(ArrayList<GetJobProviders.Datum> providersList, Context context, OnAdapterItemClick onAdapterItemClick) {
        this.providersList = providersList;
        this.context = context;
        this.onAdapterItemClick = onAdapterItemClick;
    }

    @NonNull
    @Override
    public JobViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.job_provider_adapter,parent,false);
        return new JobViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull JobViewHolder holder, int position) {

        holder.tvName.setText(providersList.get(position).getName());
        holder.tvPhoneNumber.setText(providersList.get(position).getPhone());
        String cat = String.valueOf(providersList.get(position).getCategory());
        holder.tvCat.setText(cat);
        String imgUrl = providersList.get(position).getAvatar();
        Glide.with(context)
                .load(imgUrl)
                .centerCrop()
                .placeholder(R.drawable.ic_dummy_user)
                .into(holder.imgUser);


    }

    @Override
    public int getItemCount() {
        return providersList.size();
    }

    class JobViewHolder extends RecyclerView.ViewHolder {
        private ImageView imgUser;
        private TextView tvName;
        private TextView tvCat;
        private TextView tvPhoneNumber;
         public JobViewHolder(@NonNull View itemView) {
             super(itemView);
             imgUser = itemView.findViewById(R.id.profileImageRv);
             tvName = itemView.findViewById(R.id.tvRvName);
             tvCat= itemView.findViewById(R.id.tvRvCtg);
             tvPhoneNumber = itemView.findViewById(R.id.tvRvPhnNum);

         }
     }
}
