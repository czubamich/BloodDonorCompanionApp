package com.mczuba.blooddonorcompanion.vm.editors

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.hadilq.liveevent.LiveEvent
import com.mczuba.blooddonorcompanion.data.ScheduleLiveData
import com.mczuba.blooddonorcompanion.data.DonorRepository
import com.mczuba.blooddonorcompanion.data.models.Donation
import com.mczuba.blooddonorcompanion.data.models.Schedule
import com.mczuba.blooddonorcompanion.util.InjectorUtils
import kotlinx.coroutines.launch
import java.time.ZoneOffset
import java.util.*

class ScheduleRecordViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: DonorRepository = InjectorUtils.getUserRepository(application)

    private val _completeState = LiveEvent<Boolean>()

    val completeState: LiveData<Boolean> = _completeState
    val schedule = ScheduleLiveData(getDefaultSchedule())

    fun setDonationType(type: Donation.DonationType) {
        schedule.setType(type)
        updateMinDate(type)
    }

    fun setNotification(type: Schedule.NotificationSetting) {
        schedule.setNotification(type)
    }

    fun submit() {
        viewModelScope.launch {
            val schedule = schedule.getSchedule()
            repository.addSchedule(schedule)

            _completeState.value = true
        }
    }

    fun setNewDateTime(year: Int, month: Int, dayOfMonth: Int) {
        schedule.setDate(Date(year - 1900, month, dayOfMonth))
    }

    private var minimumDate = Date();
    fun getMinTime(): Date = minimumDate

    fun getMaxTime(): Date {
        var date = minimumDate.toInstant().atZone(ZoneOffset.ofHours(0)).toLocalDateTime()
        date = date.plusMonths(12)
        return Date.from(date.atZone(ZoneOffset.ofHours(0)).toInstant())
    }

    init {
        updateMinDate(Donation.DonationType.WHOLE)
    }

    fun updateMinDate(type: Donation.DonationType) {
        viewModelScope.launch {
            minimumDate = repository.getNextDonationDate(0, type)
            if(schedule.getDate()?.before(minimumDate) ?: true)
                schedule.setDate(minimumDate)
        }
    }

    init {
        viewModelScope.launch {
            val latestSchedule = repository.readUserSchedules(0).sortedBy { x -> x.date }.firstOrNull()
            if(latestSchedule!=null) {
                schedule.setSchedule(latestSchedule)
            }
        }
    }

    private fun getDefaultSchedule(): Schedule {
        return Schedule(
            0,
            0,
            Donation.DonationType.WHOLE,
            Date(),
            "",
            "",
            Schedule.NotificationSetting.THREE_DAYS_BEFORE
        )
    }
}