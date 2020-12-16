package com.mczuba.blooddonorcompanion.data.models

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "donation_table")
data class Donation (
    @PrimaryKey(autoGenerate = true) var donationId: Int,
    @ForeignKey
        (entity = User::class,
        parentColumns = ["userId"],
        childColumns = ["donorId"],
        onDelete = ForeignKey.CASCADE
    )    var donorId: Int,
    var type: DonationType,
    var amount: Int,
    var date: Date,
    var location: String,
    var note: String,
    var arm: ArmType,
    var hemoglobin: Float?,
    var duration: Float?,
    var diastolic: Int?,
    var systolic: Int?
) {
    enum class DonationType {
        WHOLE,
        PLASMA,
        PLATELETS,
        DISQUALIFIED
    }

    enum class ArmType {
        LEFT,
        RIGHT,
        UNKNOWN
    }
}