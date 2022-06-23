package com.example.myapplication.presentation.core

import android.os.Bundle
import android.os.PersistableBundle
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.R
import com.example.myapplication.presentation.core.cardCreate.CreateCardFragment
import com.example.myapplication.presentation.core.cardDetail.DetailCardFragment
import com.example.myapplication.presentation.core.home.HomeFragment

open class BaseCoreActivity: AppCompatActivity(){
    private var ivUpButton: ImageView? = null


    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)

    }


    fun initBaseActivity(view :View){
        ivUpButton = view as ImageView
        handleUpButtonListener(ivUpButton)
    }

    open fun onBackButtonOnClick(upButton: ImageView?){

    }


    override fun onBackPressed() {
        onBackButtonOnClick(ivUpButton)
    }


    private fun handleUpButtonListener(ivUpButton: ImageView?) {
        ivUpButton?.setOnClickListener {
            onBackButtonOnClick(it as ImageView)
        }
    }






}