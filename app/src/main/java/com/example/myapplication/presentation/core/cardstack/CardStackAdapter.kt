package com.example.myapplication.presentation.core.cardstack

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myapplication.R
import com.example.myapplication.model.Spot
import com.wajahatkarim3.easyflipview.EasyFlipView


class CardStackAdapter(
        private var spots: List<Spot> = emptyList(),
        private val onClickListener: OnClickListener
) : RecyclerView.Adapter<CardStackAdapter.ViewHolder>() {
    var easyFlipView: EasyFlipView? = null
    var containerCardInfo: ConstraintLayout? = null
    var containerCardImage: ImageView? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(onClickListener ,inflater.inflate(R.layout.item_spot, parent, false))
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val spot = spots[position]
//        holder.name.text = "${spot.id}. ${spot.name}"
        holder.name.text = "${spot.name}"
//        holder.city.text = Html.fromHtml("<p style=\"text-align: center\">centered text example</p>", HtmlCompat.FROM_HTML_MODE_LEGACY).toString()
        easyFlipView = holder.easyFlipView
        containerCardInfo = holder.containerInfo
        containerCardImage = holder.image


        Glide.with(holder.image)
            .load(spot.url)
            .into(holder.image)

        Glide.with(holder.image)
            .load(spot.url)
            .into(containerCardImage!!)

        holder.itemView.setOnClickListener { v ->
            Toast.makeText(v.context, spot.name, Toast.LENGTH_SHORT).show()

        }

        holder.easyFlipView.setOnTouchListener { view, motionEvent ->
            if(motionEvent.action == MotionEvent.ACTION_UP){

                Glide.with(holder.image)
                    .load(spot.url)
                    .into(holder.image)

                Glide.with(holder.image)
                    .load(spot.url)
                    .into(containerCardImage!!)

                onClickListener.onClickCard (holder.easyFlipView, holder.image)


            }
            return@setOnTouchListener true
        }

        holder.easyFlipView.setOnFlipListener { flipView, newCurrentSide ->
            onClickListener.onFlip(flipView, newCurrentSide.toString())
            easyFlipView = holder.easyFlipView

            Glide.with(holder.image)
                .load(spot.url)
                .into(containerCardImage!!)

        }

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

    fun getFlipView(): EasyFlipView? {
        return easyFlipView
    }

    fun getContainerInfo(): ConstraintLayout? {
        return containerCardInfo
    }

    fun getImageView(): ImageView? {
        return containerCardImage
    }

    class ViewHolder(onClickListener: OnClickListener, view: View) : RecyclerView.ViewHolder(view) {
        val name: TextView = view.findViewById(R.id.item_name)
//        var city: TextView = view.findViewById(R.id.tv_donat_invest)
        var image: ImageView = view.findViewById(R.id.item_image)
        val easyFlipView: EasyFlipView = view.findViewById(R.id.easy_flip_view)
        val containerInfo: ConstraintLayout = view.findViewById(R.id.container_info)


        init {


        }


    }



    interface OnClickListener {
        fun onFlip(flipView: EasyFlipView, flipSide: String)
        fun onClickCard(flipView: EasyFlipView, image: ImageView)
    }

}
