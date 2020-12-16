@file:Suppress("DEPRECATION")

package com.mczuba.blooddonorcompanion.util

import com.mczuba.blooddonorcompanion.data.models.Donation
import java.util.*
import kotlin.collections.ArrayList
import kotlin.random.Random

object TestUtils {
    @JvmStatic
    fun getTestDonations(amount: Int) : ArrayList<Donation> {
        val donations = ArrayList<Donation>()
        val currentDate = Calendar.getInstance()

        for (i in 0..amount) {
            val donationType = getRandomDonationType()
            val diastolic = Random.nextInt(60,120)
            val systolic = diastolic + Random.nextInt(20,60)

            donations.add(
                Donation(
                    i,
                    0,
                    donationType,
                    donationTypeToAmount(donationType),
                    currentDate.time,
                    getRandomLocation(),
                    "Note",
                    getRandomArm(),
                    10 + Random.nextFloat()* 10f,
                    5+Random.nextFloat()*10f,
                    diastolic,
                    systolic
                )
            )
            currentDate.add(Calendar.MONTH, -2 -Random.nextInt(12))
            currentDate.add(Calendar.DATE, -Random.nextInt(30))
        }
        return donations
    }

    @JvmStatic
    fun getTestDonation(): Donation = getTestDonations(1).first()

    @JvmStatic
    fun getRandomLocation() : String {
        return when(Random.nextInt(5)) {
            0 -> "RCKiK Katowice"
            1 -> "OT Zabrze"
            2 -> "OT Chorzów"
            3 -> "Ambulans GK"
            else -> ""
        }
    }

    @JvmStatic
    fun getRandomDonationType() : Donation.DonationType {
        return Donation.DonationType.values().get(Random.nextInt(Donation.DonationType.values().size))
    }

    @JvmStatic
    fun getRandomArm() : Donation.ArmType {
        return Donation.ArmType.values().get(Random.nextInt(Donation.ArmType.values().size))
    }

    @JvmStatic
    fun donationTypeToAmount(donationType: Donation.DonationType) : Int {
        return when(donationType) {
            Donation.DonationType.DISQUALIFIED -> 0
            Donation.DonationType.PLATELETS -> 500
            Donation.DonationType.PLASMA -> 600
            Donation.DonationType.WHOLE -> 450
        }
    }
}