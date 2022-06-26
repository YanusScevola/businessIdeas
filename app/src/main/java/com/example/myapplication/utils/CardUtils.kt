package com.example.myapplication.utils

import android.graphics.Bitmap
import android.graphics.Canvas
import android.util.Log
import android.view.View

class CardUtils {
    companion object{
        fun getScreenShotFromView(v: View): Bitmap? {
            var screenshot: Bitmap? = null
            try {
                screenshot = Bitmap.createBitmap(v.width, v.height, Bitmap.Config.ARGB_8888)
                val canvas = Canvas(screenshot)
                v.draw(canvas)
            } catch (e: Exception) {
                Log.e("GFG", "Failed to capture screenshot because:" + e.message)
            }
            return screenshot
        }
    }
}