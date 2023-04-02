package com.dbtechprojects.photonotes.screens.client


import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface RetrofitService {


    @GET("posts/{page}")
    fun getUserPage(@Path("page") page:String) : Call<Users2>

//    @GET("test/")
//    fun getFlask() : Call<Ticker>

    @Multipart
    @POST("/test/")
    fun post_profile(
        @Part image : MultipartBody.Part
//        @PartMap data: HashMap<String, RequestBody>
//    @Field("names") names:String,
//    @Field("number2") number2:String
//    @Body params: Users2

    ): Call<Users2>


}