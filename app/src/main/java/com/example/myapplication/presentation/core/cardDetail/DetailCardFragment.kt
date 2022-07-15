package com.example.myapplication.presentation.core.cardDetail

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.R
import com.example.myapplication.presentation.core.cardstack.CardStackFragment
import com.facebook.shimmer.ShimmerFrameLayout

//
//private const val ARG_PARAM1 = "param1"
//private const val ARG_PARAM2 = "param2"

class DetailCardFragment : Fragment() {
    private var param1: String? = null
    private var param2: String? = null
    private var cardStackFragment: CardStackFragment? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        cardStackFragment = parentFragmentManager.findFragmentByTag(CardStackFragment.FRAGMENT_ID) as CardStackFragment
        cardStackFragment?.setCardStackEnabled(false)

        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_detali_card, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    override fun onStart() {
        super.onStart()
    }

    companion object {
        const val FRAGMENT_ID = "DetailCardFragment"

        @JvmStatic
        fun newInstance() =
            DetailCardFragment().apply {
                arguments = Bundle().apply {
//                    putString(ARG_PARAM1, param1)
//                    putString(ARG_PARAM2, param2)
                }
            }
    }
}