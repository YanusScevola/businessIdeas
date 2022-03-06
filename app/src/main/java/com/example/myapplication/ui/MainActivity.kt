package com.example.myapplication.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.Menu
import android.view.View
import androidx.appcompat.widget.AppCompatImageView
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.FragmentManager
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.myapplication.MAIN
import com.example.myapplication.R
import com.example.myapplication.databinding.ActivityNavigateDrawerBinding
import com.google.android.material.navigation.NavigationView
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {
    private var fragmentManager: FragmentManager? = null
    private var navController: NavController? = null
    private lateinit var appBarConfiguration: AppBarConfiguration


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        Handler().postDelayed({
//            if (!supportFragmentManager.isDestroyed) {
////                navController = Navigation.findNavController(this, R.id.nav_host_fragment)
//            }
//
//        }, 2000)


    }


}