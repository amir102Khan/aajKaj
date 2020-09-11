package com.amir.serviceman.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.amir.serviceman.R;

import java.util.ArrayList;

public class RadioButtonAdapter extends RecyclerView.Adapter<RadioButtonAdapter.MyViewHolder> {

    private ArrayList<String> radios;
    private Context context;
    private String checkedPosition = "";
    private RadioButton lastCheckedRB = null;


    public RadioButtonAdapter(ArrayList<String> radios, Context context) {
        this.radios = radios;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_radio_button, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        holder.radioButton.setText(radios.get(position));

/*
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.imgTick.setVisibility(View.VISIBLE);
                if (checkedPosition != position) {
                    notifyItemChanged(checkedPosition);
                    checkedPosition = position;
                }

                // onItemClickListener.onItemClick(view,position);
            }
        });*/

        /*holder.radioButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (checkedPosition != position) {
                    notifyItemChanged(checkedPosition);
                    checkedPosition = position;
                }
            }
        });
        if (checkedPosition == -1) {
            // holder.imgTick.setVisibility(View.GONE);
            holder.radioButton.setChecked(false);
        } else {
            if (checkedPosition == position) {
                holder.radioButton.setChecked(true);
                // holder.imgTick.setVisibility(View.VISIBLE);
            } else {
                holder.radioButton.setChecked(false);
                //  holder.imgTick.setVisibility(View.GONE);
            }
        }*/

        holder.radioButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                RadioButton checked_rb = (RadioButton) compoundButton;
                if (lastCheckedRB != null) {
                    lastCheckedRB.setChecked(false);
                }

                checkedPosition = radios.get(position);
                //store the clicked radiobutton
                lastCheckedRB = checked_rb;
            }
        });


    }

    @Override
    public int getItemCount() {
        return radios.size();
    }

    public String getSeletedPosition(){
        return checkedPosition;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        private RadioButton radioButton;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            radioButton = itemView.findViewById(R.id.radio);
        }
    }
}
