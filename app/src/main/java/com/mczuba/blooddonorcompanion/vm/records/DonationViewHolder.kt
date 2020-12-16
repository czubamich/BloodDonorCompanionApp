package com.mczuba.blooddonorcompanion.vm.records

import android.content.Context
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.mczuba.blooddonorcompanion.databinding.ItemDonationBinding
import com.mczuba.blooddonorcompanion.util.FoldableCardHelper

class DonationViewHolder(var binding: ItemDonationBinding, val context: Context) : RecyclerView.ViewHolder(binding.root) {
    fun onBind(donation: DonationData) {
        binding.donation = donation

        FoldableCardHelper(binding.root.context, binding.itemDonationDetails, binding.itemDonationCard, true)

        if (donation.getNote()!="")
        {
            binding.itemDonationNote.visibility = View.VISIBLE
        }

        binding.executePendingBindings()
    }
}