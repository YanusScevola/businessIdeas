package com.example.myapplication.presentation.core

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.annotation.ColorInt
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
import com.example.myapplication.libraries.drawerview.callback.DragListener
import com.example.myapplication.libraries.drawerview.callback.DragStateListener
import com.example.myapplication.presentation.core.cardCreate.CreateCardFragment
import com.example.myapplication.presentation.core.home.HomeFragment
import com.google.android.material.navigation.NavigationView


class NavigateDrawerActivity : AppCompatActivity(), DrawerAdapter.OnItemSelectedListener {
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
        supportActionBar?.setDisplayHomeAsUpEnabled(false)


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


        val drawerRecyclerviewMenuList = findViewById<RecyclerView>(R.id.drawer_recyclerview)
        drawerRecyclerviewMenuList.isNestedScrollingEnabled = false
        drawerRecyclerviewMenuList.layoutManager = LinearLayoutManager(this)
        drawerRecyclerviewMenuList.adapter = adapter

        adapter.setSelected(POS_DASHBOARD)

    }

    //Обробатываю выбор елемента из DrawerMenu
    override fun onItemSelected(position: Int) {
        if (position == POS_LOGOUT) {
            finish()
        }
        slidingRootNav!!.closeMenu()
        val selectedScreen: HomeFragment = HomeFragment().newInstance(getItemsTitleForDrawerMenu(position))
        startFragment(selectedScreen, HomeFragment.FRAGMENT_ID)
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

    override fun onSupportNavigateUp(): Boolean {

        return super.onSupportNavigateUp()
    }


    override fun onBackPressed() {
        val fragment = supportFragmentManager.findFragmentByTag(CreateCardFragment.FRAGMENT_ID)
//        if (fragment?.isVisible == true){
//            slidingRootNav?.isMenuLocked = false
//        }


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

    private fun startFragment(fragment: Fragment, tag: String) {
        supportFragmentManager.beginTransaction()
            .addToBackStack(tag)
            .replace(R.id.fragment_container, fragment, tag)
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

    @ColorInt
    private fun color(@ColorRes res: Int): Int {
        return ContextCompat.getColor(this, res)
    }
    var isDrawerOpened = false
//    //Здесь побработаю действия после закрытия DrawerMenu
//    override fun onDragStart() {
//
//
//        val fragment = supportFragmentManager.findFragmentByTag(CreateCardFragment.FRAGMENT_ID)
//        if (fragment?.isVisible == true) {
//            if (isDrawerOpened == false){
//                isDrawerOpened = true
//                slidingRootNav?.closeMenu(true)
//                slidingRootNav?.isMenuLocked = true
//                supportFragmentManager.popBackStack()
//                slidingRootNav?.isMenuLocked = false
//
//            }else{
//                isDrawerOpened = false
//            }
//
//        }else{
//
//        }
//
//
//        Log.d("tag", "$isDrawerOpened")
//        Log.d("tag", "isMenuOpened = ${slidingRootNav?.isMenuOpened}")
//    }
//
//    //Здесь побработаю действия после закрытия DrawerMenu
//    override fun onDragEnd(isMenuOpened: Boolean) {
//
//
//        var isDrawerOpened = isMenuOpened
////        Log.d("tag", " onDragEnd = $isDrawerOpened")
//    }

}