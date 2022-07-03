package com.example.myapplication.presentation.core

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.databinding.ActivityNavigateDrawerBinding
import com.example.myapplication.libraries.drawerview.*
import com.example.myapplication.libraries.drawerview.callback.DragStateListener
import com.example.myapplication.presentation.core.cardCreate.CreateCardFragment
import com.example.myapplication.presentation.core.cardDetail.DetailCardFragment
import com.example.myapplication.presentation.core.cardstack.CardStackFragment
import com.example.myapplication.utils.AnimUtils


class NavigateDrawerActivity : BaseCoreActivity(), DrawerAdapter.OnItemSelectedListener, DragStateListener {
    private var binding: ActivityNavigateDrawerBinding? = null
    private var slidingRootNav: SlidingRootNav? = null
    private var btnUpButton: ImageView? = null
    private var btnAddNewCard: ImageView? = null

    private var fragmentManager: FragmentManager? = null
    private var cardStackFragment: CardStackFragment? = null
    private var cardCreateFragment: CreateCardFragment? = null

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

        fragmentManager = supportFragmentManager
        btnUpButton = binding?.toolbarContainer?.btnUpButton
        btnAddNewCard = binding?.toolbarContainer?.btnAddNewCard

        cardStackFragment = CardStackFragment.newInstance()
        cardCreateFragment = CreateCardFragment.newInstance()

        initBaseActivity(
            upButton = btnUpButton,
            addNewCard = btnAddNewCard
        )

        initDrawer(savedInstanceState)

    }

    override fun onBackButtonOnClick(view: ImageView?, isOnBackPressed: Boolean) {
        when (true) {
            fragmentManager?.isFragmentVisible(CreateCardFragment.FRAGMENT_ID) -> {
                fragmentManager?.popBackStack()
                btnUpButton?.setImageResource(R.drawable.ic_menu)
            }

            fragmentManager?.isFragmentVisible(DetailCardFragment.FRAGMENT_ID) -> {
                fragmentManager?.popBackStack()
                btnUpButton?.setImageResource(R.drawable.ic_menu)

                val easyFlipView = cardStackFragment?.getCurrentCardEasyFlipView()
                val cardStackView = findViewById<LinearLayout>(R.id.card_stack_view_container)

                AnimUtils.startAnimationCardOpenDetail(cardStackFragment?.view as ViewGroup, cardStackView, cardStackFragment!!.getCurrentCardEasyFlipView()?.findViewById(R.id.card_view_back), true)

                Handler(Looper.getMainLooper()).postDelayed({
                    cardStackFragment?.getCardAdapter()?.notifyItemRangeChanged(cardStackFragment!!.getCurrentCardPosition(), (cardStackFragment!!.getCurrentCardPosition() + 3))
                    easyFlipView?.setFlipTypeFromBack()
                    easyFlipView?.flipTheView()
                    easyFlipView?.setFlipTypeFromFront()
                }, 300)

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

    //Обрабатываею нажатия по кнопкам в Toolbar
    override fun onClickToolbarButton(id: Int, view: View?, isClicked: Boolean) {
        when(id){

            R.id.action_settings -> {

            }

            R.id.btn_add_new_card -> {
                fragmentManager?.startFragment(
                    fragment = cardCreateFragment!!,
                    newTag = CreateCardFragment.FRAGMENT_ID,
                    fragmentContainerId = R.id.fragment_container,
                    animation = AnimUtils.AnimFragmentTransaction(R.anim.anim_slide_down, 0, 0, R.anim.anim_slide_up),
                    isReplace = false,
                    delayMillis = 0
                )
            }

        }
    }

    //Обробатываю выбор елемента из DrawerMenu
    override fun onDrawerItemSelected(position: Int) {
        if (position == POS_LOGOUT) {
            finish()
        }

        if (position == POS_DASHBOARD) {
            slidingRootNav!!.closeMenu()
            fragmentManager?.startFragment(
                fragment = cardStackFragment!!,
                newTag = CardStackFragment.FRAGMENT_ID,
                fragmentContainerId = R.id.fragment_container,
                animation = null,
                isReplace = true,
                delayMillis = 0
            )
        }

    }

    override fun onDragStart() {

    }

    override fun onDragEnd(isMenuOpened: Boolean) {
        if (slidingRootNav?.isMenuClosed != true) {
            btnUpButton?.setImageResource(R.drawable.ic_arrow_back)
        } else {
            btnUpButton?.setImageResource(R.drawable.ic_menu)
        }
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

    //Задается название каждого елеменетна в менюшки Drawer.
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