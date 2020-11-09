package com.mczuba.blooddonorcompanion.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import java.util.*

data class DonationLiveData(private var donation: Donation) {
    private var _type = MutableLiveData<Donation.DonationType>(donation.type)
    private var _amount = MutableLiveData<Int>(donation.amount)
    private var _date = MutableLiveData<Date>(donation.date)
    private var _location = MutableLiveData<String>(donation.location)
    private var _note = MutableLiveData<String>(donation.note)
    private var _arm = MutableLiveData<Donation.ArmType?>(donation.arm)
    private var _hemoglobin = MutableLiveData<Float?>(donation.hemoglobin)
    private var _duration = MutableLiveData<Float?>(donation.duration)
    private var _diastolic = MutableLiveData<Int?>(donation.diastolic)
    private var _systolic = MutableLiveData<Int?>(donation.systolic)

    val ltype : LiveData<Donation.DonationType> = _type
    val lamount : LiveData<Int> = _amount
    val ldate : LiveData<Date> = _date
    val llocation : LiveData<String> = _location
    val lnote : MutableLiveData<String> = _note
    val larm : LiveData<Donation.ArmType?> = _arm
    val lhemoglobin : LiveData<Float?> = _hemoglobin
    val lduration : LiveData<Float?> = _duration
    val ldiastolic : LiveData<Int?> = _diastolic
    val lsystolic : LiveData<Int?> = _systolic

    fun getType() = _type.value
    fun setType(value: Donation.DonationType) {
        _type.postValue(value)
    }

    fun getAmount() = _amount.value
    fun setAmount(value: Int) {
        _amount.postValue(value)
    }

    fun getDate() = _date.value
    fun setDate(value: Date) {
        _date.postValue(value)
    }

    fun getLocation() = _location.value
    fun setLocation(value: String) {
        _location.postValue(value)
    }

    fun getNote() = _note.value
    fun setNote(value: String) {
        _note.postValue(value)
    }

    fun getArm() = _arm.value
    fun setArm(value: Donation.ArmType?) {
        _arm.postValue(value)
    }

    fun getHemoglobin() = _hemoglobin.value
    fun setHemoglobin(value: Float?) {
        if (value!=null)
            if(value<0f)
                _hemoglobin.postValue(-value)
            else
                _hemoglobin.postValue(value)
    }

    fun getDiastolic() = _diastolic.value
    fun setDiastolic(value: Int?) {
        if (value!=null)
        if(value<0f)
            _diastolic.postValue(-value)
        else
            _diastolic.postValue(value)
    }

    fun getSystolic() = _systolic.value
    fun setSystolic(value: Int?) {
        if (value!=null)
        if(value<0f)
            _systolic.postValue(-value)
        else
            _systolic.postValue(value)
    }

    fun getDuration() = _duration.value
    fun setDuration(value: Float?) {
        if (value!=null)
        if(value<0f)
            _duration.postValue(-value)
        else
            _duration.postValue(value)
    }

    fun getDonation() : Donation {
        return Donation(
            0,
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