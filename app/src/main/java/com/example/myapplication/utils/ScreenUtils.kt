package com.example.myapplication.utils

import android.app.Activity
import android.content.ContentValues
import android.graphics.Bitmap
import android.graphics.Canvas
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.view.ViewTreeObserver
import android.widget.ImageView
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream

class ScreenUtils {
    companion object{
        fun getScreenShotFromView(view: View): Bitmap? {
            var screenshot: Bitmap? = null

            val vto: ViewTreeObserver = view.viewTreeObserver
            vto.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
                override fun onGlobalLayout() {
                    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
                        view.getViewTreeObserver().removeGlobalOnLayoutListener(this)
                    } else {
                        view.getViewTreeObserver().removeOnGlobalLayoutListener(this)
                    }
                    val width: Int = view.getMeasuredWidth()
                    val height: Int = view.getMeasuredHeight()
                }
            })

            try {
                screenshot = Bitmap.createBitmap(view.width, view.height, Bitmap.Config.ARGB_8888)
                Log.e("tag", "razmer" + view.height)
                val canvas = Canvas(screenshot)
                view.draw(canvas)
            } catch (e: Exception) {
                Log.e("GFG", "Failed to capture screenshot because:" + e.message)
            }
            return screenshot

        }

        fun replaceViewWithScreenshot(viewForScreenshot: View, imageViewForReplace: ImageView?, viewShouldBeGoneAfterReplace: View?) {
            viewShouldBeGoneAfterReplace?.visibility = View.VISIBLE
            val bitmap = getScreenShotFromView(viewForScreenshot)
            imageViewForReplace?.setImageBitmap(bitmap)
            viewShouldBeGoneAfterReplace?.visibility = View.GONE
        }

        fun saveMediaToStorage(activity: Activity,  bitmap: Bitmap) {
            val filename = "${System.currentTimeMillis()}.jpg"
            var fos: OutputStream? = null
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                activity.contentResolver?.also { resolver ->
                    val contentValues = ContentValues().apply {
                        put(MediaStore.MediaColumns.DISPLAY_NAME, filename)
                        put(MediaStore.MediaColumns.MIME_TYPE, "image/jpg")
                        put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_PICTURES)
                    }
                    val imageUri: Uri? = resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)
                    fos = imageUri?.let { resolver.openOutputStream(it) }
                }
            } else {
                val imagesDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
                val image = File(imagesDir, filename)
                fos = FileOutputStream(image)
            }
            fos?.use {
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, it)
//            Toast.makeText(this , "Captured View and saved to Gallery" , Toast.LENGTH_SHORT).show()
            }
        }

    }
}