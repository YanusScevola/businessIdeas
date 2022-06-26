package com.example.myapplication.utils

import android.graphics.Paint
import android.icu.text.Transliterator
import android.text.Html

class TextUtils {
    companion object{
        fun getTextWithAlign(text: String, aling: Paint.Align ): String{

            when(aling){
                Paint.Align.LEFT -> {
                    return Html.fromHtml("<p style=\"text-align: left\">${text}</p>").toString()
                }
                Paint.Align.CENTER -> {
                    return Html.fromHtml("<p style=\"text-align: center\">${text}</p>").toString()
                }
                Paint.Align.RIGHT -> {
                    return Html.fromHtml("<p style=\"text-align: right\">${text}</p>").toString()

                }
            }

            return ""
        }

    }
}