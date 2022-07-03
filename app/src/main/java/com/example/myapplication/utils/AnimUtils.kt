package com.example.myapplication.utils

import android.os.Handler
import android.os.Looper
import android.transition.TransitionManager
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.annotation.AnimRes
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.libraries.cardview.CardStackView


class AnimUtils {


    companion object {

        fun startAnimation(view: View, @AnimRes enter: Int) {
            val slideUp: Animation = AnimationUtils.loadAnimation(view.context, enter)
            view.startAnimation(slideUp)
        }


        fun startAnimationUpButton(imageView: ImageView, isRevers: Boolean) {
            if (isRevers) {
                startAnimation(imageView, R.anim.anim_slide_to_down)

                Handler(Looper.getMainLooper()).postDelayed({
                    startAnimation(imageView, R.anim.anim_slide_to_up)
                }, 450)

                Handler(Looper.getMainLooper()).postDelayed({
                    imageView.setImageResource(R.drawable.ic_menu)
                }, 450)
            } else {


                Handler(Looper.getMainLooper()).postDelayed({
                    startAnimation(imageView, R.anim.anim_slide_to_down)
                }, 300)

                Handler(Looper.getMainLooper()).postDelayed({
                    imageView.visibility = View.GONE
                }, 450)

                Handler(Looper.getMainLooper()).postDelayed({
                    imageView.visibility = View.VISIBLE
                    startAnimation(imageView, R.anim.anim_slide_to_up)
                }, 850)

//
//                Handler(Looper.getMainLooper()).postDelayed({
//                    imageView.setImageResource(R.drawable.ic_menu)
//                }, 450)
            }

        }

        fun startAnimationCardOpenDetail(
            fragmentView: ViewGroup,
            stackViewContainer: LinearLayout?,
            cardView: CardView?,
            isFrontSide: Boolean
        ) {
            TransitionManager.beginDelayedTransition(fragmentView)
            val cardStackView = stackViewContainer?.findViewById<RecyclerView>(R.id.card_stack_view)

            val stackViewContainerParam = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT,
            )

            val stackViewParam = LinearLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.MATCH_PARENT
            )

            val cardViewParam = FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.MATCH_PARENT
            )

            if (isFrontSide) {
                stackViewContainerParam.setMargins(0, 0, 0, 0);
                stackViewParam.setMargins(80, 0, 80, 0);
                cardViewParam.setMargins(25, 95, 25, 95);
                stackViewParam.weight = 100f
                cardView?.radius = 25f

                cardView?.layoutParams = cardViewParam;
                stackViewContainer?.layoutParams = stackViewContainerParam;
                cardStackView?.layoutParams = stackViewParam;

                cardView?.requestLayout()
                stackViewContainer?.requestLayout()
                cardStackView?.requestLayout()

            } else {
                stackViewContainerParam.setMargins(0, 0, 0, 0);
                stackViewParam.setMargins(0, 0, 0, 0);
                cardViewParam.setMargins(0, 0, 0, 0);

                cardView?.radius = 0f
                cardView?.layoutParams = cardViewParam;
                stackViewContainer?.layoutParams = stackViewContainerParam;
                cardStackView?.layoutParams = stackViewParam;

                cardView?.requestLayout()
                stackViewContainer?.requestLayout()
                cardStackView?.requestLayout()

            }
        }

    }

    class AnimFragmentTransaction(
        @AnimRes val enter: Int = 0,
        @AnimRes val exit: Int = 0,
        @AnimRes val popEnter: Int = 0,
        @AnimRes val popExit: Int = 0,
    )
}