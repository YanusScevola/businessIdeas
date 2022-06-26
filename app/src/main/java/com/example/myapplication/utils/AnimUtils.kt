package com.example.myapplication.utils

import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.annotation.AnimRes
import com.example.myapplication.R

class AnimUtils {
    companion object{
        fun startAnimation(view: View, @AnimRes enter: Int){
            val slideUp: Animation = AnimationUtils.loadAnimation(view.context, enter)
            view.startAnimation(slideUp)
        }
    }

    class AnimFragmentTransaction(
        @AnimRes val enter: Int = 0,
        @AnimRes val exit: Int = 0,
        @AnimRes val popEnter: Int = 0,
        @AnimRes val popExit: Int = 0,
    )
}