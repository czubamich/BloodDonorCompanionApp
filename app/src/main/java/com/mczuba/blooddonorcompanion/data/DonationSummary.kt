package com.mczuba.blooddonorcompanion.data

import com.mczuba.blooddonorcompanion.data.models.Donation
import java.util.*

data class DonationSummary(
    var type: Donation.DonationType?,
    val amount: Int,
    val newest: Date?,
    val averageHemoglobin: Float?,
    val averageDuration: Float?
) {
    companion object Factory {
        @JvmStatic
        fun getEmptyDonationSummary(type: Donation.DonationType? = null) =
            DonationSummary(type, 0, null, 0f, 0f)

        @JvmStatic
        fun getTotalSummary(donations: List<Donation>): DonationSummary {
            var amount = 0
            var count = 0
            var countH = 0
            var countD = 0
            var hemoglobin = 0f
            var duration = 0f

            donations.forEach { donation ->
                amount += donation.amount
                count++

                donation.hemoglobin?.let {
                    hemoglobin += it
                    countH++
                }

                donation.duration?.let {
                    duration += it
                    countD++
                }
            }

            if (countH > 0)
                hemoglobin /= countH
            if (countD > 0)
                duration /= countD

            return DonationSummary(
                null,
                amount,
                donations.maxBy { it -> it.date }?.date,
                hemoglobin,
                duration
            )
        }

        @JvmStatic
        fun getTotalSummarySinceDate(donations: List<Donation>, date: Date): DonationSummary {
            val filteredDonations = donations.filter { it -> it.date.after(date) }

            return getTotalSummary(filteredDonations)
        }

        @JvmStatic
        fun getTypeSummary(
            donations: List<Donation>,
            type: Donation.DonationType
        ): DonationSummary {
            val filteredDonations = donations.filter { it -> it.type == type }

            return getTotalSummary(filteredDonations)
        }

        @JvmStatic
        fun getTypeSummarySinceDate(
            donations: List<Donation>,
            type: Donation.DonationType,
            date: Date
        ): DonationSummary {
            val filteredDonations = donations.filter { it -> it.date.after(date) }
            val filteredDonations2 = filteredDonations.filter { it -> it.type == type }

            return getTotalSummary(filteredDonations2)
        }
    }
}