package com.amir.serviceman.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CustomerProjectsList {
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


    public class Datum {

        @SerializedName("p_id")
        @Expose
        private Integer pId;
        @SerializedName("u_id")
        @Expose
        private Integer uId;
        @SerializedName("project_name")
        @Expose
        private String projectName;
        @SerializedName("business_type")
        @Expose
        private Integer businessType;
        @SerializedName("job_type")
        @Expose
        private Integer jobType;
        @SerializedName("employee_type")
        @Expose
        private Integer employeeType;
        @SerializedName("work_start_time")
        @Expose
        private String workStartTime;
        @SerializedName("work_end_time")
        @Expose
        private String workEndTime;
        @SerializedName("immediate_start")
        @Expose
        private Integer immediateStart;
        @SerializedName("status")
        @Expose
        private Integer status;
        @SerializedName("location")
        @Expose
        private String location;
        @SerializedName("lat")
        @Expose
        private String lat;
        @SerializedName("lng")
        @Expose
        private String lng;
        @SerializedName("days")
        @Expose
        private List<String> days = null;
        @SerializedName("date")
        @Expose
        private String date;
        @SerializedName("salary_hourly")
        @Expose
        private String salaryHourly;
        @SerializedName("salary_monthly")
        @Expose
        private String salaryMonthly;
        @SerializedName("created_at")
        @Expose
        private String createdAt;
        @SerializedName("updated_at")
        @Expose
        private String updatedAt;
        @SerializedName("images")
        @Expose
        private List<String> images = null;
        @SerializedName("bids")
        @Expose
        private List<Bid> bids = null;
        @SerializedName("totalbids")
        @Expose
        private String totalbids;

        public Integer getPId() {
            return pId;
        }

        public void setPId(Integer pId) {
            this.pId = pId;
        }

        public Integer getUId() {
            return uId;
        }

        public void setUId(Integer uId) {
            this.uId = uId;
        }

        public String getProjectName() {
            return projectName;
        }

        public void setProjectName(String projectName) {
            this.projectName = projectName;
        }

        public Integer getBusinessType() {
            return businessType;
        }

        public void setBusinessType(Integer businessType) {
            this.businessType = businessType;
        }

        public Integer getJobType() {
            return jobType;
        }

        public void setJobType(Integer jobType) {
            this.jobType = jobType;
        }

        public Integer getEmployeeType() {
            return employeeType;
        }

        public void setEmployeeType(Integer employeeType) {
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

        public Integer getImmediateStart() {
            return immediateStart;
        }

        public void setImmediateStart(Integer immediateStart) {
            this.immediateStart = immediateStart;
        }

        public Integer getStatus() {
            return status;
        }

        public void setStatus(Integer status) {
            this.status = status;
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

        public List<String> getDays() {
            return days;
        }

        public void setDays(List<String> days) {
            this.days = days;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
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

        public String getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
        }

        public String getUpdatedAt() {
            return updatedAt;
        }

        public void setUpdatedAt(String updatedAt) {
            this.updatedAt = updatedAt;
        }

        public List<String> getImages() {
            return images;
        }

        public void setImages(List<String> images) {
            this.images = images;
        }

        public List<Bid> getBids() {
            return bids;
        }

        public void setBids(List<Bid> bids) {
            this.bids = bids;
        }

        public String getTotalbids() {
            return totalbids;
        }

        public void setTotalbids(String totalbids) {
            this.totalbids = totalbids;
        }

    }

    public class Data {

        @SerializedName("current_page")
        @Expose
        private Integer currentPage;
        @SerializedName("data")
        @Expose
        private List<Datum> data = null;
        @SerializedName("first_page_url")
        @Expose
        private String firstPageUrl;
        @SerializedName("from")
        @Expose
        private Integer from;
        @SerializedName("last_page")
        @Expose
        private Integer lastPage;
        @SerializedName("last_page_url")
        @Expose
        private String lastPageUrl;
        @SerializedName("next_page_url")
        @Expose
        private String nextPageUrl;
        @SerializedName("path")
        @Expose
        private String path;
        @SerializedName("per_page")
        @Expose
        private Integer perPage;
        @SerializedName("prev_page_url")
        @Expose
        private Object prevPageUrl;
        @SerializedName("to")
        @Expose
        private Integer to;
        @SerializedName("total")
        @Expose
        private Integer total;

        public Integer getCurrentPage() {
            return currentPage;
        }

        public void setCurrentPage(Integer currentPage) {
            this.currentPage = currentPage;
        }

        public List<Datum> getData() {
            return data;
        }

        public void setData(List<Datum> data) {
            this.data = data;
        }

        public String getFirstPageUrl() {
            return firstPageUrl;
        }

        public void setFirstPageUrl(String firstPageUrl) {
            this.firstPageUrl = firstPageUrl;
        }

        public Integer getFrom() {
            return from;
        }

        public void setFrom(Integer from) {
            this.from = from;
        }

        public Integer getLastPage() {
            return lastPage;
        }

        public void setLastPage(Integer lastPage) {
            this.lastPage = lastPage;
        }

        public String getLastPageUrl() {
            return lastPageUrl;
        }

        public void setLastPageUrl(String lastPageUrl) {
            this.lastPageUrl = lastPageUrl;
        }

        public String getNextPageUrl() {
            return nextPageUrl;
        }

        public void setNextPageUrl(String nextPageUrl) {
            this.nextPageUrl = nextPageUrl;
        }

        public String getPath() {
            return path;
        }

        public void setPath(String path) {
            this.path = path;
        }

        public Integer getPerPage() {
            return perPage;
        }

        public void setPerPage(Integer perPage) {
            this.perPage = perPage;
        }

        public Object getPrevPageUrl() {
            return prevPageUrl;
        }

        public void setPrevPageUrl(Object prevPageUrl) {
            this.prevPageUrl = prevPageUrl;
        }

        public Integer getTo() {
            return to;
        }

        public void setTo(Integer to) {
            this.to = to;
        }

        public Integer getTotal() {
            return total;
        }

        public void setTotal(Integer total) {
            this.total = total;
        }

    }




    public class Bid {

        @SerializedName("b_id")
        @Expose
        private Integer bId;
        @SerializedName("p_id")
        @Expose
        private Integer pId;
        @SerializedName("u_id")
        @Expose
        private Integer uId;
        @SerializedName("c_id")
        @Expose
        private Integer cId;
        @SerializedName("amount")
        @Expose
        private Integer amount;
        @SerializedName("bid_accepted")
        @Expose
        private Integer bidAccepted;
        @SerializedName("created_at")
        @Expose
        private String createdAt;
        @SerializedName("updated_at")
        @Expose
        private String updatedAt;
        @SerializedName("review")
        @Expose
        private Integer review;
        @SerializedName("totalreview")
        @Expose
        private Integer totalreview;
        @SerializedName("contractor_name")
        @Expose
        private String contractorName;
        @SerializedName("gender")
        @Expose
        private Integer gender;

        public Integer getBId() {
            return bId;
        }

        public void setBId(Integer bId) {
            this.bId = bId;
        }

        public Integer getPId() {
            return pId;
        }

        public void setPId(Integer pId) {
            this.pId = pId;
        }

        public Integer getUId() {
            return uId;
        }

        public void setUId(Integer uId) {
            this.uId = uId;
        }

        public Integer getCId() {
            return cId;
        }

        public void setCId(Integer cId) {
            this.cId = cId;
        }

        public Integer getAmount() {
            return amount;
        }

        public void setAmount(Integer amount) {
            this.amount = amount;
        }

        public Integer getBidAccepted() {
            return bidAccepted;
        }

        public void setBidAccepted(Integer bidAccepted) {
            this.bidAccepted = bidAccepted;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
        }

        public String getUpdatedAt() {
            return updatedAt;
        }

        public void setUpdatedAt(String updatedAt) {
            this.updatedAt = updatedAt;
        }

        public Integer getReview() {
            return review;
        }

        public void setReview(Integer review) {
            this.review = review;
        }

        public Integer getTotalreview() {
            return totalreview;
        }

        public void setTotalreview(Integer totalreview) {
            this.totalreview = totalreview;
        }

        public String getContractorName() {
            return contractorName;
        }

        public void setContractorName(String contractorName) {
            this.contractorName = contractorName;
        }

        public Integer getGender() {
            return gender;
        }

        public void setGender(Integer gender) {
            this.gender = gender;
        }

    }
}
