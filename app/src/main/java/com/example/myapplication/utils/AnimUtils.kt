package com.example.myapplication.utils

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.TransitionDrawable
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

        fun startAnimationBackgroundMadeTransparent(view: View) {
            val color: Array<ColorDrawable> = arrayOf(ColorDrawable(Color.TRANSPARENT), ColorDrawable(Color.WHITE))
            val trans = TransitionDrawable(color)
            view.setBackgroundDrawable(trans)
            trans.startTransition(500)
        }

        fun startAnimationBackgroundMadeNoTransparent(view: View) {
            val color: Array<ColorDrawable> = arrayOf(ColorDrawable(Color.WHITE), ColorDrawable(Color.TRANSPARENT), ColorDrawable(Color.TRANSPARENT))
            val trans = TransitionDrawable(color)
            view.background = trans
            trans.startTransition(300)
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
                stackViewParam.setMargins(70, 0, 70, 0);
                cardViewParam.setMargins(0, 95, 0, 95);
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