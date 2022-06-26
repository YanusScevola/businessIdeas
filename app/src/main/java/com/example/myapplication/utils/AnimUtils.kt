package com.example.myapplication.utils

import androidx.annotation.AnimRes

class AnimUtils {
    companion object{

    }

    class AnimFragmentTransaction(
        @AnimRes val enter: Int = 0,
        @AnimRes val exit: Int = 0,
        @AnimRes val popEnter: Int = 0,
        @AnimRes val popExit: Int = 0,
    )
}