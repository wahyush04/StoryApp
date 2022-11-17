package com.wahyush04.submissionstoryapp.api

import com.wahyush04.submissionstoryapp.data.*
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.*


interface ApiService {

    @POST("/v1/login")
    fun userLogin(
        @Body loginRequest: LoginRequest
    ): Call<LoginResponse>

    @POST("/v1/register")
    fun userRegister(
        @Body registerRequest: RegisterRequest
    ): Call<RegisterResponse>

    @GET("/v1/stories")
    suspend fun getStory(
        @Header("Authorization") token : String?,
        @Query("location") location: Int
    ):StoryResponse

    @GET("/v1/stories")
    suspend fun getListStory(
        @Header("Authorization") token : String?,
        @Query("page") page: Int,
        @Query("size") size: Int,
        @Query("location") location: Int
    ):StoryResponse

    @Multipart
    @POST("/v1/stories")
    suspend fun uploadImage(
        @Header("Authorization") token : String,
        @Part file: MultipartBody.Part,
        @Part("description") description: String,
        @Part("lat") lat: Float,
        @Part("lon") lon: Float,
    ): ImageUploadResponse
}