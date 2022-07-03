package com.example.myapplication.presentation.core

import android.os.Handler
import android.os.Looper
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentContainer
import androidx.fragment.app.FragmentManager
import com.example.myapplication.R
import com.example.myapplication.presentation.core.cardstack.CardStackFragment
import com.example.myapplication.utils.AnimUtils

open class BaseCoreFragment : Fragment() {
    private var ivUpButton: ImageView? = null
    private var cardStackFragment: Fragment? = null

    fun FragmentManager.getFragmentVisibility(tag: String): Boolean {
        return this.findFragmentByTag(tag)?.isVisible == true
    }

    fun initBaseFragment(imageView: ImageView, fragment: Fragment) {
        ivUpButton = imageView
        cardStackFragment = fragment

    }

    fun FragmentManager.startFragment(
        fragment: Fragment,
        newTag: String,
        fragmentContainerId: Int,
        animation: AnimUtils.AnimFragmentTransaction?,
        isReplace: Boolean,
        delayMillis: Long
    ) {
        if (CardStackFragment.FRAGMENT_ID != newTag) {
            ivUpButton?.setImageResource(R.drawable.ic_arrow_back)
        }

        if (delayMillis > 0) {
            Handler(Looper.getMainLooper()).postDelayed({
                if (isReplace) {
                    this.beginTransaction()
                        .setCustomAnimations(animation?.enter ?: 0, animation?.exit ?: 0, animation?.popEnter ?: 0, animation?.popExit ?: 0)
                        .addToBackStack(newTag)
                        .replace(fragmentContainerId, fragment, newTag)
                        .commit()
                } else {
                    this.beginTransaction()
                        .setCustomAnimations(animation?.enter ?: 0, animation?.exit ?: 0, animation?.popEnter ?: 0, animation?.popExit ?: 0)
                        .addToBackStack(newTag)
                        .add(fragmentContainerId, fragment, newTag)
                        .commit()
                }
            }, delayMillis)

        } else {
            if (isReplace) {
                this.beginTransaction()
                    .setCustomAnimations(animation?.enter ?: 0, animation?.exit ?: 0, animation?.popEnter ?: 0, animation?.popExit ?: 0)
                    .addToBackStack(newTag)
                    .replace(fragmentContainerId, fragment, newTag)
                    .commit()
            } else {
                this.beginTransaction()
                    .setCustomAnimations(animation?.enter ?: 0, animation?.exit ?: 0, animation?.popEnter ?: 0, animation?.popExit ?: 0)
                    .addToBackStack(newTag)
                    .add(fragmentContainerId, fragment, newTag)
                    .commit()
            }
        }

    }


}