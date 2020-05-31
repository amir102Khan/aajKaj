package com.amir.serviceman.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.amir.serviceman.Model.DayModel;
import com.amir.serviceman.R;
import com.amir.serviceman.interfaces.OnAdapterItemClick;

import java.util.ArrayList;

public class DayAdapter extends RecyclerView.Adapter<DayAdapter.MyViewHolder>  {

    private ArrayList<DayModel> daysList;
    private Context context;
    private OnAdapterItemClick onAdapterItemClick;

    public DayAdapter(Context context,ArrayList<DayModel> daysList,OnAdapterItemClick onAdapterItemClick){
        this.context = context;
        this.onAdapterItemClick = onAdapterItemClick;
        this.daysList = daysList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_day_layout,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        holder.tvDay.setText(daysList.get(position).getDayName());

        if (daysList.get(position).isSelected()){
            holder.tvDay.setBackground(ContextCompat.getDrawable(context,R.drawable.edittextbackground));
            holder.tvDay.setTextColor(ContextCompat.getColor(context,R.color.white));
        }
        else {

            holder.tvDay.setBackground(ContextCompat.getDrawable(context,R.drawable.ic_background));
            holder.tvDay.setTextColor(ContextCompat.getColor(context,R.color.black));
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onAdapterItemClick.onClick(position,daysList.get(position).isSelected(),1);
            }
        });
    }

    @Override
    public int getItemCount() {
        return daysList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView tvDay;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvDay = itemView.findViewById(R.id.tvDay);

        }
    }
}
