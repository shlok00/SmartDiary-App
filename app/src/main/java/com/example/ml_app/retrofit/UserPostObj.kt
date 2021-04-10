package com.example.ml_app.retrofit

public class UserPostObj {

    var email: String? = null
    var password: String? = null

    fun UserPostObj(email: String?, password: String?) {
        this.email = email
        this.password = password
    }
}