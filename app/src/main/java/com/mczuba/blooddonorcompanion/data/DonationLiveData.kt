package com.mczuba.blooddonorcompanion.data

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.mczuba.blooddonorcompanion.BR
import com.mczuba.blooddonorcompanion.data.models.Donation
import java.util.*

data class DonationLiveData(private var donation: Donation): BaseObservable() {

    fun setDonation(donation: Donation) {
        donationId = donation.donationId
        setType(donation.type)
        setAmount(donation.amount)
        setDate(donation.date)
        setLocation(donation.location)
        setNote(donation.note)
        setArm(donation.arm)
        setHemoglobin(donation.hemoglobin)
        setDuration(donation.duration)
        setDiastolic(donation.diastolic)
        setSystolic(donation.systolic)

        notifyPropertyChanged(BR.type)
        notifyPropertyChanged(BR.diastolic)
        notifyPropertyChanged(BR.amount)
        notifyPropertyChanged(BR.duration)
        notifyPropertyChanged(BR.systolic)
        notifyPropertyChanged(BR.hemoglobin)
        notifyPropertyChanged(BR.arm)
        notifyPropertyChanged(BR.note)
        notifyPropertyChanged(BR.date)
        notifyPropertyChanged(BR.location)
    }

    private var donationId = 0;
    var _type = MutableLiveData<Donation.DonationType>(donation.type)
    var _amount = MutableLiveData<Int>(donation.amount)
    var _date = MutableLiveData<Date>(donation.date)
    var _location = MutableLiveData<String>(donation.location)
    var _note = MutableLiveData<String>(donation.note)
    var _arm = MutableLiveData<Donation.ArmType?>(donation.arm)
    var _hemoglobin = MutableLiveData<Float?>(donation.hemoglobin)
    var _duration = MutableLiveData<Float?>(donation.duration)
    var _diastolic = MutableLiveData<Int?>(donation.diastolic)
    var _systolic = MutableLiveData<Int?>(donation.systolic)

    val ltype: LiveData<Donation.DonationType> = _type
    val lamount: LiveData<Int> = _amount
    val ldate: LiveData<Date> = _date
    val llocation: LiveData<String> = _location
    val lnote: MutableLiveData<String> = _note
    val larm: LiveData<Donation.ArmType?> = _arm
    val lhemoglobin: LiveData<Float?> = _hemoglobin
    val lduration: LiveData<Float?> = _duration
    val ldiastolic: LiveData<Int?> = _diastolic
    val lsystolic: LiveData<Int?> = _systolic

    @Bindable
    fun getType() = _type.value
    fun setType(value: Donation.DonationType) {
        if(_type.value==value)
            return

        _type.value = value
    }

    @Bindable
    fun getAmount() = _amount.value
    fun setAmount(value: Int) {
        if(_amount.value==value)
            return

        _amount.value = value
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
    fun getArm() = _arm.value
    fun setArm(value: Donation.ArmType?) {
        if(_arm
                .value==value)
            return

        _arm.value = value
    }

    @Bindable
    fun getHemoglobin() = _hemoglobin.value
    fun setHemoglobin(value: Float?) {
        if(_hemoglobin.value==value)
            return

        if (value != null)
            if (value < 0f)
                _hemoglobin.value = (-value)
            else
                _hemoglobin.value = (value)
    }

    @Bindable
    fun getDiastolic() = _diastolic.value
    fun setDiastolic(value: Int?) {
        if(_diastolic.value==value)
            return

        if (value != null)
            if (value < 0f)
                _diastolic.value = (-value)
            else
                _diastolic.value = value
    }

    @Bindable
    fun getSystolic() = _systolic.value
    fun setSystolic(value: Int?) {
        if(_systolic.value==value)
            return

        if (value != null)
            if (value < 0f)
                _systolic.value = (-value)
            else
                _systolic.value = (value)
    }

    @Bindable
    fun getDuration() = _duration.value
    fun setDuration(value: Float?) {
        if(_duration.value==value)
            return

        if (value != null)
            if (value < 0f)
                _duration.value = (-value)
            else
                _duration.value = (value)
    }

    fun getDonation(): Donation {
        return Donation(
            donationId,
            0,
            getType() ?: Donation.DonationType.WHOLE,
            getAmount() ?: 450,
            getDate() ?: Date(),
            getLocation() ?: "",
            getNote() ?: "",
            getArm() ?: Donation.ArmType.UNKNOWN,
            getHemoglobin(),
            getDuration(),
            getDiastolic(),
            getSystolic()
        )
    }
}