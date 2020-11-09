package com.mczuba.blooddonorcompanion.vm.editors

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.viewModelScope
import com.hadilq.liveevent.LiveEvent
import com.mczuba.blooddonorcompanion.data.Donation
import com.mczuba.blooddonorcompanion.data.DonationLiveData
import com.mczuba.blooddonorcompanion.data.UserRepository
import com.mczuba.blooddonorcompanion.util.InjectorUtils
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class NewRecordViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: UserRepository = InjectorUtils.getUserRepository(application)

    private val _completeState = LiveEvent<Boolean>()
    private val _openDatePicker = LiveEvent<Boolean>()

    val completeState: LiveData<Boolean> = _completeState
    val openDatePicker: LiveData<Boolean> = _openDatePicker
    val donation = DonationLiveData(getDefaultDonation())

    fun pickDate() {
        _openDatePicker.value = true
    }

    fun click() {
        viewModelScope.launch {
            repository.addDonation(donation.getDonation())
            _completeState.value = true
        }
    }

    val formattedAmount = Transformations.map(donation.lamount) {
        if(it>0)
            it.toString()
        else
            "-"
    }

    val formattedDate = Transformations.map(donation.ldate) {
        SimpleDateFormat("EE, d MMM yyy").format(it)
    }

    fun setNewDateTime(year: Int, month: Int, dayOfMonth: Int) {
        donation.setDate(Date(year - 1900, month, dayOfMonth))
        _openDatePicker.value = false
    }

    private fun getDefaultDonation() : Donation {
        return Donation(
            0,
            0,
            Donation.DonationType.WHOLE,
            450,
            Date(),
            "",
            "",
            Donation.ArmType.UNKNOWN,
            null,
            null,
            null,
            null
        )
    }
}