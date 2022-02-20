package com.example.myapplication.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import androidx.appcompat.widget.AppCompatImageView
import androidx.fragment.app.FragmentManager
import com.example.myapplication.R

class MainActivity : AppCompatActivity() {
    private var fragmentManager:FragmentManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        fragmentManager = supportFragmentManager

        Handler().postDelayed({
            if (!supportFragmentManager.isDestroyed){
                findViewById<AppCompatImageView>(R.id.image).visibility = View.GONE
                supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.fragment_container, SwipePlayFragment.newInstance("", ""))
                    .commit()
            }

        }, 2000)


    }

    override fun onBackPressed() {
        fragmentManager?.popBackStack()
    }
}