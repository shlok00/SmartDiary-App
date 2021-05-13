package com.example.ml_app
import com.google.gson.annotations.SerializedName

data class Entry(

val angry: Double?= 0.0,
val diaryentry: String?= null,
val emotion: String?= null,
val fear: Double?= 0.0,
val happy: Double?= 0.0,
val nsfw: String?= null,
val sad: Double?= 0.0,
val surprise: Double?= 0.0,
val time: String?= null,
val username: String?= null



    )
