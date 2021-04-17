package com.example.ml_app

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.Window
import android.view.WindowManager
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.victor.loading.book.BookLoading

class MainActivity : AppCompatActivity() {

    private lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE); //will hide the title
        getSupportActionBar()?.hide();
        setContentView(R.layout.activity_main)
        val dashboardIntent = Intent(this, DashboardActivity::class.java)
        startActivity(dashboardIntent)

        /**If user is not authenticated, send him to SignInActivity to authenticate first.
         * Else send him to DashboardActivity*/
        /*
        val bookLoading: BookLoading = findViewById(R.id.bookloading)
        if(!bookLoading.isStart()){
            bookLoading.start();
        }
        mAuth = FirebaseAuth.getInstance()
        val user = mAuth.currentUser

        Handler().postDelayed({
            if(user != null){
                val dashboardIntent = Intent(this, DashboardActivity::class.java)
                startActivity(dashboardIntent)
                finish()
            }else{
//                val dashboardIntent = Intent(this, DashboardActivity::class.java)
//                startActivity(dashboardIntent)
//                finish()
                val signInIntent = Intent(this, SignInActivity::class.java)
                startActivity(signInIntent)
                finish()
            }
        }, 5000)
        */
    }
}