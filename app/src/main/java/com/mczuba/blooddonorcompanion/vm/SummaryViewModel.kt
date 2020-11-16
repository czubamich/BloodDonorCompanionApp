package com.mczuba.blooddonorcompanion.vm

import android.app.Application
import androidx.databinding.Observable
import androidx.databinding.Observable.OnPropertyChangedCallback
import androidx.databinding.ObservableInt
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.hadilq.liveevent.LiveEvent
import com.mczuba.blooddonorcompanion.R
import com.mczuba.blooddonorcompanion.data.Donation
import com.mczuba.blooddonorcompanion.data.DonationSummary
import com.mczuba.blooddonorcompanion.data.User
import com.mczuba.blooddonorcompanion.data.UserRepository
import com.mczuba.blooddonorcompanion.util.InjectorUtils
import kotlinx.coroutines.launch
import java.time.Duration
import java.time.Instant
import java.time.temporal.ChronoUnit


class SummaryViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: UserRepository = InjectorUtils.getUserRepository(application)
    private var _user = MutableLiveData<User>()
    private lateinit var _totalSummary : DonationSummary
    private lateinit var _wholeSummary : DonationSummary
    private lateinit var _plasmaSummary : DonationSummary
    private lateinit var _plateletsSummary : DonationSummary

    private val _currentSummary = MutableLiveData<DonationSummary>(DonationSummary.Factory.getEmptyDonationSummary(Donation.DonationType.WHOLE))
    private val _daysLeft = MutableLiveData<Int>(0)

    val user: LiveData<User> = _user
    var currentSummary: LiveData<DonationSummary> = _currentSummary;
    val checkedBtnObs = ObservableInt(R.id.summary_chipgroup)
    val daysLeft: LiveData<Int> = _daysLeft

    private val _navigateToDetails = LiveEvent<Boolean>()
    val navigateToDetails: LiveData<Boolean> = _navigateToDetails

    init {
        viewModelScope.launch {
            val users = repository.readAllUsers()
            val user = users.first()
            _user.postValue(user)

            val summary = repository.getUserDonationSummaryByType(user.userId)
            _wholeSummary = summary.find { it.type == Donation.DonationType.WHOLE }
                ?: DonationSummary.Factory.getEmptyDonationSummary(
                    Donation.DonationType.WHOLE
                )
            _plasmaSummary  = summary.find {
                it.type == Donation.DonationType.PLASMA } ?: DonationSummary.Factory.getEmptyDonationSummary(
                Donation.DonationType.PLASMA
            )
            _plateletsSummary = summary.find {
                it.type == Donation.DonationType.PLATELETS } ?: DonationSummary.Factory.getEmptyDonationSummary(
                Donation.DonationType.PLATELETS
            )

            val sumsum : DonationSummary? = repository.getUserDonationSummary(user.userId)
            _totalSummary = sumsum ?: DonationSummary.Factory.getEmptyDonationSummary( Donation.DonationType.WHOLE )
            if(_totalSummary != null && _totalSummary.newest != null)
            {
                val dateNow = Instant.now()
                val date = _totalSummary.newest!!
                val dateThen = Instant.ofEpochMilli(date.time).plus(57, ChronoUnit.DAYS)
                val days = Duration.between(dateNow, dateThen).toDays().toInt()

                if(days<0)
                    _daysLeft.postValue(0)
                else
                    _daysLeft.postValue(days)
            }
            checkedBtnObs.set(R.id.summary_chiptotal)
        }

        checkedBtnObs.addOnPropertyChangedCallback(object : OnPropertyChangedCallback() {
            override fun onPropertyChanged(sender: Observable, propertyId: Int) {
                val newSelection = when(checkedBtnObs.get())
                {
                    R.id.summary_chiptotal -> _totalSummary;
                    R.id.summary_chipwhole -> _wholeSummary;
                    R.id.summary_chipplasma -> _plasmaSummary;
                    R.id.summary_chipplatelets -> _plateletsSummary;
                    else -> _totalSummary;
                }
                _currentSummary.postValue(newSelection)
            }
        })
    }
}