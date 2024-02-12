package com.example.openinapp.network

import com.example.openinapp.Model.dashboardAPIResponse.DashboardApiResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header


interface ApiInterface {

    @GET("dashboardNew")
    suspend fun dashBoard(@Header("Authorization") token:String) : Response<DashboardApiResponse>

}