package com.example.ml_app.retrofit

import retrofit2.Call
import retrofit2.http.GET

interface ApiInterface {

    @GET("services")
    fun getServices() : Call<ServicesSetterGetter>

}