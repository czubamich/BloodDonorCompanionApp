package com.mczuba.blooddonorcompanion.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "user_table")
data class User (
    @PrimaryKey val userId: Int,
    val name: String,
    val birthDay: Date,
    val gender: Gender,
    val bloodType: BloodType
) {
    enum class Gender {
        Male,
        Female;
    }
    enum class BloodType(val type: String) {
        ZeroPlus("0 Rh+"),
        ZeroMinus("0 Rh-"),
        Aplus("A Rh+"),
        Aminus("A Rh-"),
        Bplus("B Rh+"),
        Bminus("B Rh-"),
        ABplus("AB Rh+"),
        ABminus("AB Rh-"),
        Unknown("-");

        override fun toString(): String {
            return this.type;
        }
    }
}