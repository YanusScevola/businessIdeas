package com.example.myapplication.utils

import android.graphics.Bitmap
import android.graphics.Canvas
import android.util.Log
import android.view.View

class ScreenUtils {
    companion object{
        fun getScreenShotFromView(view: View): Bitmap? {
            var screenshot: Bitmap? = null
            try {
                screenshot = Bitmap.createBitmap(view.width, view.height, Bitmap.Config.ARGB_8888)
                val canvas = Canvas(screenshot)
                view.draw(canvas)
            } catch (e: Exception) {
                Log.e("GFG", "Failed to capture screenshot because:" + e.message)
            }
            return screenshot
        }

        fun replaceViewWithScreenshot(viewForScreenshot: View, replacedView: View, ) {

        }

    }
}