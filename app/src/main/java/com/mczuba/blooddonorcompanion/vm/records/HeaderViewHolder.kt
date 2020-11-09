package com.mczuba.blooddonorcompanion.vm.records

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mczuba.blooddonorcompanion.R

class HeaderViewHolder(view: View ) : RecyclerView.ViewHolder(view) {
    fun onBind(header: String) {
        this.itemView.findViewById<TextView>(R.id.item_year_header).text = header
    }
}