package com.mczuba.blooddonorcompanion.vm

import android.app.Application
import androidx.lifecycle.*
import com.mczuba.blooddonorcompanion.R
import com.mczuba.blooddonorcompanion.data.UserRepository
import com.mczuba.blooddonorcompanion.data.models.Donation
import com.mczuba.blooddonorcompanion.data.models.Schedule
import com.mczuba.blooddonorcompanion.util.InjectorUtils
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.time.temporal.ChronoUnit
import java.util.*


class SummaryScheduleViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: UserRepository = InjectorUtils.getUserRepository(application)
    val _schedule = MutableLiveData<Schedule>(getDefaultSchedule())
    val _daysTo = MutableLiveData<Int>(0)

    val schedule: LiveData<Schedule> = _schedule
    val daysTo: LiveData<Int> = _daysTo

    val formattedDate = Transformations.map(schedule) {
        SimpleDateFormat("EE, d MMM yyy").format(it.date)
    }

    val scheduleType = Transformations.map(schedule) {
        when(it.type) {
            Donation.DonationType.WHOLE -> application.resources.getString(R.string.donation_whole)
            Donation.DonationType.PLASMA -> application.resources.getString(R.string.donation_plasma)
            Donation.DonationType.PLATELETS -> application.resources.getString(R.string.donation_platelets)
            else -> application.resources.getString(R.string.donation_whole)
        }
    }

    fun removeSchedule() {
        val scheduleToRemove = _schedule.value
        _schedule.postValue(getDefaultSchedule())
        _daysTo.postValue(0)

        viewModelScope.launch {
            scheduleToRemove?.run { repository.removeSchedule(this) }
            getSchedule()
        }
    }

    init {
        viewModelScope.launch {
            getSchedule()
        }
    }

    suspend fun getSchedule() {
        repository.readUserSchedules(0).sortedBy { x -> x.date }.firstOrNull()?.run {
            _schedule.postValue(this)

            val then = this.date.toInstant().atZone(ZoneOffset.ofHours(0)).toLocalDateTime()
            _daysTo.postValue(ChronoUnit.DAYS.between(LocalDateTime.now(), then).toInt()+1)
        }
    }

    private fun getDefaultSchedule(): Schedule {
        return Schedule(
            -1,
            0,
            Donation.DonationType.WHOLE,
            Date(),
            "",
            "",
            Schedule.NotificationSetting.THREE_DAYS_BEFORE
        )
    }
}