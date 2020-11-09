package com.mczuba.blooddonorcompanion.data

import java.util.*

data class DonationSummary (
    var type: Donation.DonationType?,
    val total: Int,
    val newest: Date?,
    val averageHemoglobin: Float?,
    val averageDuration: Float?
) {
    companion object Factory {
        @JvmStatic
        fun getEmptyDonationSummary(type: Donation.DonationType? = null ) = DonationSummary(type, 0, null, 0f, 0f)
    }
}