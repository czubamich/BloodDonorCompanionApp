package com.mczuba.blooddonorcompanion.data.models

import androidx.room.Embedded
import androidx.room.Relation
import androidx.room.RoomWarnings

@SuppressWarnings(RoomWarnings.PRIMARY_KEY_FROM_EMBEDDED_IS_DROPPED)
data class UserDonations(
    @Embedded val user: User,
    @Relation(
        parentColumn = "userId",
        entityColumn = "donationId",
        entity = Donation::class
    )
    val donations: List<Donation>
)