package com.jetbrains.handson.mpp.mobile

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.soywiz.klock.DateFormat
import com.soywiz.klock.parse

class LiveTrainsAdapter :
    RecyclerView.Adapter<LiveTrainsAdapter.LiveTrainsViewHolder>() {
    private var journeys = emptyList<Journey>()

    class LiveTrainsViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        private lateinit var journey: Journey

        fun bindItems(journey: Journey) {
            this.journey = journey

            val departureTime = view.findViewById<TextView>(R.id.departure_time)
            val destinationStation = view.findViewById<TextView>(R.id.destination_station)
            val status = view.findViewById<TextView>(R.id.status)

            departureTime.text = processTime(journey.departureTime)
            destinationStation.text = journey.destinationStation.displayName
            status.text = journey.status
        }

        private fun processTime(timeString: String): String {
            val dateFormat = DateFormat("YYYY-MM-dd'T'HH:mm:ss.SSSXXX")
            val time = dateFormat.parse(timeString)
            return time.format("HH:mm")
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): LiveTrainsViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.live_train, parent, false)

        return LiveTrainsViewHolder(view)
    }

    override fun onBindViewHolder(holder: LiveTrainsViewHolder, position: Int) {
        holder.bindItems(journeys[position])
    }

    override fun getItemCount(): Int {
        return journeys.size
    }

    fun updateData(journeys: List<Journey>) {
        this.journeys = journeys
        notifyDataSetChanged()
    }
}