package com.example.ml_app
import com.google.gson.annotations.SerializedName

data class Movies(

    val userId: Int,
    val id: Int,
    val title: String,
    val completed: Boolean

)
