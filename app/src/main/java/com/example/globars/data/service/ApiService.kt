package com.example.globars.data.service

import com.example.globars.data.pojo.LoginResponse
import com.example.globars.data.pojo.SessionsResponse
import com.example.globars.data.pojo.request.User
import com.example.globars.data.pojo.units.UnitsResponse
import com.google.gson.GsonBuilder
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*


interface ApiService {

    @POST("auth/login")
    fun login(@Body user: User):
            Call<LoginResponse>

    @GET("tracking/sessions")
    fun getIdSession(@Header("Authorization") token: String):
            Call<SessionsResponse>

    @GET("tracking/{sessionId}/units")
    fun getUnits(@Header("Authorization") token: String, @Path("sessionId") sessionId: String,
                 @Query("mobile") mobile: Boolean):
            Call<UnitsResponse>

    companion object {
        val instance: ApiService by lazy {
            val gson = GsonBuilder()
                .setLenient()
                .create()

            val retrofit = Retrofit.Builder()
                .baseUrl("https://test.globars.ru/api/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()

            retrofit.create(ApiService::class.java)
        }
    }


}