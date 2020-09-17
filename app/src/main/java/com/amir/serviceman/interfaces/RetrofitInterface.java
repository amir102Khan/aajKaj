package com.amir.serviceman.interfaces;

import java.util.ArrayList;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface RetrofitInterface {

    @GET("project/getcategories")
    Call<ResponseBody> getCategories();


    //profile_image
    //idproof
    @Multipart
    @POST("user/register")
    Call<ResponseBody>
    registerUser(
            @Part("firstname") RequestBody name,
            @Part("lastname") RequestBody last_name,
            @Part("phone") RequestBody phone_number,
            @Part("lat") RequestBody latitude,
            @Part("lng") RequestBody longitude,
            @Part("category") RequestBody category,
            @Part("device_type") RequestBody device_type,
            @Part("device_id") RequestBody device_id,
            @Part("device_token") RequestBody device_token,
            @Part("role") RequestBody role,
            @Part MultipartBody.Part id_proof,
            @Part MultipartBody.Part profileImage
    );

    //profile_image
    //id_proof
    @Multipart
    @POST("user/updateprofile")
    Call<ResponseBody> updateProfile(@Header("Authorization") String authHeader,
                                     @Path("firstname") RequestBody firstName,
                                     @Path("lastname") RequestBody lastname,
                                     @Path("lat") RequestBody lat,
                                     @Part("lng") RequestBody lng,
                                     @Part MultipartBody.Part idProof,
                                     @Part MultipartBody.Part profileImage);


    @POST("user/logout")
    Call<ResponseBody> logout(@Header("Authorization") String authHeader);

    @FormUrlEncoded
    @POST("user/login")
    Call<ResponseBody> login(@Field("phone") String phone,
                             @Field("device_id") String deviceId,
                             @Field("device_token") String deviceTOken,
                             @Field("device_type") String deviceType);


    @FormUrlEncoded
    @POST("user/checkverification")
    Call<ResponseBody> checkVerification(@Header("Authorization") String authHeader);

    @FormUrlEncoded
    @POST("user/contractorlist")
    Call<ResponseBody> contractorList(@Header("Authorization") String authHeader,
                                      @Field("search") String search,
                                      @Field("filter_category") String category);

    @FormUrlEncoded
    @POST("project/getuserprojects")
    Call<ResponseBody> getUserProjects(@Header("Authorization") String authHeader,
                                       @Field("status") int status,@Field("page") int page);


    @FormUrlEncoded
    @POST("project/projectdetails")
    Call<ResponseBody> getProjectDetails(@Header("Authorization") String authHeader,
                                         @Field("p_id") int pId);

    @FormUrlEncoded
    @POST("user/contractorlist")
    Call<ResponseBody> getJobProviders(@Header("Authorization") String authHeader,
                                       @Field("search") String search,
                                       @Field("filter_category") Integer filterCat);

    @FormUrlEncoded
    @POST("project/getall")
    Call<ResponseBody>  getNearbyProject (@Header("Authorization") String authHeader,
                                          @Field("lat") String lat,
                                          @Field("lng")String lng);

    @FormUrlEncoded
    @POST("project/addbid")
    Call<ResponseBody> bidOnProject (@Header("Authorization") String authHeader,
                                     @Field("p_id")int projectId,
                                     @Field("amount")String amount);

    @FormUrlEncoded
    @POST("project/acceptbid")
    Call<ResponseBody> acceptBid(@Header("Authorization") String authHeader,
                                 @Field("bid_id")int bidId);

    @FormUrlEncoded
    @POST("project/cancelbiduser")
    Call<ResponseBody> cancelProviderBidByCustomer(@Header("Authorization")String authHeader,
                                         @Field("bid_id")int bidId);


    @FormUrlEncoded
    @POST("project/cancelbidcontractor")
    Call<ResponseBody> cancelProviderBid(@Header("Authorization")String authHeader,
                                         @Field("bid_id")int bidId);

    @POST("project/getallbiddedprojects")
    Call<ResponseBody> getMyBid(@Header("Authorization")String authHeader);

    @Multipart
    @POST("project/createproject")
    Call<ResponseBody> createJob(@Header("Authorization") String authHeader,
                                 @Part("project_name") RequestBody projectName,
                                 @Part("business_type") RequestBody btype,
                                 @Part("job_type") RequestBody jobtype,
                                 @Part("employee_type") RequestBody employee_type,
                                 @Part("location") RequestBody location,
                                 @Part("lat") RequestBody lat,
                                 @Part("lng") RequestBody lng,
                                 @Part("work_start_time") RequestBody sTime,
                                 @Part("work_end_time") RequestBody eTime,
                                 @Part("salary_hourly") RequestBody hrSal,
                                 @Part("salary_monthly") RequestBody monthSal,
                                 @Part("immediate_start") RequestBody immi_start,
                                 @Part("date")RequestBody days,
                                 @Part MultipartBody.Part[] image);


    @FormUrlEncoded
    @POST("project/projectdetails")
    Call<ResponseBody> customerProjectDetails(@Header("Authorization") String authHeade,
                                              @Field("p_id")int projectId);



}
