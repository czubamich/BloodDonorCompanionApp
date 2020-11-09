package com.mczuba.blooddonorcompanion.vm.records

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.mczuba.blooddonorcompanion.data.Donation
import com.mczuba.blooddonorcompanion.data.UserRepository
import com.mczuba.blooddonorcompanion.util.InjectorUtils
import kotlinx.coroutines.launch

class HistoryViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: UserRepository = InjectorUtils.getUserRepository(application)
    private var _donations = MutableLiveData<List<Donation>>()



    val donations: LiveData<List<Donation>> = _donations
    init {
        viewModelScope.launch {
            val user = repository.readAllUsers().first()
            val donations = repository.readUserDonations(0)
            _donations.postValue(donations)
        }
    }
}