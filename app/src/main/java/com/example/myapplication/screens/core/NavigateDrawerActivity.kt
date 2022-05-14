package com.example.myapplication.screens.core


import android.os.Bundle
import android.view.Menu
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import com.example.myapplication.R
import com.example.myapplication.databinding.ActivityNavigateDrawerBinding
import com.example.myapplication.screens.core.home.HomeFragment
import com.google.android.material.navigation.NavigationView
import com.example.myapplication.drawer.views.DuoDrawerLayout
import com.example.myapplication.drawer.views.DuoMenuView
import com.example.myapplication.drawer.widgets.DuoDrawerToggle
import com.example.myapplication.screens.core.cardCreate.CreateCardFragment
import java.util.*
import kotlin.collections.ArrayList


public class NavigateDrawerActivity : AppCompatActivity(), DuoMenuView.OnMenuClickListener{
    private lateinit var appBarConfiguration: AppBarConfiguration
    private var binding: ActivityNavigateDrawerBinding? = null

    private var mMenuAdapter: MenuAdapter? = null
    private var mViewHolder: ViewHolder? = null



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNavigateDrawerBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        setSupportActionBar(binding?.appBarNavigateDrawer?.toolbar)

//        val drawerLayout: DrawerLayout? = binding?.drawerLayout
//        val navView: NavigationView? = binding?.navView
        val navController = findNavController(R.id.nav_fragment_navigate_drawer)
//

        var mTitles = ArrayList(Arrays.asList(resources.getStringArray(R.array.menuOptions))).toList()

        // Initialize the views

        // Initialize the views
        mViewHolder = ViewHolder(this)



        // Handle toolbar actions
        handleToolbar()


        // Handle menu actions
        handleMenu(mTitles)


        // Handle drawer actions
        handleDrawer()



        // Show main fragment in container
        goToFragment(CreateCardFragment(), false)
        mMenuAdapter?.setViewSelected(0, true)
        title = mTitles[0].toString()



//        appBarConfiguration = AppBarConfiguration(
//            setOf(
//                R.id.nav_home,
//                R.id.nav_my_office,
//                R.id.nav_community,
//                R.id.nav_about_business,
//                R.id.nav_donat
//            ), null
//        )
////
//        navView?.setupWithNavController(navController)
//        setupActionBarWithNavController(navController, appBarConfiguration)

    }


    private fun handleMenu(mTitles: List<Array<String>>) {
        mMenuAdapter = MenuAdapter(listOf("ssssssqqw", "ssssssqqw", "ssssssqqw", "ssssssqqw", "ssssssqqw",))
        mViewHolder?.mDuoMenuView?.setOnMenuClickListener(this)
        mViewHolder?.mDuoMenuView?.adapter = mMenuAdapter
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.navigate_drawer, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_fragment_navigate_drawer)
        navController.navigateUp(appBarConfiguration)
        return navController.navigateUp() || super.onSupportNavigateUp()
    }



    override fun onBackPressed() {
        val fragmentManager: FragmentManager = supportFragmentManager
        if (fragmentManager.backStackEntryCount > 1) {
            fragmentManager.popBackStackImmediate()
        } else {
            super.onBackPressed()
        }
    }

    override fun onStop() {
        super.onStop()
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }

    private fun handleToolbar() {
        setSupportActionBar(mViewHolder!!.mToolbar)
    }

    private fun handleDrawer() {
        val duoDrawerToggle = DuoDrawerToggle(
            this,
            mViewHolder!!.mDuoDrawerLayout,
            mViewHolder!!.mToolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        mViewHolder!!.mDuoDrawerLayout.setDrawerListener(duoDrawerToggle)
        duoDrawerToggle.syncState()
    }


    override fun onFooterClicked() {
        Toast.makeText(this, "onFooterClicked", Toast.LENGTH_SHORT).show()
    }

    override fun onHeaderClicked() {
        Toast.makeText(this, "onHeaderClicked", Toast.LENGTH_SHORT).show()
    }

    private fun goToFragment(fragment: Fragment, addToBackStack: Boolean) {
        val transaction: FragmentTransaction = supportFragmentManager.beginTransaction()
        if (addToBackStack) {
            transaction.addToBackStack(null)
        }
        transaction.add(R.id.container, fragment).commit()
    }

    override fun onOptionClicked(position: Int, objectClicked: Any?) {
        // Set the toolbar title
//        title = mTitles[position]

        // Set the right options selected
        mMenuAdapter!!.setViewSelected(position, true)
        when (position) {
            else -> goToFragment(CreateCardFragment(), false)
        }

        // Close the drawer
        mViewHolder!!.mDuoDrawerLayout.closeDrawer()
    }

    private class ViewHolder internal constructor(view: NavigateDrawerActivity) {
        val mDuoDrawerLayout: DuoDrawerLayout
        val mDuoMenuView: DuoMenuView
        val mToolbar: Toolbar

        init {
            mDuoDrawerLayout = view.findViewById<View>(R.id.drawer_layout) as DuoDrawerLayout
            mDuoMenuView = mDuoDrawerLayout.menuView as DuoMenuView
            mToolbar = view.findViewById<View>(R.id.toolbar) as Toolbar
        }
    }


}