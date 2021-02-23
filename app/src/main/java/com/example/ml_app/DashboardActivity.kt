package com.example.ml_app

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.speech.RecognizerIntent
import android.view.View
import androidx.fragment.app.Fragment
import com.etebarian.meowbottomnavigation.MeowBottomNavigation
import android.content.Intent
import android.widget.TextView
import com.example.ml_app.DiaryFragment.Companion.newInstance
import java.lang.reflect.Array.newInstance
import java.util.*

public class DashboardActivity : AppCompatActivity() {
    //var voicetxt = findViewById(R.id.voiceInput) as TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard);

        val bottomNavigation = findViewById(R.id.bottomnav) as MeowBottomNavigation
        addFragment(DiaryFragment.newInstance())
        bottomNavigation.show(0)
        bottomNavigation.add(MeowBottomNavigation.Model(0,R.drawable.ic_baseline_menu_book_24))
        bottomNavigation.add(MeowBottomNavigation.Model(1,R.drawable.ic_baseline_search_24))
        bottomNavigation.add(MeowBottomNavigation.Model(2,R.drawable.ic_charts))
        bottomNavigation.add(MeowBottomNavigation.Model(3,R.drawable.ic_baseline_settings_24))

        bottomNavigation.setOnClickMenuListener {
            when(it.id){
                0 -> {                    replaceFragment(DiaryFragment.newInstance())

                }
                1 -> {                    replaceFragment(SearchFragment.newInstance())


                }
                2 -> {                    replaceFragment(AnalyticsFragment.newInstance())

                }
                3 -> {                    replaceFragment(SettingsFragment.newInstance())

                }

                else -> {                    replaceFragment(DiaryFragment.newInstance())

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
