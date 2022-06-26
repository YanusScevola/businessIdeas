package com.example.myapplication.presentation.core.cardstack


import android.content.ContentValues
import android.graphics.Bitmap
import android.net.Uri
import android.os.*
import android.provider.MediaStore
import android.util.Log
import android.view.*
import android.view.animation.*
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.DiffUtil
import com.example.myapplication.R
import com.example.myapplication.libraries.cardview.*
import com.example.myapplication.model.Spot
import com.example.myapplication.presentation.core.BaseCoreFragment
import com.example.myapplication.presentation.core.cardDetail.DetailCardFragment
import com.example.myapplication.utils.AnimUtils
import com.example.myapplication.utils.ScreenUtils
import com.wajahatkarim3.easyflipview.EasyFlipView
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream


class CardStackFragment : BaseCoreFragment(), CardStackListener, CardStackAdapter.OnClickListener {

    private val model: CardStackViewModel by activityViewModels()
//    private val cardStackView by lazy { view?.findViewById<CardStackView>(R.id.card_stack_view) }
//    private val manager by lazy { CardStackLayoutManager(requireContext(), this) }
//    private val adapter by lazy { CardStackAdapter(createSpots()) }

    private var adapter: CardStackAdapter? = null
    private var cardStackView: CardStackView? = null
    private var manager: CardStackLayoutManager? = null
    private val supportActionBar by lazy { (requireActivity() as (AppCompatActivity)).supportActionBar }
    private var param1: String? = null
    private var param2: String? = null
    private var ivUpButton: ImageView? = null
    private var currentFlipView: EasyFlipView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_card_stack, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(false)
        ivUpButton = requireActivity().findViewById(R.id.iv_upButton)
        cardStackView = view.findViewById(R.id.card_stack_view)

        manager = CardStackLayoutManager(requireContext(), this)
        adapter = CardStackAdapter(requireActivity(), createSpots(), this)



