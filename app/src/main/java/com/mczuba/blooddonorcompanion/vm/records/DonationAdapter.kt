package com.mczuba.blooddonorcompanion.vm.records

import android.content.Context
import android.view.*
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.mczuba.blooddonorcompanion.R
import com.mczuba.blooddonorcompanion.data.models.Donation
import com.mczuba.blooddonorcompanion.databinding.ItemDonationBinding

const val TYPE_HEADER = 0
const val TYPE_ITEM = 1

class DonationAdapter(var context: Context, val donations: ArrayList<DonationData>) : RecyclerView.Adapter<RecyclerView.ViewHolder>(), StickyHeaderItemDecoration.StickyHeaderInterface, View.OnCreateContextMenuListener {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType != TYPE_HEADER) {
            val layoutInflater = LayoutInflater.from(parent.context)
            val itemBinding = ItemDonationBinding.inflate(layoutInflater, parent, false)

            return DonationViewHolder(itemBinding, context)
        } else {
            return HeaderViewHolder(
                LayoutInflater.from(parent.context).inflate(
                    R.layout.item_year,
                    parent,
                    false
                )
            )
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if(holder is DonationViewHolder) {
            holder.onBind(donations[position])
            holder.binding.itemDonationCard.setOnCreateContextMenuListener(this)
            holder.binding.itemDonationCard.setOnLongClickListener {
                selectedDonation = holder.binding.donation?.donation ?: null
                selectedPosition = holder.adapterPosition
                false
            }

        } else if(holder is HeaderViewHolder)
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

    override fun onViewRecycled(holder: ViewHolder) {
        if(holder is DonationViewHolder) {
            holder.binding.itemDonationCard.setOnCreateContextMenuListener(null)
            holder.binding.itemDonationCard.setOnLongClickListener(null)
        }
        super.onViewRecycled(holder)
    }

    var selectedDonation: Donation? = null
    var selectedPosition: Int = 0

    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        menu?.add(Menu.NONE, R.id.record_edit, Menu.NONE, R.string.record_edit)
        menu?.add(Menu.NONE, R.id.record_remove, Menu.NONE, R.string.record_remove)
    }
}