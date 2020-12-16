package com.mczuba.blooddonorcompanion.vm

import android.app.Application
import androidx.databinding.Observable
import androidx.databinding.Observable.OnPropertyChangedCallback
import androidx.databinding.ObservableInt
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.mczuba.blooddonorcompanion.R
import com.mczuba.blooddonorcompanion.data.DonationSummary
import com.mczuba.blooddonorcompanion.data.UserRepository
import com.mczuba.blooddonorcompanion.data.models.Donation
import com.mczuba.blooddonorcompanion.data.models.User
import com.mczuba.blooddonorcompanion.util.InjectorUtils
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.time.temporal.ChronoUnit
import java.util.*


class SummaryViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: UserRepository = InjectorUtils.getUserRepository(application)
    private var _user = MutableLiveData<User>()
    private lateinit var _totalSummary : DonationSummary
    private lateinit var _wholeSummary : DonationSummary
    private lateinit var _plasmaSummary : DonationSummary
    private lateinit var _plateletsSummary : DonationSummary
    private lateinit var _totalSummaryYear : DonationSummary
    private lateinit var _wholeSummaryYear : DonationSummary
    private lateinit var _plasmaSummaryYear : DonationSummary
    private lateinit var _plateletsSummaryYear : DonationSummary

    private val _currentSummary = MutableLiveData<DonationSummary>(DonationSummary.Factory.getEmptyDonationSummary(
        Donation.DonationType.WHOLE))
    private val _yearlySummary = MutableLiveData<DonationSummary>(DonationSummary.Factory.getEmptyDonationSummary(
        Donation.DonationType.WHOLE))
    private var _daysLeftWhole = 0
    private var _daysLeftPlatelets = 0
    private var _daysLeftPlasma = 0
    private val _daysLeft = MutableLiveData(0)

    val user: LiveData<User> = _user
    var currentSummary: LiveData<DonationSummary> = _currentSummary;
    var yearlySummary: LiveData<DonationSummary> = _yearlySummary;
    val checkedBtnObs = ObservableInt(R.id.summary_chipgroup)
    val daysLeft: LiveData<Int> = _daysLeft

    init {
        viewModelScope.launch {
            val users = repository.readAllUsers()
            val user = users.first()
            _user.postValue(user)

            val donations = repository.readUserDonations(user.userId)
            val latest = donations.sortedBy { x -> x.date }.firstOrNull()

            _wholeSummary = DonationSummary.getTypeSummary(donations, Donation.DonationType.WHOLE)
            _plasmaSummary = DonationSummary.getTypeSummary(donations, Donation.DonationType.PLASMA)
            _plateletsSummary = DonationSummary.getTypeSummary(donations, Donation.DonationType.PLATELETS)

            val yearDate = Date(Date().year, 0, 1)
            _wholeSummaryYear = DonationSummary.getTypeSummarySinceDate(donations, Donation.DonationType.WHOLE, yearDate)
            _plasmaSummaryYear = DonationSummary.getTypeSummarySinceDate(donations, Donation.DonationType.PLASMA, yearDate)
            _plateletsSummaryYear = DonationSummary.getTypeSummarySinceDate(donations, Donation.DonationType.PLATELETS, yearDate)
            _totalSummaryYear = DonationSummary.getTotalSummarySinceDate(donations, yearDate)

            _totalSummary = DonationSummary.getTotalSummary(donations)

            latest?.run {
                val today = LocalDateTime.now()
                val thenWhole = repository.getNextDonationDate(user.userId, Donation.DonationType.WHOLE).toInstant().atZone(ZoneOffset.ofHours(0)).toLocalDateTime()
                val thenPlasm = repository.getNextDonationDate(user.userId, Donation.DonationType.PLASMA).toInstant().atZone(ZoneOffset.ofHours(0)).toLocalDateTime()
                val thenPlate = repository.getNextDonationDate(user.userId, Donation.DonationType.PLATELETS).toInstant().atZone(ZoneOffset.ofHours(0)).toLocalDateTime()


                _daysLeftWhole = ChronoUnit.DAYS.between(today, thenWhole).toInt()+1
                _daysLeftPlasma = ChronoUnit.DAYS.between(today, thenPlasm).toInt()+1
                _daysLeftPlatelets = ChronoUnit.DAYS.between(today, thenPlate).toInt()+1
            }

            checkedBtnObs.set(R.id.summary_chiptotal)
        }

        checkedBtnObs.addOnPropertyChangedCallback(object : OnPropertyChangedCallback() {
            override fun onPropertyChanged(sender: Observable, propertyId: Int) {
                val newSelection = when(checkedBtnObs.get())
                {
                    R.id.summary_chipwhole -> _wholeSummary;
                    R.id.summary_chipplasma -> _plasmaSummary;
                    R.id.summary_chipplatelets -> _plateletsSummary;
                    else -> _totalSummary;
                }
                _currentSummary.postValue(newSelection)
                val newYearSelection = when(checkedBtnObs.get())
                {
                    R.id.summary_chipwhole -> _wholeSummaryYear;
                    R.id.summary_chipplasma -> _plasmaSummaryYear;
                    R.id.summary_chipplatelets -> _plateletsSummaryYear;
                    else -> _totalSummaryYear;
                }
                _yearlySummary.postValue(newYearSelection)
                _daysLeft.postValue(
                    when(checkedBtnObs.get()) {
                        R.id.summary_chipwhole -> _daysLeftWhole;
                        R.id.summary_chipplasma -> _daysLeftPlasma;
                        R.id.summary_chipplatelets -> _daysLeftPlatelets;
                        else -> maxOf(_daysLeftWhole, _daysLeftPlasma, _daysLeftPlatelets);
                    }
                )
            }
        })
    }
}