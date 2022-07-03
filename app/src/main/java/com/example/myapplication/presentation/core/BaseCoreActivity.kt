package com.example.myapplication.presentation.core

import android.os.Handler
import android.os.Looper
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import androidx.annotation.ColorRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.myapplication.R
import com.example.myapplication.presentation.core.cardstack.CardStackFragment
import com.example.myapplication.utils.AnimUtils

abstract class BaseCoreActivity : AppCompatActivity() {
    private var btnUpButton: ImageView? = null
    private var btnAddNewCard: ImageView? = null

    abstract fun onBackButtonOnClick(view: ImageView?, isOnBackPressed: Boolean)
    abstract fun onClickToolbarButton(id: Int, view: View?, isClicked: Boolean)

    override fun onBackPressed() {
        onBackButtonOnClick(btnUpButton, true)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.navigate_drawer, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        var isSettingClicked = false
        when (item.itemId) {
            R.id.action_settings -> {
                isSettingClicked = true
                onClickToolbarButton(R.id.action_settings, item.actionView, isSettingClicked)
            }
            else -> super.onOptionsItemSelected(item)
        }
        return true
    }

    private fun handleToolbarButtonsListener(btnList: List<View>) {
        var isAddButtonClickClicked = false
        btnList.forEach {
            when(it.id){
                R.id.btn_add_new_card ->{
                    it.setOnClickListener {
                        isAddButtonClickClicked = true
                        onClickToolbarButton(R.id.btn_add_new_card, it as ImageView, isAddButtonClickClicked)
                    }
                }

            }
        }
    }

    private fun handleUpButtonListener(btn: ImageView?) {
        btn?.setOnClickListener {
            onBackButtonOnClick(it as ImageView, false)
        }
    }


    fun initBaseActivity(upButton: ImageView?, addNewCard: ImageView?) {
        btnUpButton = upButton
        btnAddNewCard = addNewCard
        handleUpButtonListener(btnUpButton)
        handleToolbarButtonsListener(listOf(addNewCard as View))
    }

    fun getColorFromResource(@ColorRes res: Int): Int {
        return ContextCompat.getColor(this, res)
    }


    fun FragmentManager.isFragmentVisible(tag: String): Boolean {
        return this.findFragmentByTag(tag)?.isVisible == true
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
            btnUpButton?.setImageResource(R.drawable.ic_arrow_back)
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