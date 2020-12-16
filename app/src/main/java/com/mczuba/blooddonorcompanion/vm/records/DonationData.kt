package com.mczuba.blooddonorcompanion.vm.records

import android.content.Context
import android.view.View
import com.mczuba.blooddonorcompanion.R
import com.mczuba.blooddonorcompanion.data.models.Donation
import java.text.SimpleDateFormat

class DonationData(val context: Context, val donation: Donation, val header: Boolean = false)
{
    fun getDonationID() = donation.donationId
    fun getType() = donation.run{
        when(type){
            Donation.DonationType.WHOLE -> context.resources.getStringArray(R.array.donation_array)[0]
            Donation.DonationType.PLASMA -> context.resources.getStringArray(R.array.donation_array)[1]
            Donation.DonationType.PLATELETS -> context.resources.getStringArray(R.array.donation_array)[2]
            Donation.DonationType.DISQUALIFIED -> context.resources.getStringArray(R.array.donation_array)[3]
            else -> context.getString(R.string.donation_unknown)
        }
    }
    fun getAmount() = context.getString(R.string.format_number, donation.amount)
    fun getArmShort() = donation.run{
        when(arm){
            Donation.ArmType.RIGHT -> context.resources.getStringArray(R.array.arm_array_short)[0]
            Donation.ArmType.LEFT -> context.resources.getStringArray(R.array.arm_array_short)[1]
            else -> context.resources.getStringArray(R.array.arm_array_short)[2]
        }
    }
    fun getArm() = donation.run{
        when(arm){
            Donation.ArmType.RIGHT -> context.resources.getStringArray(R.array.arm_array)[0]
            Donation.ArmType.LEFT -> context.resources.getStringArray(R.array.arm_array)[1]
            else -> context.resources.getStringArray(R.array.arm_array)[2]
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
    fun getDuration() = donation.duration?.run {
        context.getString(R.string.format_minutes, this)
    } ?: "-"

    fun getHemoglobin() = donation.hemoglobin?.run {
        context.getString(R.string.format_decimal, this)
    } ?: "-"

    fun getBloodPressure() : String {
        if(donation.diastolic==null || donation.systolic==null)
            return "-"
        else
            return context.getString(R.string.format_pressure, donation.diastolic, donation.systolic)
    }
        fun getNote() = donation.note
}