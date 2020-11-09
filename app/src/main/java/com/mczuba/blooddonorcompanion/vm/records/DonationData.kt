package com.mczuba.blooddonorcompanion.vm.records

import android.content.Context
import android.view.View
import com.mczuba.blooddonorcompanion.R
import com.mczuba.blooddonorcompanion.data.Donation
import java.text.SimpleDateFormat

class DonationData(val context: Context, val donation: Donation, val header: Boolean = false)
{
    fun getType() = donation.run{
        when(type){
            Donation.DonationType.WHOLE -> context.getString(R.string.donationtype_whole)
            Donation.DonationType.PLASMA -> context.getString(R.string.donationtype_plasma)
            Donation.DonationType.PLATELETS -> context.getString(R.string.donationtype_platelets)
            Donation.DonationType.DISQUALIFIED -> context.getString(R.string.donationtype_disqualified)
            else -> context.getString(R.string.donationtype_unknown)
        }
    }
    fun getAmount() = context.getString(R.string.format_number, donation.amount)
    fun getArm() = donation.run{
        when(arm){
            Donation.ArmType.RIGHT -> context.getString(R.string.armtype_right)
            Donation.ArmType.LEFT -> context.getString(R.string.armtype_left)
            else -> context.getString(R.string.armtype_unknown)
        }
    }
    fun getLocation() = donation.location
    fun getYear() = SimpleDateFormat("yyyy").format(donation.date)
    fun getDate() = SimpleDateFormat("EE, d MMM yyy").format(donation.date)
    fun getAmountVisibility() = if (donation.amount>0) View.VISIBLE else View.GONE
    fun getTypeColor() = when(donation.type) {
        Donation.DonationType.PLATELETS -> context.resources.getColor(R.color.colorPrimaryDark)
        Donation.DonationType.WHOLE -> context.resources.getColor(R.color.colorPrimary)
        Donation.DonationType.PLASMA -> context.resources.getColor(R.color.colorPrimaryLight)
        Donation.DonationType.DISQUALIFIED -> context.resources.getColor(R.color.colorBlack)
    }
    fun getDuration() = context.getString(R.string.format_number, donation.duration)
    fun getHemoglobin() = context.getString(R.string.format_number, donation.hemoglobin)
    fun getNote() = donation.note
}