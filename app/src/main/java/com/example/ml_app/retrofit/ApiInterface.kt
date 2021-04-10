package com.example.ml_app.retrofit

import org.json.JSONObject
import retrofit2.Call
import retrofit2.http.*


interface ApiInterface {
    @GET("users")
    fun getUsers(): Call<List<UserData>>

    @Headers("Content-Type: application/json")
    @POST("login")
    fun loginUser(@Body UserPostObj: JSONObject): Call<UserData>

//    @POST("users/new")
//    fun createUser(@Body user: User?): Call<User?>?
}

object ApiUtils {

    val BASE_URL = "https://aquatic-held-brow.glitch.me/"

    val apiService: ApiInterface
        get() = ApiClient.getClient(BASE_URL)!!.create(ApiInterface::class.java)

}