package com.amir.serviceman.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.GridLayoutManager;

import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.GridLayout;
import android.widget.TimePicker;

import com.amir.serviceman.Model.DayModel;
import com.amir.serviceman.R;
import com.amir.serviceman.adapter.DayAdapter;
import com.amir.serviceman.core.BaseActivity;
import com.amir.serviceman.databinding.ActivityPostJob2ndScreenBinding;
import com.amir.serviceman.interfaces.OnAdapterItemClick;
import com.amir.serviceman.util.Common;
import com.amir.serviceman.util.Dialogs;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class PostJob2ndScreen extends BaseActivity implements View.OnClickListener, OnAdapterItemClick {

    private ActivityPostJob2ndScreenBinding binding;
    private DayAdapter dayAdapter;
    private ArrayList<DayModel> dayModels = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_post_job2nd_screen);
        Common.setToolbarWithBackAndTitle(mContext,"Create a job",true,R.drawable.ic_arrow_back_black_24dp);
        implementListener();
        setDayAdapter();
    }


    private void setDayAdapter(){
        dayModels.add(new DayModel("Monday",false));
        dayModels.add(new DayModel("Tuesday",false));
        dayModels.add(new DayModel("Wednesday",false));
        dayModels.add(new DayModel("Thursday",false));
        dayModels.add(new DayModel("Friday",false));
        dayModels.add(new DayModel("Saturday",false));
        dayModels.add(new DayModel("Sunday",false));

        GridLayoutManager gridLayoutManager = new GridLayoutManager(mContext,3);
        binding.rvDay.setLayoutManager(gridLayoutManager);

        dayAdapter = new DayAdapter(mContext,dayModels,this);
        binding.rvDay.setAdapter(dayAdapter);
        
    }
    private void implementListener(){
        binding.tvFrom.setOnClickListener(this);
        binding.tvTo.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
       if (v == binding.tvFrom){
           openTimePicker(true);
       }
       else if (v == binding.tvTo){
           openTimePicker(false);
       }
    }

    private void openTimePicker( final boolean isFromTime) {
        TimePickerDialog mTimePicker;
        mTimePicker = new TimePickerDialog(mContext, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                String hour = selectedHour < 10 ? ("0" + selectedHour) : (selectedHour + "");
                String min = selectedMinute < 10 ? ("0" + selectedMinute) : (selectedMinute + "");

                String generatedTime = "";
                SimpleDateFormat inputFormat = new SimpleDateFormat("HH:mm");
                SimpleDateFormat outputFormat = new SimpleDateFormat("hh:mm aa");

                try {
                    Date date = inputFormat.parse(hour + ":" + min);
                    generatedTime = inputFormat.format(date);
                    if (!isFromTime) {
                        if (Common.validateEditText(binding.tvFrom.getText().toString())) {
                            // Date fromDate = outputFormat.parse(data.get(position).getFromtime());
                            Date fromDate = inputFormat.parse(binding.tvFrom.getText().toString());
                            Date toDate = inputFormat.parse(""+hour+ ":" + min);
                            if (fromDate.after(toDate)) {
                                binding.tvTo.setText("");
                                Dialogs.alertDialog(getString(R.string.from_time_more_than_to_time), mContext);
                                return;
                            }
                        } else {
                            Dialogs.alertDialog(getString(R.string.select_from_time_first), mContext);
                            return;
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                if (isFromTime){
                    binding.tvFrom.setText(generatedTime);
                }
                else {
                    binding.tvTo.setText(generatedTime);
                }

            }
        }, 0, 0, true);
        mTimePicker.setTitle(isFromTime ? getString(R.string.from) : getString(R.string.to));

        mTimePicker.show();
    }

    @Override
    public void onClick(int position, boolean data) {
        if (data){
            dayModels.get(position).setSelected(false);
        }
        else {
            dayModels.get(position).setSelected(true);
        }

        dayAdapter.notifyDataSetChanged();
    }
}
