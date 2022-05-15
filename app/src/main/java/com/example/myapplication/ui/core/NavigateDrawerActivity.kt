package com.example.myapplication.ui.core

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.Menu
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.navigation.ui.AppBarConfiguration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.databinding.ActivityNavigateDrawerBinding
import com.example.myapplication.drawer.SlidingRootNav
import com.example.myapplication.drawer.SlidingRootNavBuilder
import com.example.myapplication.ui.core.home.HomeFragment


class NavigateDrawerActivity : AppCompatActivity(), DrawerAdapter.OnItemSelectedListener {
    private lateinit var appBarConfiguration: AppBarConfiguration
    private var binding: ActivityNavigateDrawerBinding? = null
    private var slidingRootNav: SlidingRootNav? = null

    private val POS_DASHBOARD = 0
    private val POS_ACCOUNT = 1
    private val POS_MESSAGES = 2
    private val POS_CART = 3
    private val POS_LOGOUT = 5

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNavigateDrawerBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        setSupportActionBar(binding?.toolbarContainer?.toolbar)
        title = ""

        slidingRootNav = SlidingRootNavBuilder(this)
            .withToolbarMenuToggle(binding?.toolbarContainer?.toolbar)
            .withMenuOpened(false)
            .withContentClickableWhenMenuOpened(true)
            .withSavedState(savedInstanceState)
            .withMenuLayout(R.layout.left_drawer_layout)
            .inject()


        val adapter = DrawerAdapter(
            listOf(
                createItemFor(POS_DASHBOARD).setChecked(true),
                createItemFor(POS_ACCOUNT),
                createItemFor(POS_MESSAGES),
                createItemFor(POS_CART),
                SpaceItem(48),
                createItemFor(POS_LOGOUT)
            )
        )

        adapter.setListener(this)

        val list = findViewById<RecyclerView>(R.id.list)
        list.isNestedScrollingEnabled = false
        list.layoutManager = LinearLayoutManager(this)
        list.adapter = adapter

        adapter.setSelected(POS_DASHBOARD)

    }


    override fun onItemSelected(position: Int) {
        if (position == POS_LOGOUT) {
            finish()
        }
        slidingRootNav!!.closeMenu()
        val selectedScreen: HomeFragment = HomeFragment().createFor(getItemsTitleForDrawerMenu(position))
        showFragment(selectedScreen)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.navigate_drawer, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        return super.onSupportNavigateUp()
    }


    override fun onBackPressed() {
        val fragmentManager: FragmentManager = supportFragmentManager
        if (fragmentManager.backStackEntryCount > 1) {
            fragmentManager.popBackStackImmediate()
        } else {
            super.onBackPressed()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }

    private fun showFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .addToBackStack("")
            .replace(R.id.container, fragment)
            .commit()
    }

    private fun createItemFor(position: Int): DrawerItem<*> {
        return SimpleItem(getItemsIconForDrawerMenu(position), getItemsTitleForDrawerMenu(position))
            .withIconTint(color(R.color.textColorSecondary))
            .withTextTint(color(R.color.textColorPrimary))
            .withSelectedIconTint(color(R.color.colorAccent))
            .withSelectedTextTint(color(R.color.colorAccent))
    }

    private fun getItemsTitleForDrawerMenu(position: Int): String {
        return arrayOf("Dashboard", "My Account", "My Account", "My Account", "My Account", "Log Out")[position]
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

    @ColorInt
    private fun color(@ColorRes res: Int): Int {
        return ContextCompat.getColor(this, res)
    }

}