package com.mczuba.blooddonorcompanion.util

import android.app.Application
import com.mczuba.blooddonorcompanion.data.DonorDatabase
import com.mczuba.blooddonorcompanion.data.UserRepository

object InjectorUtils {
    @JvmStatic
    fun getUserRepository(application: Application) : UserRepository
    {
        val d = DonorDatabase.getDatabase(application)
        return UserRepository(d.userDao(), d.donationDao(), d.scheduleDao())
    }
}