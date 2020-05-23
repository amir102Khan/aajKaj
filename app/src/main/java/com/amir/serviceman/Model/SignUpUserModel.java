package com.amir.serviceman.Model;

import java.io.File;
import java.io.Serializable;

public class SignUpUserModel implements Serializable {
    private String firstName;
    private String lastName;
    private String phone;
    private String categoryId;
    private String lat;
    private String lng;
    private File profileImage;
    private File profileId;

    public SignUpUserModel(String firstName,
                           String lastName,
                           String phone,String categoryId,File profileImage, File profileId,String lat,String lng){
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.categoryId = categoryId;
        this.profileId = profileId;
        this.lat = lat;
        this.lng = lng;
        this.profileImage = profileImage;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public File getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(File profileImage) {
        this.profileImage = profileImage;
    }

    public File getProfileId() {
        return profileId;
    }

    public void setProfileId(File profileId) {
        this.profileId = profileId;
    }
}
