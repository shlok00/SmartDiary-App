package com.example.ml_app

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.speech.RecognizerIntent
import android.view.View
import androidx.fragment.app.Fragment
import com.etebarian.meowbottomnavigation.MeowBottomNavigation
import android.content.Intent
import android.view.Window
import android.view.WindowManager
import android.widget.TextView
import com.example.ml_app.DiaryFragment.Companion.newInstance
//import com.example.ml_app.retrofit.MainActivityViewModel
import java.lang.reflect.Array.newInstance
import java.util.*
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.ml_app.R
//import com.example.ml_app.retrofit.MainActivityRepository
import com.example.ml_app.retrofit.RetrofitClient
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

public class DashboardActivity : AppCompatActivity() {
    //var voicetxt = findViewById(R.id.voiceInput) as TextView

//    lateinit var mainActivityViewModel: MainActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE); //will hide the title
        getSupportActionBar()?.hide(); // hide the title bar
        this.getWindow().setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.dashboard);


//        mainActivityViewModel = ViewModelProvider(this).get(MainActivityViewModel::class.java)


//        mainActivityViewModel.getUser()!!.observe(this, Observer { serviceSetterGetter ->
//            val msg = serviceSetterGetter.message
//            val toast = Toast.makeText(applicationContext, msg, Toast.LENGTH_LONG).show()
//        })

        val bottomNavigation = findViewById(R.id.bottomnav) as MeowBottomNavigation
        addFragment(newInstance())
        bottomNavigation.show(0)
        bottomNavigation.add(MeowBottomNavigation.Model(0,R.drawable.ic_baseline_menu_book_24))
        bottomNavigation.add(MeowBottomNavigation.Model(1,R.drawable.ic_baseline_search_24))
        bottomNavigation.add(MeowBottomNavigation.Model(2,R.drawable.ic_baseline_mic_none_24))
        bottomNavigation.add(MeowBottomNavigation.Model(3,R.drawable.ic_charts))
        bottomNavigation.add(MeowBottomNavigation.Model(4,R.drawable.ic_baseline_settings_24))

        bottomNavigation.setOnClickMenuListener {
            when(it.id){
                0 -> {                    replaceFragment(newInstance())

                }
                1 -> {                    replaceFragment(SearchFragment.newInstance())


                }
                2 -> {                    replaceFragment(AnalyticsFragment.newInstance())

                }
                3 -> {                    replaceFragment(AIFragment.newInstance())

                }
                4 -> {                    replaceFragment(SettingsFragment.newInstance())

                }

                else -> {                    replaceFragment(newInstance())

                }
            }

        }


        }


    private fun replaceFragment(fragment:Fragment){
        val fragmentTransition = supportFragmentManager.beginTransaction()
        fragmentTransition.replace(R.id.fragmentContainer,fragment).addToBackStack(Fragment::class.java.simpleName).commit()
    }

    private fun addFragment(fragment:Fragment){
        val fragmentTransition = supportFragmentManager.beginTransaction()
        fragmentTransition.add(R.id.fragmentContainer,fragment).addToBackStack(Fragment::class.java.simpleName).commit()
    }

}
