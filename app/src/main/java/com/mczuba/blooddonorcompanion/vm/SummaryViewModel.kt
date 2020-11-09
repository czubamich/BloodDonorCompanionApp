package com.mczuba.blooddonorcompanion.vm

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.hadilq.liveevent.LiveEvent
import com.mczuba.blooddonorcompanion.data.Donation
import com.mczuba.blooddonorcompanion.data.DonationSummary
import com.mczuba.blooddonorcompanion.data.User
import com.mczuba.blooddonorcompanion.data.UserRepository
import com.mczuba.blooddonorcompanion.util.InjectorUtils
import kotlinx.coroutines.launch

class SummaryViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: UserRepository = InjectorUtils.getUserRepository(application)
    private var _user = MutableLiveData<User>()
    private var _totalSummary = MutableLiveData<DonationSummary>()
    private var _wholeSummary = MutableLiveData<DonationSummary>()
    private var _plasmaSummary = MutableLiveData<DonationSummary>()
    private var _plateletsSummary = MutableLiveData<DonationSummary>()

    val user: LiveData<User> = _user
    val totalSummary: LiveData<DonationSummary> = _totalSummary
    val wholeSummary: LiveData<DonationSummary> = _wholeSummary
    val plasmaSummary: LiveData<DonationSummary> = _plasmaSummary
    val plateletsSummary: LiveData<DonationSummary> = _plateletsSummary


    private val _navigateToDetails = LiveEvent<Boolean>()
    val navigateToDetails: LiveData<Boolean> = _navigateToDetails

    init {
        viewModelScope.launch {
            val users = repository.readAllUsers()
            val user = users.first()
            _user.postValue(user)

            val summary = repository.getUserDonationSummaryByType(user.userId)
            _wholeSummary.postValue(summary.find { it.type == Donation.DonationType.WHOLE } ?: DonationSummary.Factory.getEmptyDonationSummary(Donation.DonationType.WHOLE) )
            _plasmaSummary.postValue(summary.find { it.type == Donation.DonationType.PLASMA ?: DonationSummary.Factory.getEmptyDonationSummary(Donation.DonationType.PLASMA) })
            _plateletsSummary.postValue(summary.find { it.type == Donation.DonationType.PLATELETS ?: DonationSummary.Factory.getEmptyDonationSummary(Donation.DonationType.PLATELETS) })

            val sumsum : DonationSummary? = repository.getUserDonationSummary(user.userId)
            _totalSummary.postValue(sumsum)
            //_totalSummary.postValue(summary)
        }
    }
}