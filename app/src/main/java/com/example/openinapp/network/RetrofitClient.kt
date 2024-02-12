package com.example.openinapp.network

import com.example.openinapp.Utils.Constants
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetroFitClient {
    fun getApi(): ApiInterface {
        return retrofit?.create(ApiInterface::class.java)!!
    }

    init {
        retrofit = Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    }


    companion object{
        private  var retrofit : Retrofit?=null

    }
}