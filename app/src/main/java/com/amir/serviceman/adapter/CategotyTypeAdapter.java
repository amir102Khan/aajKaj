package com.amir.serviceman.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.amir.serviceman.Model.CategoriesModel;
import com.amir.serviceman.R;

import java.util.ArrayList;

public class CategotyTypeAdapter extends RecyclerView.Adapter<CategotyTypeAdapter.MyViewHolder> {

    private ArrayList<CategoriesModel.Datum> categories ;
    private Context context;
    public CategotyTypeAdapter(ArrayList<CategoriesModel.Datum> categories, Context context){
        this.categories = categories;
        this.context =context;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_radio,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.radioButton.setText(categories.get(position).getCategory());
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        private RadioButton radioButton;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            radioButton = itemView.findViewById(R.id.rdJYpe);
        }
    }
}
