package com.mczuba.blooddonorcompanion.data

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.mczuba.blooddonorcompanion.BR
import com.mczuba.blooddonorcompanion.data.models.Donation
import com.mczuba.blooddonorcompanion.data.models.Schedule
import java.text.SimpleDateFormat
import java.util.*

data class ScheduleLiveData(private var schedule: Schedule): BaseObservable() {
    fun setSchedule(schedule: Schedule) {
        scheduleId = schedule.scheduleId
        setType(schedule.type)
        setDate(schedule.date)
        setLocation(schedule.location)
        setNote(schedule.note)
        setNotification(schedule.notificationSetting)

        notifyPropertyChanged(BR.type)
        notifyPropertyChanged(BR.note)
        notifyPropertyChanged(BR.date)
        notifyPropertyChanged(BR.location)
        notifyPropertyChanged(BR.notification)
    }

    private var scheduleId = 0;
    var _type = MutableLiveData<Donation.DonationType>(schedule.type)
    var _date = MutableLiveData<Date>(schedule.date)
    var _location = MutableLiveData<String>(schedule.location)
    var _note = MutableLiveData<String>(schedule.note)
    var _notification = MutableLiveData<Schedule.NotificationSetting>(schedule.notificationSetting)

    val ltype: LiveData<Donation.DonationType> = _type
    val ldate: LiveData<Date> = _date
    val llocation: LiveData<String> = _location
    val lnote: MutableLiveData<String> = _note
    val lnotification: LiveData<Schedule.NotificationSetting> = _notification

    val formattedDate = Transformations.map(ldate) {
        SimpleDateFormat("EE, d MMM yyy").format(it)
    }

    @Bindable
    fun getType() = _type.value
    fun setType(value: Donation.DonationType) {
        if(_type.value==value)
            return

        _type.value = value
    }

    @Bindable
    fun getDate() = _date.value
    fun setDate(value: Date) {
        if(_date.value==value)
            return

        _date.value = value
    }

    @Bindable
    fun getLocation() = _location.value
    fun setLocation(value: String) {
        if(_location.value==value)
            return

        _location.value = value
    }

    @Bindable
    fun getNote() = _note.value
    fun setNote(value: String) {
        if(_note.value==value)
            return

        _note.value = value
    }

    @Bindable
    fun getNotification() = _notification.value
    fun setNotification(value: Schedule.NotificationSetting) {
        if(_notification.value==value)
            return

        _notification.value = value
    }

    fun getSchedule(): Schedule {
        return Schedule(
            scheduleId,
            0,
            getType() ?: Donation.DonationType.WHOLE,
            getDate() ?: Date(),
            getLocation() ?: "",
            getNote() ?: "",
            getNotification() ?: Schedule.NotificationSetting.THREE_DAYS_BEFORE
        )
    }
}