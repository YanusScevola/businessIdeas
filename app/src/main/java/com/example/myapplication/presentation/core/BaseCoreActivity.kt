package com.example.myapplication.presentation.core

import android.view.Menu
import android.view.View
import android.widget.ImageView
import androidx.annotation.AnimRes
import androidx.annotation.ColorRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentContainer
import androidx.fragment.app.FragmentManager
import com.example.myapplication.R
import com.example.myapplication.presentation.core.cardCreate.CreateCardFragment
import com.example.myapplication.presentation.core.cardstack.CardStackFragment

abstract class BaseCoreActivity: AppCompatActivity(){
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

    fun FragmentManager.startFragment(fragmentManager: FragmentManager, fragment: Fragment, newTag: String, animation: FragmentAnim, isReplace: Boolean) {
        if (CardStackFragment.FRAGMENT_ID != newTag) {
            ivUpButton?.setImageResource(R.drawable.ic_arrow_back)
        }

        if (isReplace){
            fragmentManager.beginTransaction()
                .setCustomAnimations(animation.enter, animation.exit, animation.popEnter, animation.popExit)
                .addToBackStack(newTag)
                .replace(R.id.fragment_container, fragment, newTag)
                .commit()

        }else{
            fragmentManager.beginTransaction()
                .setCustomAnimations(animation.enter, animation.exit, animation.popEnter, animation.popExit)
                .addToBackStack(newTag)
                .add(R.id.fragment_container, fragment, newTag)
                .commit()
        }

    }

    inner class FragmentAnim(
        @AnimRes val enter: Int = 0,
        @AnimRes val exit: Int = 0,
        @AnimRes val popEnter: Int = 0,
        @AnimRes val popExit: Int = 0,
    )


}