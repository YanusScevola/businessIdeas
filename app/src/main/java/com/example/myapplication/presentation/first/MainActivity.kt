package com.example.myapplication.presentation.first

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.FragmentManager
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.AppBarConfiguration
import com.example.myapplication.R

class MainActivity : AppCompatActivity() {
    private var fragmentManager: FragmentManager? = null
    private var navController: NavController? = null
    private lateinit var appBarConfiguration: AppBarConfiguration


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        Handler().postDelayed({
            if (!supportFragmentManager.isDestroyed) {
                navController = Navigation.findNavController(this, R.id.nav_host_fragment_first)
            }

//        }, 2000)


    }


}