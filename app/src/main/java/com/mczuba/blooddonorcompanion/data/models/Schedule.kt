package com.mczuba.blooddonorcompanion.data.models

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "schedule_table")
data class Schedule (
    @PrimaryKey(autoGenerate = true) var scheduleId: Int,
    @ForeignKey
        (entity = User::class,
        parentColumns = ["userId"],
        childColumns = ["userId"],
        onDelete = ForeignKey.CASCADE
    )    var userId: Int,
    var type: Donation.DonationType,
    var date: Date,
    var location: String,
    var note: String,
    var notificationSetting: NotificationSetting
) {
    enum class NotificationSetting {
        NO_NOTIFICATION,
        DAY_BEFORE,
        THREE_DAYS_BEFORE,
        WEEK_BEFORE
    }
}