package com.example.globars.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.globars.R
import com.example.globars.data.pojo.units.TrackingUnit
import com.example.globars.ui.maps.GoogleMapsActivity


class TrackingListAdapter(
    private var trackingData: List<TrackingUnit>?,
    private val mapsActivity: GoogleMapsActivity,
): RecyclerView.Adapter<TrackingListAdapter.ViewHolder>() {
    class ViewHolder(v: View, private val mapsActivity: GoogleMapsActivity) :
        RecyclerView.ViewHolder(v) {
        init {
            v.setOnClickListener { _ ->
                val trackingUnit = itemView.tag as TrackingUnit
                mapsActivity.moveToMark(trackingUnit)
            }
        }

        val nameTextView: TextView = v.findViewById(R.id.trackingNameTextView) as TextView
    }

    fun setTrackingData(data: List<TrackingUnit>) {
        this.trackingData = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val vh = ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.tracking_item, parent, false),
            mapsActivity
        )
        return vh
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        trackingData?.let {
            val trackingUnit = it[position]
            holder.itemView.tag = trackingUnit
            holder.nameTextView.text = trackingUnit.name
        }
    }

    override fun getItemCount(): Int {
        return trackingData?.size?: 0
    }
}