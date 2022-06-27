package com.example.myapplication.presentation.core.cardstack

import android.annotation.SuppressLint
import android.annotation.TargetApi
import android.app.Activity
import android.os.Build
import android.util.Log
import android.view.*
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myapplication.R
import com.example.myapplication.model.Spot
import com.example.myapplication.utils.ScreenUtils
import com.wajahatkarim3.easyflipview.EasyFlipView
import kotlinx.coroutines.*


class CardStackAdapter(
    private var activity: Activity,
    private var spots: List<Spot> = emptyList(),
    private val onClickListener: OnClickListener
) : RecyclerView.Adapter<CardStackAdapter.ViewHolder>() {
    var cardViewHolderList = mutableListOf<ViewHolder>()
    var listener: ViewTreeObserver.OnGlobalLayoutListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(onClickListener, inflater.inflate(R.layout.item_spot, parent, false))
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val spot = spots[position]
//        holder.name.text = "${spot.id}. ${spot.name}"
//        holder.city.text = Html.fromHtml("<p style=\"text-align: center\">centered text example</p>", HtmlCompat.FROM_HTML_MODE_LEGACY).toString()
        holder.name.text = "${spot.name}"
        cardViewHolderList.add(holder)

        Glide.with(holder.image)
            .load(spot.url)
            .into(holder.image)

        holder.itemView.setOnClickListener { v ->
            Toast.makeText(v.context, spot.name, Toast.LENGTH_SHORT).show()
        }

        holder.easyFlipView.setOnTouchListener { view, motionEvent ->
            if (motionEvent.action == MotionEvent.ACTION_UP) {
                onClickListener.onClickCard(holder.easyFlipView)
            }
            return@setOnTouchListener true
        }

        holder.easyFlipView.setOnFlipListener { flipView, newCurrentSide ->
            onClickListener.onFlipCard(flipView, newCurrentSide.toString())
        }


        holder.easyFlipView.viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                if (Build.VERSION.SDK_INT > 16) {
                    holder.easyFlipView.viewTreeObserver.removeOnGlobalLayoutListener(this);
                } else {
                    holder.easyFlipView.viewTreeObserver.removeGlobalOnLayoutListener(this);
                }


                GlobalScope.launch {
                    delay(500)
                    Log.i("tag", "1")
                    activity.runOnUiThread {
                        holder.containerInfo.visibility = View.VISIBLE
                        val bitmap = ScreenUtils.getScreenShotFromView(holder.easyFlipView)
//                      ScreenUtils.saveMediaToStorage(activity, bitmap!!)
                        holder.image.setImageBitmap(bitmap)
                        holder.containerInfo.visibility = View.GONE
                    }
                }

//                holder.easyFlipView.viewTreeObserver.removeGlobalOnLayoutListener(listener)
//                holder.easyFlipView.getViewTreeObserver().addOnGlobalLayoutListener(this);

            }

        })


    }

    override fun getItemCount(): Int {
        return spots.size
    }

    infix fun setSpots(spots: List<Spot>) {
        this.spots = spots
    }

    fun getSpots(): List<Spot> {
        return spots
    }

    fun getCardViewHolderList(position: Int): ViewHolder {
        return cardViewHolderList[position]
    }

    class ViewHolder(onClickListener: OnClickListener, view: View) : RecyclerView.ViewHolder(view) {
        val name: TextView = view.findViewById(R.id.item_name)

        //        var city: TextView = view.findViewById(R.id.tv_donat_invest)
        var image: ImageView = view.findViewById(R.id.item_image)
        val easyFlipView: EasyFlipView = view.findViewById(R.id.easy_flip_view)
        val containerInfo: ConstraintLayout = view.findViewById(R.id.container_info)

    }

    interface OnClickListener {
        fun onFlipCard(flipView: EasyFlipView, flipSide: String)
        fun onClickCard(flipView: EasyFlipView)
    }
}
