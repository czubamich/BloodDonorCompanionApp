package com.mczuba.blooddonorcompanion.vm.records

import android.view.View
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.mczuba.blooddonorcompanion.R
import com.mczuba.blooddonorcompanion.databinding.ItemDonationBinding
import com.mczuba.blooddonorcompanion.util.FoldableCardHelper

class DonationViewHolder(var binding: ItemDonationBinding) : RecyclerView.ViewHolder(binding.root) {
    fun onBind(donation: DonationData) {
        binding.donation = donation

        val viewDetail = binding.root.findViewById<View>(R.id.item_donation_details)
        val viewCard = binding.root.findViewById<CardView>(R.id.item_donation_card)
        FoldableCardHelper(binding.root.context, viewDetail, viewCard, true)

        binding.executePendingBindings()
    }
}