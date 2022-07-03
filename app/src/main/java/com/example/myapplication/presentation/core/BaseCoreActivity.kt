package com.example.myapplication.presentation.core

import android.os.Handler
import android.os.Looper
import android.view.Menu
import android.view.View
import android.widget.ImageView
import androidx.annotation.ColorRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.myapplication.R
import com.example.myapplication.presentation.core.cardstack.CardStackFragment
import com.example.myapplication.utils.AnimUtils

abstract class BaseCoreActivity : AppCompatActivity() {
    private var ivUpButton: ImageView? = null

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

    fun initBaseActivity(view: View) {
        ivUpButton = view as ImageView
        handleUpButtonListener(ivUpButton)
    }

    fun getColorFromResource(@ColorRes res: Int): Int {
        return ContextCompat.getColor(this, res)
    }


    fun FragmentManager.getFragmentVisibility(tag: String): Boolean {
        return this.findFragmentByTag(tag)?.isVisible == true
    }

    fun FragmentManager.startFragment(
        fragment: Fragment,
        newTag: String,
        fragmentContainerId: Int,
        animation: AnimUtils.AnimFragmentTransaction?,
        isReplace: Boolean,
        delayMillis: Long
    ) {
        if (CardStackFragment.FRAGMENT_ID != newTag) {
            ivUpButton?.setImageResource(R.drawable.ic_arrow_back)
        }

        if (delayMillis > 0) {
            Handler(Looper.getMainLooper()).postDelayed({
                if (isReplace) {
                    this.beginTransaction()
                        .setCustomAnimations(animation?.enter ?: 0, animation?.exit ?: 0, animation?.popEnter ?: 0, animation?.popExit ?: 0)
                        .addToBackStack(newTag)
                        .replace(fragmentContainerId, fragment, newTag)
                        .commit()
                } else {
                    this.beginTransaction()
                        .setCustomAnimations(animation?.enter ?: 0, animation?.exit ?: 0, animation?.popEnter ?: 0, animation?.popExit ?: 0)
                        .addToBackStack(newTag)
                        .add(fragmentContainerId, fragment, newTag)
                        .commit()
                }
            }, delayMillis)

        } else {
            if (isReplace) {
                this.beginTransaction()
                    .setCustomAnimations(animation?.enter ?: 0, animation?.exit ?: 0, animation?.popEnter ?: 0, animation?.popExit ?: 0)
                    .addToBackStack(newTag)
                    .replace(fragmentContainerId, fragment, newTag)
                    .commit()
            } else {
                this.beginTransaction()
                    .setCustomAnimations(animation?.enter ?: 0, animation?.exit ?: 0, animation?.popEnter ?: 0, animation?.popExit ?: 0)
                    .addToBackStack(newTag)
                    .add(fragmentContainerId, fragment, newTag)
                    .commit()
            }
        }


    }


}