package com.mczuba.blooddonorcompanion.data

import androidx.room.*

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addUser(user: User)

    @Update
    suspend fun updateUser(user: User)

    @Delete
    suspend fun deleteUser(user: User)

    @Query("SELECT * FROM user_table ORDER BY userId ASC")
    suspend fun readAllUsers() : List<User>

    @Transaction
    @Query("SELECT * FROM user_table ORDER BY userId ASC")
    suspend fun readAllUserDonations() : List<UserDonations>

    @Query("SELECT * FROM donation_table WHERE donorId = :userId ORDER BY date ASC")
    suspend fun readAllUserDonations(userId: Int) : List<Donation>

    @Query(
        "SELECT type, SUM(amount) AS total, MAX(date) AS newest,AVG(hemoglobin) AS averageHemoglobin,  AVG(duration) AS averageDuration FROM donation_table WHERE donorId = :userId GROUP BY type")
    suspend fun getUserDonationSummaryByType(userId: Int) : List<DonationSummary>

    @Query("SELECT NULL as type, SUM(amount) AS total, MAX(date) AS newest,AVG(hemoglobin) AS averageHemoglobin,  AVG(duration) AS averageDuration FROM donation_table WHERE donorId = :userId")
    suspend fun getUserDonationSummary(userId: Int) : DonationSummary?

    @Query("Select count(*) FROM user_table")
    suspend fun getUserCount() : Int

    @Query("SELECT EXISTS (SELECT * FROM user_table WHERE userId = :userId)")
    suspend fun checkUserExist(userId: Int) : Boolean
}
