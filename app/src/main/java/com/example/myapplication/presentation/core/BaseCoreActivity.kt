package com.example.myapplication.presentation.core

import android.os.Bundle
import android.os.PersistableBundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.annotation.ColorRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.myapplication.R
import com.example.myapplication.presentation.core.cardCreate.CreateCardFragment
import com.example.myapplication.presentation.core.cardDetail.DetailCardFragment
import com.example.myapplication.presentation.core.home.HomeFragment

abstract class BaseCoreActivity: AppCompatActivity(){
    private var ivUpButton: ImageView? = null

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)

    }

    abstract fun onBackButtonOnClick(upButton: ImageView?, isOnBackPressed: Boolean)



    override fun onBackPressed() {
        onBackButtonOnClick(ivUpButton, true)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.navigate_drawer, menu)
        return true
    }

    private fun handleUpButtonListener(ivUpButton: ImageView?) {
        ivUpButton?.setOnClickListener {
            onBackButtonOnClick(it as ImageView, false)
        }
    }

    fun initBaseActivity(view :View){
        ivUpButton = view as ImageView
        handleUpButtonListener(ivUpButton)
    }

    fun getColorFromResource(@ColorRes res: Int): Int {
        return ContextCompat.getColor(this, res)
    }



    fun FragmentManager.getFragmentVisibility(tag: String): Boolean {
        return this.findFragmentByTag(tag)?.isVisible == true
    }

    fun FragmentManager.startFragment(fragment: Fragment, newTag: String) {
        if (HomeFragment.FRAGMENT_ID != newTag) {
            ivUpButton?.setImageResource(R.drawable.ic_arrow_back)
        }

        supportFragmentManager.beginTransaction()
            .addToBackStack(newTag)
            .replace(R.id.fragment_container, fragment, newTag)
            .commit()
    }


}