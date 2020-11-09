package com.mczuba.blooddonorcompanion.data

import androidx.room.*

@Dao
interface DonationDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addDonation(donation: Donation)

    @Delete
    suspend fun removeDonation(donation: Donation)

    @Update
    suspend fun updateDonation(donation: Donation)
}