        initBaseFragment(ivUpButton as ImageView, this)
        setupCardStackView()
        setupButton()
    }

    override fun onCardDragging(direction: Direction, ratio: Float) {
        Log.d("CardStackView", "onCardDragging: d = ${direction.name}, r = $ratio")
    }

    override fun onCardSwiped(direction: Direction) {
        Log.d("CardStackView", "onCardSwiped: p = ${manager!!.topPosition}, d = $direction")
        if (manager!!.topPosition == adapter!!.itemCount - 5) {
            paginate()
        }
    }

    override fun onCardRewound() {
        Log.d("CardStackView", "onCardRewound: ${manager!!.topPosition}")
    }

    override fun onCardCanceled() {
        Log.d("CardStackView", "onCardCanceled: ${manager!!.topPosition}")
    }

    override fun onCardAppeared(view: View, position: Int) {
        currentFlipView = adapter?.getCardViewByPosition(0)!!

        val containerCardInfo: ConstraintLayout = currentFlipView?.findViewById(R.id.container_info)!!
        val currentCardImageView: ImageView = currentFlipView?.findViewById(R.id.item_image)!!

        containerCardInfo.visibility = View.VISIBLE
        val bitmap = ScreenUtils.getScreenShotFromView(currentFlipView!!)
        if (bitmap != null) {
            ScreenUtils.saveMediaToStorage(requireActivity(), bitmap)
        }
        currentCardImageView.setImageBitmap(bitmap)
        containerCardInfo.visibility = View.GONE




        Log.i("tag", "adapter?.getCardViewByPosition(0): ${adapter?.getCardViewByPosition(0)}")
//        Log.i("tag", "adapter: position: ${adapter?.getPosition()}")
//        Log.i("tag", "onCardAppeared: (${containerCardInfo.visibility}) | position ${position}")

    }

    override fun onCardDisappeared(view: View, position: Int) {
        currentFlipView = adapter?.getCardViewByPosition(position)!!
        val containerCardInfo: ConstraintLayout = currentFlipView?.findViewById(R.id.container_info)!!

        containerCardInfo.visibility = View.VISIBLE


//        getCurrentCardContainerInfo()?.visibility = View.GONE
//        Log.d("CardStackView", "onCardDisappeared: ($position) ${textView.text}")
//        Log.i("tag", "onCardDisappeared visibility: (${containerCardInfo.visibility}) | position: ${position}")
//        Log.i("tag", "onCardDisappeared: position: ${position}")
//        Log.i("tag", "adapter: position: ${adapter?.getPosition()}")


    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.navigation_toolbar, menu)
        return super.onCreateOptionsMenu(menu, inflater)

    }

    override fun onFlipCard(flipView: EasyFlipView, flipSide: String) {
        val containerCardInfo: ConstraintLayout = flipView.findViewById(R.id.container_info)
        val currentCardImageView: ImageView = flipView.findViewById(R.id.item_image)


        if (flipSide == "BACK_SIDE") {
            AnimUtils.startAnimation(cardStackView as View, R.anim.anim_zum_open)

            parentFragmentManager.startFragment(
                fragment = DetailCardFragment(),
                newTag = DetailCardFragment.FRAGMENT_ID,
                animation = AnimUtils.AnimFragmentTransaction(0, 0, 0, 0),
                isReplace = false,
                delayMillis = 300
            )

        } else {
//
            containerCardInfo.visibility = View.VISIBLE
            val bitmap = ScreenUtils.getScreenShotFromView(flipView)
            currentCardImageView.setImageBitmap(bitmap)
            containerCardInfo.visibility = View.GONE

        }

    }

    override fun onClickCard(flipView: EasyFlipView) {
        val containerCardInfo: ConstraintLayout = flipView.findViewById(R.id.container_info)
        val currentCardImageView: ImageView = flipView.findViewById(R.id.item_image)

        containerCardInfo.visibility = View.VISIBLE
        val bitmap = ScreenUtils.getScreenShotFromView(flipView)
        currentCardImageView.setImageBitmap(bitmap)
        containerCardInfo.visibility = View.GONE
    }

    private fun saveMediaToStorage(bitmap: Bitmap) {
        val filename = "${System.currentTimeMillis()}.jpg"
        var fos: OutputStream? = null
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            requireActivity().contentResolver?.also { resolver ->
                val contentValues = ContentValues().apply {
                    put(MediaStore.MediaColumns.DISPLAY_NAME, filename)
                    put(MediaStore.MediaColumns.MIME_TYPE, "image/jpg")
                    put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_PICTURES)
                }
                val imageUri: Uri? = resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)
                fos = imageUri?.let { resolver.openOutputStream(it) }
            }
        } else {
            val imagesDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
            val image = File(imagesDir, filename)
            fos = FileOutputStream(image)
        }
        fos?.use {
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, it)
//            Toast.makeText(this , "Captured View and saved to Gallery" , Toast.LENGTH_SHORT).show()
        }
    }


    private fun setupButton() {
        val skip = view?.findViewById<View>(R.id.skip_button)
        skip?.setOnClickListener {
            val setting = SwipeAnimationSetting.Builder()
                .setDirection(Direction.Left)
                .setDuration(Duration.Normal.duration)
                .setInterpolator(AccelerateInterpolator())
                .build()
            manager!!.setSwipeAnimationSetting(setting)
            cardStackView?.swipe()
        }

        val rewind = view?.findViewById<View>(R.id.rewind_button)
        rewind?.setOnClickListener {
            val setting = RewindAnimationSetting.Builder()
                .setDirection(Direction.Bottom)
                .setDuration(Duration.Normal.duration)
                .setInterpolator(DecelerateInterpolator())
                .build()
            manager!!.setRewindAnimationSetting(setting)
            cardStackView?.rewind()
        }

        val like = view?.findViewById<View>(R.id.like_button)
        like?.setOnClickListener {
            val setting = SwipeAnimationSetting.Builder()
                .setDirection(Direction.Right)
                .setDuration(Duration.Normal.duration)
                .setInterpolator(AccelerateInterpolator())
                .build()
            manager!!.setSwipeAnimationSetting(setting)
            cardStackView?.swipe()
        }
    }

    private fun setupCardStackView() {
        manager!!.setStackFrom(StackFrom.None)
        manager!!.setVisibleCount(3)
        manager!!.setTranslationInterval(8.0f)
        manager!!.setScaleInterval(0.95f)
        manager!!.setSwipeThreshold(0.3f)
        manager!!.setMaxDegree(20.0f)
        manager!!.setDirections(Direction.HORIZONTAL)
        manager!!.setCanScrollHorizontal(true)
        manager!!.setCanScrollVertical(true)
        manager!!.setSwipeableMethod(SwipeableMethod.AutomaticAndManual)
        manager!!.setOverlayInterpolator(LinearInterpolator())
        cardStackView?.layoutManager = manager
        cardStackView?.adapter = adapter
        cardStackView?.itemAnimator.apply {
            if (this is DefaultItemAnimator) {
                supportsChangeAnimations = false
            }
        }
    }

    private fun paginate() {
        val old = adapter!!.getSpots()
        val new = old.plus(createSpots())
        val callback = SpotDiffCallback(old, new)
        val result = DiffUtil.calculateDiff(callback)
        adapter!!.setSpots(new)
        result.dispatchUpdatesTo(adapter!!)
    }

    private fun reload() {
        val old = adapter!!.getSpots()
        val new = createSpots()
        val callback = SpotDiffCallback(old, new)
        val result = DiffUtil.calculateDiff(callback)
        adapter!! setSpots (new)
        result.dispatchUpdatesTo(adapter!!)
    }

    private fun addFirst(size: Int) {
        val old = adapter!!.getSpots()
        val new = mutableListOf<Spot>().apply {
            addAll(old)
            for (i in 0 until size) {
                add(manager!!.topPosition, createSpot())
            }
        }
        val callback = SpotDiffCallback(old, new)
        val result = DiffUtil.calculateDiff(callback)
        adapter!!.setSpots(new)
        result.dispatchUpdatesTo(adapter!!)
    }

    private fun addLast(size: Int) {
        val old = adapter!!.getSpots()
        val new = mutableListOf<Spot>().apply {
            addAll(old)
            addAll(List(size) { createSpot() })
        }
        val callback = SpotDiffCallback(old, new)
        val result = DiffUtil.calculateDiff(callback)
        adapter!!.setSpots(new)
        result.dispatchUpdatesTo(adapter!!)
    }

    private fun removeFirst(size: Int) {
        if (adapter!!.getSpots().isEmpty()) {
            return
        }

        val old = adapter!!.getSpots()
        val new = mutableListOf<Spot>().apply {
            addAll(old)
            for (i in 0 until size) {
                removeAt(manager!!.topPosition)
            }
        }
        val callback = SpotDiffCallback(old, new)
        val result = DiffUtil.calculateDiff(callback)
        adapter!!.setSpots(new)
        result.dispatchUpdatesTo(adapter!!)
    }

    private fun removeLast(size: Int) {
        if (adapter!!.getSpots().isEmpty()) {
            return
        }

        val old = adapter!!.getSpots()
        val new = mutableListOf<Spot>().apply {
            addAll(old)
            for (i in 0 until size) {
                removeAt(this.size - 1)
            }
        }
        val callback = SpotDiffCallback(old, new)
        val result = DiffUtil.calculateDiff(callback)
        adapter!!.setSpots(new)
        result.dispatchUpdatesTo(adapter!!)
    }

    private fun replace() {
        val old = adapter!!.getSpots()
        val new = mutableListOf<Spot>().apply {
            addAll(old)
            removeAt(manager!!.topPosition)
            add(manager!!.topPosition, createSpot())
        }
        adapter!!.setSpots(new)
        adapter!!.notifyItemChanged(manager!!.topPosition)
    }

    private fun swap() {
        val old = adapter!!.getSpots()
        val new = mutableListOf<Spot>().apply {
            addAll(old)
            val first = removeAt(manager!!.topPosition)
            val last = removeAt(this.size - 1)
            add(manager!!.topPosition, last)
            add(first)
        }
        val callback = SpotDiffCallback(old, new)
        val result = DiffUtil.calculateDiff(callback)
        adapter!!.setSpots(new)
        result.dispatchUpdatesTo(adapter!!)
    }

    private fun createSpot(): Spot {
        return Spot(
            name = "Yasaka Shrine",
            city = "Сумма инвестиций",
            url = "https://source.unsplash.com/Xq1ntWruZQI/600x800"
        )
    }

    private fun createSpots(): List<Spot> {
        val spots = ArrayList<Spot>()
        model.spotArray.observe(viewLifecycleOwner) {
            spots.addAll(it)
        }

        return spots
    }

    fun getCurrentCardEasyFlipView(): EasyFlipView? {
        return currentFlipView
    }


    companion object {
        const val FRAGMENT_ID = "CardStackFragment"
        private const val ARG_PARAM1 = "param1"
        private const val ARG_PARAM2 = "param2"

        @JvmStatic
        fun newInstance() =
            CardStackFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }


}