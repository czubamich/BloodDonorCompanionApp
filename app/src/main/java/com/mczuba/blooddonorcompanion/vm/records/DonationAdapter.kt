package com.mczuba.blooddonorcompanion.vm.records

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mczuba.blooddonorcompanion.R
import com.mczuba.blooddonorcompanion.databinding.ItemDonationBinding

const val TYPE_HEADER = 0
const val TYPE_ITEM = 1

class DonationAdapter(var context: Context, val donations: ArrayList<DonationData>) : RecyclerView.Adapter<RecyclerView.ViewHolder>(), StickyHeaderItemDecoration.StickyHeaderInterface {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType != TYPE_HEADER) {
            val layoutInflater = LayoutInflater.from(parent.context)
            val itemBinding = ItemDonationBinding.inflate(layoutInflater, parent, false)
            return DonationViewHolder(itemBinding)
        } else {
            return HeaderViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_year, parent, false))
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if(holder is DonationViewHolder)
            holder.onBind(donations[position])
        else if(holder is HeaderViewHolder)
            holder.onBind(donations[position].getYear())
    }

    override fun getItemCount() = donations.count()

    override fun getHeaderPositionForItem(itemPosition: Int): Int {
        var headerPosition = 0
        var position = itemPosition
        do {
            if (this.isHeader(position)) {
                headerPosition = position
                break
            }
            position -= 1
        } while (position >= 0)
        return headerPosition
    }

    override fun getItemViewType(position: Int): Int {
        return if (isHeader(position)) TYPE_HEADER else TYPE_ITEM
    }

    override fun getHeaderLayout(headerPosition: Int): Int {
        return R.layout.item_year
    }

    override fun bindHeaderData(header: View, headerPosition: Int) {
        //Binding inside viewholder
    }

    override fun isHeader(itemPosition: Int): Boolean {
        return donations[itemPosition].header
    }
}