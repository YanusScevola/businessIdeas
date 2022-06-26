package com.example.myapplication.presentation.core.cardstack

import android.annotation.SuppressLint
import android.app.Activity
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.compose.runtime.currentCompositeKeyHash
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myapplication.R
import com.example.myapplication.model.Spot
import com.example.myapplication.utils.ScreenUtils
import com.wajahatkarim3.easyflipview.EasyFlipView


class CardStackAdapter(
    private var activity: Activity,
    private var spots: List<Spot> = emptyList(),
    private val onClickListener: OnClickListener
) : RecyclerView.Adapter<CardStackAdapter.ViewHolder>() {
    var viewHolders = mutableListOf<ViewHolder>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(onClickListener, inflater.inflate(R.layout.item_spot, parent, false))
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        viewHolders.add(holder)

        val spot = spots[position]
        holder.name.text = "${spot.name}"

        Glide.with(holder.image)
            .load(spot.url)
            .into(holder.image)



        holder.itemView.setOnClickListener { v ->
            Toast.makeText(v.context, spot.name, Toast.LENGTH_SHORT).show()

        }

        holder.easyFlipView.setOnTouchListener { view, motionEvent ->
            if (motionEvent.action == MotionEvent.ACTION_UP) {

                Glide.with(viewHolders.get(position).image)
                    .load(spot.url)
                    .into(viewHolders.get(position).image)

                onClickListener.onClickCard(viewHolders.get(position).easyFlipView)
            }

            return@setOnTouchListener true
        }

        holder.easyFlipView.setOnFlipListener { flipView, newCurrentSide ->
            onClickListener.onFlipCard(flipView, newCurrentSide.toString())

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

    fun getCardViewByPosition(position: Int): EasyFlipView {
        return viewHolders[position].easyFlipView
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
