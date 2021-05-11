package com.example.ml_app

import retrofit2.http.Body
import retrofit2.http.POST
import com.squareup.okhttp.Response
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call

interface Sentiment {
    // ...
    @POST("/getEmotion")
    fun createentry(@Body requestBody: RequestBody): Call<ResponseBody>
    // ...
}