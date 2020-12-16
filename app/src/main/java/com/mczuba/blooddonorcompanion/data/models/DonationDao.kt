package com.mczuba.blooddonorcompanion.data.models

import androidx.room.*

@Dao
interface DonationDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addDonation(donation: Donation)

    @Delete
    suspend fun removeDonation(donation: Donation)

    @Update
    suspend fun updateDonation(donation: Donation)

    @Transaction
    @Query("SELECT * FROM user_table ORDER BY userId ASC")
    suspend fun readAllUserDonations() : List<UserDonations>

    @Query("SELECT * FROM donation_table WHERE donorId = :userId ORDER BY date ASC")
    suspend fun readAllUserDonations(userId: Int) : List<Donation>
}