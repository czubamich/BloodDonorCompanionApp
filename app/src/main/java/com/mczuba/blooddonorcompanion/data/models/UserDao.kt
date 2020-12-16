package com.mczuba.blooddonorcompanion.data.models

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

    @Query("Select count(*) FROM user_table")
    suspend fun getUserCount() : Int

    @Query("SELECT EXISTS (SELECT * FROM user_table WHERE userId = :userId)")
    suspend fun checkUserExist(userId: Int) : Boolean
}
