package com.jetbrains.handson.mpp.mobile

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class LiveTrainsAdapter(private val trains: List<String>) :
    RecyclerView.Adapter<LiveTrainsAdapter.LiveTrainsViewHolder>() {
    class LiveTrainsViewHolder(val textView: TextView) : RecyclerView.ViewHolder(textView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LiveTrainsViewHolder {
        val textView = LayoutInflater.from(parent.context)
            .inflate(R.layout.live_train, parent, false) as TextView

        return LiveTrainsViewHolder(textView)
    }

    override fun onBindViewHolder(holder: LiveTrainsViewHolder, position: Int) {
        holder.textView.text = trains[position]
    }

    override fun getItemCount(): Int {
        return trains.size
    }
}