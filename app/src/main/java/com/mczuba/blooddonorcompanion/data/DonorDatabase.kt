package com.mczuba.blooddonorcompanion.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.mczuba.blooddonorcompanion.data.models.*
import com.mczuba.blooddonorcompanion.util.Converters

@Database(entities = [User::class, Donation::class, Schedule::class], version =2, exportSchema = true)
@TypeConverters(Converters::class)
abstract class DonorDatabase: RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun donationDao(): DonationDao
    abstract fun scheduleDao(): ScheduleDao

    companion object {
        @Volatile
        private var INSTANCE: DonorDatabase? = null

        fun getDatabase(context: Context): DonorDatabase {
            val tempInstance = INSTANCE
            if(tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val MIGRATION_1_2 = object : Migration(1, 2) {
                    override fun migrate(database: SupportSQLiteDatabase) {
                        database.execSQL(
                            "CREATE TABLE IF NOT EXISTS `schedule_table` (`scheduleId` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `userId` INTEGER NOT NULL, `type` TEXT NOT NULL, `date` INTEGER NOT NULL, `location` TEXT NOT NULL, `note` TEXT NOT NULL, `notificationSetting` TEXT NOT NULL)")
                    }
                }

                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    DonorDatabase::class.java,
                    "user_database"
                ).addMigrations(MIGRATION_1_2)
                 .fallbackToDestructiveMigration().build()
                INSTANCE = instance
                return instance
            }
        }
    }
}