package com.example.ml_app


import com.google.gson.GsonBuilder
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET


const val BASE_URL = "https://jsonplaceholder.typicode.com/"


interface MoviesApi{

    @GET("todos")
    fun getAllData(): Call<List<Movies>>


    companion object {
        operator fun invoke() : MoviesApi{
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(MoviesApi::class.java)
        }
    }
}
