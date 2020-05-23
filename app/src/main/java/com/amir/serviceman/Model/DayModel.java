package com.amir.serviceman.Model;

import java.io.Serializable;

public class DayModel implements Serializable {

    private String dayName;
    private boolean isSelected;

    public DayModel(String dayName, boolean isSelected){
        this.dayName = dayName;
        this.isSelected = isSelected;
    }

    public String getDayName() {
        return dayName;
    }

    public void setDayName(String dayName) {
        this.dayName = dayName;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
