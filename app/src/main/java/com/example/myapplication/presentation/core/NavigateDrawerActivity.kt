package com.example.myapplication.presentation.core

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.databinding.ActivityNavigateDrawerBinding
import com.example.myapplication.libraries.cardview.CardStackView
import com.example.myapplication.libraries.drawerview.*
import com.example.myapplication.libraries.drawerview.callback.DragStateListener
import com.example.myapplication.presentation.core.cardCreate.CreateCardFragment
import com.example.myapplication.presentation.core.cardDetail.DetailCardFragment
import com.example.myapplication.presentation.core.home.HomeFragment


class NavigateDrawerActivity : BaseCoreActivity(), DrawerAdapter.OnItemSelectedListener, DragStateListener {
    private var binding: ActivityNavigateDrawerBinding? = null
    private var slidingRootNav: SlidingRootNav? = null
    private val POS_DASHBOARD = 0
    private val POS_ACCOUNT = 1
    private val POS_MESSAGES = 2
    private val POS_CART = 3
    private val POS_LOGOUT = 5
    private var ivUpButton: ImageView? = null
    private var fragmentManager: FragmentManager? = null

    private var homeFragment: HomeFragment? = null
    private var cardCreateFragment: CreateCardFragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNavigateDrawerBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        setSupportActionBar(binding?.toolbarContainer?.toolbar)

        fragmentManager = supportFragmentManager
        ivUpButton = binding?.toolbarContainer?.ivUpButton
        homeFragment = HomeFragment().newInstance()
        cardCreateFragment = CreateCardFragment()

        initBaseActivity(ivUpButton as View)
        initDrawer(savedInstanceState)

    }

    override fun onBackButtonOnClick(upButton: ImageView?, isOnBackPressed: Boolean) {

        when (true) {
            (fragmentManager?.getFragmentVisibility(CreateCardFragment.FRAGMENT_ID)) -> {
                fragmentManager?.popBackStackImmediate()
                ivUpButton?.setImageResource(R.drawable.ic_menu)
            }

            (fragmentManager?.getFragmentVisibility(DetailCardFragment.FRAGMENT_ID)) -> {
                fragmentManager?.popBackStackImmediate()
                ivUpButton?.setImageResource(R.drawable.ic_menu)

                val slideUp: Animation = AnimationUtils.loadAnimation(this, R.anim.anim_zum_close)
                val cardStackView = findViewById<CardStackView>(R.id.card_stack_view)
                cardStackView?.startAnimation(slideUp);

                val easyFlipView = homeFragment?.getEasyFlipView()
                easyFlipView?.setFlipTypeFromBack()
                easyFlipView?.flipTheView()

            }

            else -> {
                if (isOnBackPressed) {
                    moveTaskToBack(true)
                } else {
                    if (slidingRootNav?.isMenuOpened == true) {
                        slidingRootNav?.closeMenu()
                    } else {
                        slidingRootNav?.openMenu()
                    }
                }
            }

        }

    }

    //Обробатываю выбор елемента из DrawerMenu
    override fun onItemSelected(position: Int) {
        if (position == POS_LOGOUT) {
            finish()
        }

        if (position == POS_DASHBOARD) {
            slidingRootNav!!.closeMenu()
            fragmentManager?.startFragment(homeFragment!!, HomeFragment.FRAGMENT_ID)
        }

    }

    override fun onDragStart() {

    }

    override fun onDragEnd(isMenuOpened: Boolean) {
        if (slidingRootNav?.isMenuClosed != true) {
            ivUpButton?.setImageResource(R.drawable.ic_arrow_back)
        } else {
            ivUpButton?.setImageResource(R.drawable.ic_menu)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val itemId = item.itemId
        when (itemId) {
            R.id.add_new_card -> {
                fragmentManager?.startFragment(cardCreateFragment!!, CreateCardFragment.FRAGMENT_ID)
            }

            R.id.action_settings -> {

            }

            else -> super.onOptionsItemSelected(item)
        }
        return true
    }


    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }


    private fun createItemFor(position: Int): DrawerItem<*> {
        val simpleItem = SimpleItem(
            getItemsIconForDrawerMenu(position),
            getItemsTitleForDrawerMenu(position)
        )

        simpleItem.withIconTint(getColorFromResource(R.color.textColorSecondary))
            .withTextTint(getColorFromResource(R.color.textColorPrimary))
            .withSelectedIconTint(getColorFromResource(R.color.colorAccent))
            .withSelectedTextTint(getColorFromResource(R.color.colorAccent))

        return simpleItem
    }

    //Здесь задается название каждого елеменетна в менюшки Drawer.
    private fun getItemsTitleForDrawerMenu(position: Int): String {
        return arrayOf(
            "Dashboard",
            "My Account",
            "My Account",
            "My Account",
            "My Account",
            "Log Out"
        )[position]
    }

    private fun getItemsIconForDrawerMenu(position: Int): Drawable? {
        val ta = resources.obtainTypedArray(R.array.ld_activityScreenIcons)
        val icons = arrayOfNulls<Drawable?>(ta.length())
        for (i in 0 until ta.length()) {
            val id = ta.getResourceId(i, 0)
            if (id != 0) icons[i] = ContextCompat.getDrawable(this, id)
        }

        ta.recycle()
        return icons[position]
    }


    fun initDrawer(savedInstanceState: Bundle?) {
        slidingRootNav = SlidingRootNavBuilder(this)
            .withToolbarMenuToggle(binding?.toolbarContainer?.toolbar)
            .withMenuOpened(false)
            .withContentClickableWhenMenuOpened(true)
            .withSavedState(savedInstanceState)
            .addDragStateListener(this)
            .withMenuLayout(R.layout.left_drawer_layout)
            .inject()

        val list = mutableListOf<DrawerItem<*>>()
        list.add(createItemFor(POS_DASHBOARD).setChecked(true))
        list.add(createItemFor(POS_ACCOUNT))
        list.add(createItemFor(POS_MESSAGES))
        list.add(createItemFor(POS_CART))
        list.add(SpaceItem(48))
        list.add(createItemFor(POS_LOGOUT))

        val adapter = DrawerAdapter(list)
        adapter.setListener(this)

        val drawerRecyclerviewMenuList = findViewById<RecyclerView>(R.id.drawer_recyclerview)
        drawerRecyclerviewMenuList.isNestedScrollingEnabled = false
        drawerRecyclerviewMenuList.layoutManager = LinearLayoutManager(this)
        drawerRecyclerviewMenuList.adapter = adapter

        adapter.setSelected(POS_DASHBOARD)
    }


}