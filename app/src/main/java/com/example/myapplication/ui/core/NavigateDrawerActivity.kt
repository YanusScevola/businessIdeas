package com.example.myapplication.ui.core

import android.os.Bundle
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.FragmentManager
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.myapplication.R
import com.example.myapplication.databinding.ActivityNavigateDrawerBinding
import com.google.android.material.navigation.NavigationView

class NavigateDrawerActivity : AppCompatActivity() {
    private lateinit var appBarConfiguration: AppBarConfiguration
    private var binding: ActivityNavigateDrawerBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNavigateDrawerBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        setSupportActionBar(binding?.appBarNavigateDrawer?.toolbar)

        val drawerLayout: DrawerLayout? = binding?.drawerLayout
        val navView: NavigationView? = binding?.navView
        val navController = findNavController(R.id.nav_fragment_navigate_drawer)




        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home,
                R.id.nav_my_office,
                R.id.nav_community,
                R.id.nav_about_business,
                R.id.nav_donat
            ), drawerLayout
        )

        navView?.setupWithNavController(navController)
        setupActionBarWithNavController(navController, appBarConfiguration)

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.navigate_drawer, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_fragment_navigate_drawer)
        navController.navigateUp(appBarConfiguration)
        return navController.navigateUp() || super.onSupportNavigateUp()
    }



    override fun onBackPressed() {
        val fragmentManager: FragmentManager = supportFragmentManager
        if (fragmentManager.backStackEntryCount > 1) {
            fragmentManager.popBackStackImmediate()
        } else {
            super.onBackPressed()
        }
    }

    override fun onStop() {
        super.onStop()
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }

    fun showUpButton() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    fun hideUpButton() {
        supportActionBar?.setDisplayHomeAsUpEnabled(false)
    }


}