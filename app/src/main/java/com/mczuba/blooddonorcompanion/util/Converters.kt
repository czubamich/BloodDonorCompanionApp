package com.mczuba.blooddonorcompanion.util

import androidx.room.TypeConverter
import com.mczuba.blooddonorcompanion.data.models.Donation
import com.mczuba.blooddonorcompanion.data.models.Schedule
import com.mczuba.blooddonorcompanion.data.models.User
import java.util.*


object Converters {
    @TypeConverter
    @JvmStatic
    fun fromTimestamp(value: Long?): Date? {
        return if (value == null) null else Date(value)
    }

    @TypeConverter
    @JvmStatic
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time
    }

    @TypeConverter
    @JvmStatic
    fun toGender(value: String) = enumValueOf<User.Gender>(value)

    @TypeConverter
    @JvmStatic
    fun fromGender(value: User.Gender) = value.name

    @TypeConverter
    @JvmStatic
    fun toDonationType(value: String?) : Donation.DonationType {
        return if (value != null)
            enumValueOf<Donation.DonationType>(value)
        else
            Donation.DonationType.WHOLE
    }

    @TypeConverter
    @JvmStatic
    fun fromDonationType(value: Donation.DonationType) = value.name

    @TypeConverter
    @JvmStatic
    fun toBloodType(value: String) = enumValueOf<User.BloodType>(value)

    @TypeConverter
    @JvmStatic
    fun fromBloodType(value: User.BloodType) = value.name

    @TypeConverter
    @JvmStatic
    fun toArm(value: String) = enumValueOf<Donation.ArmType>(value)

    @TypeConverter
    @JvmStatic
    fun fromArm(value: Donation.ArmType) = value.name

    @TypeConverter
    @JvmStatic
    fun toNotificationSetting(value: String) = enumValueOf<Schedule.NotificationSetting>(value)

    @TypeConverter
    @JvmStatic
    fun fromNotificationSetting(value: Schedule.NotificationSetting) = value.name

    fun fromDonationTypeToDays(type: Donation.DonationType, prevtype: Donation.DonationType) =
        when(prevtype) {
        Donation.DonationType.WHOLE -> when(type) {
            Donation.DonationType.PLATELETS -> 56
            Donation.DonationType.PLASMA -> 28
            Donation.DonationType.DISQUALIFIED -> 14
            else -> 56
        }
        Donation.DonationType.PLASMA -> when(type) {
            Donation.DonationType.WHOLE -> 14
            Donation.DonationType.PLATELETS -> 28
            else -> 14
        }
        Donation.DonationType.PLATELETS -> 28
        Donation.DonationType.DISQUALIFIED -> 14
    }
}