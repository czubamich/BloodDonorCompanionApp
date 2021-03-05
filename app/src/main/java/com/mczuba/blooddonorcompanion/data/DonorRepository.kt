package com.mczuba.blooddonorcompanion.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.mczuba.blooddonorcompanion.data.models.*
import com.mczuba.blooddonorcompanion.util.Converters
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.util.*

class DonorRepository(
    private val userDao: UserDao,
    private val donationDao: DonationDao,
    private val scheduleDao: ScheduleDao
) {
    suspend fun addDonation(donation: Donation) = donationDao.addDonation(donation)
    suspend fun removeDonation(donation: Donation) = donationDao.removeDonation(donation)
    suspend fun updateDonation(donation: Donation) = donationDao.updateDonation(donation)
    suspend fun readUserDonations() = donationDao.readAllUserDonations()
    suspend fun readUserDonations(userId: Int) = donationDao.readAllUserDonations(userId)

    suspend fun addSchedule(schedule: Schedule) = scheduleDao.addSchedule(schedule)
    suspend fun removeSchedule(schedule: Schedule) = scheduleDao.removeSchedule(schedule)
    suspend fun updateSchedule(schedule: Schedule) = scheduleDao.updateSchedule(schedule)
    suspend fun readUserSchedules(userId: Int) = scheduleDao.readAllUserScheduled(userId)

    suspend fun addUser(user: User) = userDao.addUser(user)
    suspend fun updateUser(user: User) = userDao.updateUser(user)
    suspend fun deleteUser(user: User) = userDao.deleteUser(user)
    suspend fun readAllUsers() = userDao.readAllUsers()
    suspend fun userExists() = userDao.getUserCount()>0;
    suspend fun checkUserExists(userId: Int) = userDao.checkUserExist(userId)
    suspend fun getNextDonationDate(userId: Int, type: Donation.DonationType): Date {
        val donation = readUserDonations(userId).sortedByDescending { x -> x.date }.firstOrNull()
        if (donation == null)
            return Date()

        var d = donation?.date?.toInstant().atZone(ZoneOffset.ofHours(0)).toLocalDateTime()
        val nd = LocalDateTime.now()
        val v = Converters.fromDonationTypeToDays(type, donation?.type ?: Donation.DonationType.WHOLE)
        d = d.plusDays(v.toLong())
        return Date.from(maxOf(d, nd).atZone(ZoneOffset.ofHours(0)).toInstant())
    }

    private var _user = MutableLiveData<User>(null)
    val user: LiveData<User> = _user

    init {
        MainScope().launch {
            _user.postValue((readAllUsers().firstOrNull()))
        }
    }
}