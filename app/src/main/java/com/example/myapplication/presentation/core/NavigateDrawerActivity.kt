package com.example.myapplication.presentation.core

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.annotation.ColorRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.databinding.ActivityNavigateDrawerBinding
import com.example.myapplication.libraries.drawerview.*
import com.example.myapplication.libraries.drawerview.callback.DragStateListener
import com.example.myapplication.presentation.core.cardCreate.CreateCardFragment
import com.example.myapplication.presentation.core.cardDetail.DetailCardFragment
import com.example.myapplication.presentation.core.home.HomeFragment


class NavigateDrawerActivity : AppCompatActivity(), DrawerAdapter.OnItemSelectedListener, DragStateListener {
    private var binding: ActivityNavigateDrawerBinding? = null
    private var slidingRootNav: SlidingRootNav? = null
    private val POS_DASHBOARD = 0
    private val POS_ACCOUNT = 1
    private val POS_MESSAGES = 2
    private val POS_CART = 3
    private val POS_LOGOUT = 5
    private var ivUpButton: ImageView? = null
    private var containerDrawerLayout: LinearLayout? = null
    private var fragmentManager: FragmentManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNavigateDrawerBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        setSupportActionBar(binding?.toolbarContainer?.toolbar)
        ivUpButton = findViewById(R.id.iv_upButton)

        val inflater = LayoutInflater.from(this)
        val layout: LinearLayout = inflater.inflate(R.layout.left_drawer_layout, null, false) as LinearLayout

        containerDrawerLayout = layout.findViewById(R.id.container_drawer_layout)
        fragmentManager = supportFragmentManager
        containerDrawerLayout?.setBackgroundColor(ContextCompat.getColor(this, R.color.color_green))


        initDrawer(savedInstanceState)
        handleUpButtonListener(ivUpButton)

    }

    private fun handleUpButtonListener(ivUpButton: ImageView?) {
        ivUpButton?.setUpButtonListener {
            handleBackButtonClick(
                listOf(
                    CreateCardFragment.FRAGMENT_ID,
                    DetailCardFragment.FRAGMENT_ID
                ), false
            )
        }
    }

    override fun onBackPressed() {
        handleBackButtonClick(
            listOf(
                CreateCardFragment.FRAGMENT_ID,
                DetailCardFragment.FRAGMENT_ID
            ), true
        )

    }

    override fun onDragStart() {
        if (slidingRootNav?.isMenuClosed == true) {
            ivUpButton?.setImageResource(R.drawable.ic_arrow_back)
        } else {
            ivUpButton?.setImageResource(R.drawable.ic_menu)
        }
    }

    override fun onDragEnd(isMenuOpened: Boolean) {

    }


    //Обробатываю выбор елемента из DrawerMenu
    override fun onItemSelected(position: Int) {
        if (position == POS_LOGOUT) {
            finish()
        }

        if (position == POS_DASHBOARD) {
            slidingRootNav!!.closeMenu()
            val selectedScreen: HomeFragment = HomeFragment().newInstance(getItemsTitleForDrawerMenu(position))
            startFragment(selectedScreen, HomeFragment.FRAGMENT_ID)
        }

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.navigate_drawer, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val itemId = item.itemId
        when (itemId) {
            R.id.add_new_card -> {
                startFragment(CreateCardFragment(), CreateCardFragment.FRAGMENT_ID)
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

    private fun startFragment(fragment: Fragment, tag: String) {
        if (HomeFragment.FRAGMENT_ID != tag) {
            ivUpButton?.setImageResource(R.drawable.ic_arrow_back)
        }

        supportFragmentManager.beginTransaction()
            .addToBackStack(tag)
            .replace(R.id.fragment_container, fragment, tag)
            .commit()
    }

    private fun createItemFor(position: Int): DrawerItem<*> {
        return SimpleItem(
            getItemsIconForDrawerMenu(position),
            getItemsTitleForDrawerMenu(position)
        ).withIconTint(getColorFromResource(R.color.textColorSecondary))
            .withTextTint(getColorFromResource(R.color.textColorPrimary))
            .withSelectedIconTint(getColorFromResource(R.color.colorAccent))
            .withSelectedTextTint(getColorFromResource(R.color.colorAccent))
    }

    //Здесь задается название каждого елеменетна в менюшки Drawer.
    private fun getItemsTitleForDrawerMenu(position: Int): String {
        return arrayOf(
            "Dashboard",
            "My Awccount",
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


    private fun getColorFromResource(@ColorRes res: Int): Int {
        return ContextCompat.getColor(this, res)
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

    private fun handleBackButtonClick(listFragmentsId: List<String>, isOnBackPressed: Boolean) {
        val listFragmentsVisibility = mutableListOf<Boolean?>()

        listFragmentsId.forEach { fragmentId ->
            listFragmentsVisibility.add(fragmentManager?.findFragmentByTag(fragmentId)?.isVisible)
        }

        if (listFragmentsVisibility.any { it == true }) {
            fragmentManager?.popBackStackImmediate()
            ivUpButton?.setImageResource(R.drawable.ic_menu)
        } else {
            //Обработка нажатия onBackPressed()
            if (isOnBackPressed) {
                moveTaskToBack(true);
            } else {
                if (slidingRootNav?.isMenuOpened == true) {
                    slidingRootNav?.closeMenu()


                } else {
                    containerDrawerLayout?.visibility = View.GONE
                    slidingRootNav?.openMenu()
                }
            }

        }
    }

    private fun ImageView.setUpButtonListener(onClick: (View) -> Unit) {
        this.setOnClickListener {
            onClick(it)
        }

    }


}