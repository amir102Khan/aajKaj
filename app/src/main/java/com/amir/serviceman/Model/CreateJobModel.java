package com.amir.serviceman.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class CreateJobModel implements Serializable {
        @SerializedName("type")
        @Expose
        private String type;
        @SerializedName("message")
        @Expose
        private String message;
        @SerializedName("data")
        @Expose
        private Data data;

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public Data getData() {
            return data;
        }

        public void setData(Data data) {
            this.data = data;
        }

    public class Data implements  Serializable{

        @SerializedName("project_name")
        @Expose
        private String projectName;
        @SerializedName("business_type")
        @Expose
        private String businessType;
        @SerializedName("job_type")
        @Expose
        private String jobType;
        @SerializedName("employee_type")
        @Expose
        private String employeeType;
        @SerializedName("work_start_time")
        @Expose
        private String workStartTime;
        @SerializedName("work_end_time")
        @Expose
        private String workEndTime;
        @SerializedName("immediate_start")
        @Expose
        private String immediateStart;
        @SerializedName("location")
        @Expose
        private String location;
        @SerializedName("lat")
        @Expose
        private String lat;
        @SerializedName("lng")
        @Expose
        private String lng;
        @SerializedName("salary_hourly")
        @Expose
        private String salaryHourly;
        @SerializedName("salary_monthly")
        @Expose
        private String salaryMonthly;
        @SerializedName("days")
        @Expose
        private List<String> days = null;
        @SerializedName("u_id")
        @Expose
        private Integer uId;
        @SerializedName("updated_at")
        @Expose
        private String updatedAt;
        @SerializedName("created_at")
        @Expose
        private String createdAt;
        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("images")
        @Expose
        private List<String> images = null;
        @SerializedName("totalbids")
        @Expose
        private String totalbids;

        public String getProjectName() {
            return projectName;
        }

        public void setProjectName(String projectName) {
            this.projectName = projectName;
        }

        public String getBusinessType() {
            return businessType;
        }

        public void setBusinessType(String businessType) {
            this.businessType = businessType;
        }

        public String getJobType() {
            return jobType;
        }

        public void setJobType(String jobType) {
            this.jobType = jobType;
        }

        public String getEmployeeType() {
            return employeeType;
        }

        public void setEmployeeType(String employeeType) {
            this.employeeType = employeeType;
        }

        public String getWorkStartTime() {
            return workStartTime;
        }

        public void setWorkStartTime(String workStartTime) {
            this.workStartTime = workStartTime;
        }

        public String getWorkEndTime() {
            return workEndTime;
        }

        public void setWorkEndTime(String workEndTime) {
            this.workEndTime = workEndTime;
        }

        public String getImmediateStart() {
            return immediateStart;
        }

        public void setImmediateStart(String immediateStart) {
            this.immediateStart = immediateStart;
        }

        public String getLocation() {
            return location;
        }

        public void setLocation(String location) {
            this.location = location;
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

        public String getSalaryHourly() {
            return salaryHourly;
        }

        public void setSalaryHourly(String salaryHourly) {
            this.salaryHourly = salaryHourly;
        }

        public String getSalaryMonthly() {
            return salaryMonthly;
        }

        public void setSalaryMonthly(String salaryMonthly) {
            this.salaryMonthly = salaryMonthly;
        }

        public List<String> getDays() {
            return days;
        }

        public void setDays(List<String> days) {
            this.days = days;
        }

        public Integer getUId() {
            return uId;
        }

        public void setUId(Integer uId) {
            this.uId = uId;
        }

        public String getUpdatedAt() {
            return updatedAt;
        }

        public void setUpdatedAt(String updatedAt) {
            this.updatedAt = updatedAt;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
        }

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public List<String> getImages() {
            return images;
        }

        public void setImages(List<String> images) {
            this.images = images;
        }

        public String getTotalbids() {
            return totalbids;
        }

        public void setTotalbids(String totalbids) {
            this.totalbids = totalbids;
        }

    }
}
