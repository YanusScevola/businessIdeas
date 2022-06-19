package com.example.myapplication.extensions

import android.view.View
import android.widget.ImageView

class ImageViewExtension {
    fun ImageView.setUpButtonListener(onClick: (View) -> Unit ) {
        this.setOnClickListener {
            onClick(it)
        }

    }
}