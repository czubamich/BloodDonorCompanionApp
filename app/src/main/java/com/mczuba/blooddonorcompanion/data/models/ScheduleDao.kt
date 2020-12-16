package com.mczuba.blooddonorcompanion.data.models

import androidx.room.*

@Dao
interface ScheduleDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addSchedule(schedule: Schedule)

    @Delete
    suspend fun removeSchedule(schedule: Schedule)

    @Update
    suspend fun updateSchedule(schedule: Schedule)

    @Query("SELECT * FROM schedule_table WHERE userId = :userId ORDER BY date ASC")
    suspend fun readAllUserScheduled(userId: Int) : List<Schedule>
}