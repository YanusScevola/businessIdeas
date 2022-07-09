package com.example.myapplication.presentation.core.cardCreate

import android.animation.Animator
import android.animation.ArgbEvaluator
import android.animation.ValueAnimator
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.myapplication.R
import com.example.myapplication.presentation.core.cardstack.CardStackFragment
import com.example.myapplication.utils.AnimUtils


class CreateCardFragment : Fragment() {
    private var param1: String? = null
    private var param2: String? = null
    private var cardStackFragment: CardStackFragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        cardStackFragment = parentFragmentManager.findFragmentByTag(CardStackFragment.FRAGMENT_ID) as CardStackFragment
        cardStackFragment?.setCardStackEnabled(false)

        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_create_card, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.background = ColorDrawable(Color.TRANSPARENT)
        Handler(Looper.getMainLooper()).postDelayed({
            AnimUtils.startAnimationBackgroundMadeTransparent(view)
        }, 600)

    }

    override fun onDestroyView() {
        super.onDestroyView()
    }

    override fun onDestroy() {
        super.onDestroy()
        if (view != null) {
//            view!!.background = ColorDrawable(Color.WHITE)

//            val from = ContextCompat.getColor(requireContext(), android.R.color.white)
//            val to = ContextCompat.getColor(requireContext(), android.R.color.transparent)
//            val anim = ValueAnimator()
//            anim.setIntValues(from, to)
//            anim.setEvaluator(ArgbEvaluator())
//            anim.addUpdateListener { valueAnimator -> view!!.setBackgroundColor((valueAnimator.getAnimatedValue() as Int)!!) }
//            anim.duration = 1500
//            anim.start()

//
//            val colorFrom = Color.WHITE
//            val colorTo = Color.TRANSPARENT
//            val duration = 1300
//            ObjectAnimator.ofObject(view!!, "backgroundColor", ArgbEvaluator(), colorFrom, colorTo)
//                .setDuration(duration.toLong())
//                .start()

        }
    }


    companion object {
        const val FRAGMENT_ID = "CreateCardFragment"
        private const val ARG_PARAM1 = "param1"
        private const val ARG_PARAM2 = "param2"

        @JvmStatic
        fun newInstance() =
            CreateCardFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}