package com.mczuba.blooddonorcompanion.vm.editors

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.viewModelScope
import com.hadilq.liveevent.LiveEvent
import com.mczuba.blooddonorcompanion.data.DonationLiveData
import com.mczuba.blooddonorcompanion.data.DonorRepository
import com.mczuba.blooddonorcompanion.data.models.Donation
import com.mczuba.blooddonorcompanion.util.InjectorUtils
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.time.ZoneOffset
import java.util.*

class NewRecordViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: DonorRepository = InjectorUtils.getUserRepository(application)

    private val _completeState = LiveEvent<Boolean>()

    val completeState: LiveData<Boolean> = _completeState
    val donation = DonationLiveData(getDefaultDonation())
    var editMode = false

    fun setupEditMode(donationId: Int) {
        viewModelScope.launch {
            editMode = true;
            repository.readUserDonations(0).find { it.donationId == donationId }
                ?.let { donation.setDonation(it) }
        }
    }

    fun setDonationType(type: Donation.DonationType) {
        donation.setType(type)
        donation.setAmount(
            when (type) {
                Donation.DonationType.WHOLE -> 450
                Donation.DonationType.PLASMA -> 600
                Donation.DonationType.PLATELETS -> 500
                else -> 0
            }
        )
    }

    fun setArmType(type: Donation.ArmType) {
        donation.setArm(type)
    }

    fun submit() {
        viewModelScope.launch {
            val donations = repository.readUserDonations(0)

            if (editMode)
                repository.updateDonation(donation.getDonation())
            else
                repository.addDonation(donation.getDonation())

            _completeState.value = true
        }
    }

    val formattedAmount = Transformations.map(donation.lamount) {
        if (it > 0)
            it.toString()
        else
            "-"
    }

    val formattedDate = Transformations.map(donation.ldate) {
        SimpleDateFormat("EE, d MMM yyy").format(it)
    }

    fun setNewDateTime(year: Int, month: Int, dayOfMonth: Int) {
        donation.setDate(Date(year - 1900, month, dayOfMonth))
    }

    fun getMinTime(): Date {
        var date = repository.user.value!!.birthDay!!.toInstant().atZone(ZoneOffset.ofHours(0)).toLocalDateTime()
        date = date.plusYears(18)
        return Date.from(date.atZone(ZoneOffset.ofHours(0)).toInstant())
    }

    fun getMaxTime(): Date = Date()


    private fun getDefaultDonation(): Donation {
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