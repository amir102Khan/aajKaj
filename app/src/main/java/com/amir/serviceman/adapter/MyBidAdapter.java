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

public class MyBidAdapter extends RecyclerView.Adapter<MyBidAdapter.MyBidHolder> {

    private Context context;
    private OnAdapterItemClick click;

    public MyBidAdapter(Context context, OnAdapterItemClick click) {
        this.context = context;
        this.click = click;
    }

    @NonNull
    @Override
    public MyBidAdapter.MyBidHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_bid_adapter,parent,false);
        return new MyBidAdapter.MyBidHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyBidAdapter.MyBidHolder holder, final int position) {

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
