package com.mczuba.blooddonorcompanion.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.mczuba.blooddonorcompanion.util.Converters

@Database(entities = [User::class, Donation::class], version =1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class DonorDatabase: RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun donationDao(): DonationDao

    companion object {
        @Volatile
        private var INSTANCE: DonorDatabase? = null

        fun getDatabase(context: Context): DonorDatabase {
            val tempInstance = INSTANCE
            if(tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    DonorDatabase::class.java,
                    "user_database"
                ).fallbackToDestructiveMigration().build()
                INSTANCE = instance
                return instance
            }
        }
    }
}