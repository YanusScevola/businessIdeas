package com.example.myapplication.presentation.core.home

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myapplication.R
import com.example.myapplication.model.Spot
import com.wajahatkarim3.easyflipview.EasyFlipView
import kotlin.math.log


class CardStackAdapter(
        private var spots: List<Spot> = emptyList(),
        private val onClickListener: OnClickListener
) : RecyclerView.Adapter<CardStackAdapter.ViewHolder>() {
    var easyFlipView: EasyFlipView? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(onClickListener ,inflater.inflate(R.layout.item_spot, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val spot = spots[position]
//        holder.name.text = "${spot.id}. ${spot.name}"
        holder.name.text = "${spot.name}"
        holder.city.text = spot.city

        Glide.with(holder.image)
            .load(spot.url)
            .into(holder.image)

        holder.itemView.setOnClickListener { v ->
            Toast.makeText(v.context, spot.name, Toast.LENGTH_SHORT).show()
        }

        holder.easyFlipView.setOnFlipListener { flipView, newCurrentSide ->
            onClickListener.onFlip(flipView, newCurrentSide.toString())
            easyFlipView = flipView
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

    class ViewHolder(onClickListener: OnClickListener, view: View) : RecyclerView.ViewHolder(view) {
        val name: TextView = view.findViewById(R.id.item_name)
        var city: TextView = view.findViewById(R.id.item_city)
        var image: ImageView = view.findViewById(R.id.item_image)
        val easyFlipView: EasyFlipView = view.findViewById(R.id.easy_flip_view)


        init {


        }


    }



    interface OnClickListener {
        fun onFlip(flipView: EasyFlipView, flipSide: String)
    }

}
