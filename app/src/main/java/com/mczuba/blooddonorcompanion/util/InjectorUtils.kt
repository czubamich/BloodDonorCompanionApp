package com.mczuba.blooddonorcompanion.util

import android.app.Application
import com.mczuba.blooddonorcompanion.data.DonorDatabase
import com.mczuba.blooddonorcompanion.data.DonorRepository

object InjectorUtils {
    @JvmStatic
    fun getUserRepository(application: Application) : DonorRepository
    {
        val d = DonorDatabase.getDatabase(application)
        return DonorRepository(d.userDao(), d.donationDao(), d.scheduleDao())
    }
}