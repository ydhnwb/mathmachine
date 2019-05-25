package com.ydhnwb.mathmachine

import android.content.pm.ActivityInfo
import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.ydhnwb.mathmachine.fragments.DailyFragment
import com.ydhnwb.mathmachine.fragments.OtherFragment
import com.ydhnwb.mathmachine.fragments.ProfileFragment

class MainActivity : AppCompatActivity() {

    private lateinit var fragment : Fragment
    companion object { var navStatus = -1 }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val navView: BottomNavigationView = findViewById(R.id.nav_view)
        navView.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)
        if(savedInstanceState == null){
            navView.selectedItemId = R.id.navigation_home
        }
    }

    override fun onResume() {
        super.onResume()
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

    private val onNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_home -> {
                if(navStatus != 0){
                    fragment = DailyFragment()
                    navStatus = 0
                }
            }
            R.id.navigation_dashboard -> {
                if(navStatus != 1){
                    fragment = OtherFragment()
                    navStatus = 1
                }
            }
            R.id.navigation_profile -> {
                if(navStatus != 2){
                    fragment = ProfileFragment()
                    navStatus = 2
                }
            }
            else -> {
                navStatus = 0
                fragment = DailyFragment()
            }
        }
        if(fragment == null){
            navStatus = 0
            fragment = DailyFragment()
        }
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragment_container, fragment)
        fragmentTransaction.commit()
        true
    }
}
