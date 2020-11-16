package com.mczuba.blooddonorcompanion.vm.greeting

import android.app.Application
import android.content.Context
import androidx.lifecycle.*
import com.hadilq.liveevent.LiveEvent
import com.mczuba.blooddonorcompanion.R
import com.mczuba.blooddonorcompanion.data.User
import com.mczuba.blooddonorcompanion.util.InjectorUtils
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class GreetRegisterViewModel(application: Application) : AndroidViewModel(application) {

    private val _completeState = LiveEvent<Boolean>()
    private val _openDatePicker = LiveEvent<Boolean>()

    val completeState: LiveData<Boolean> = _completeState
    val openDatePicker: LiveData<Boolean> = _openDatePicker
    val name = MutableLiveData("")
    val bloodType = MutableLiveData(User.BloodType.Aplus)
    val gender = MutableLiveData(User.Gender.Male)
    val birthday = MutableLiveData(Date())

    fun pickDate() {
        _openDatePicker.value = true
    }
    val formattedDate = Transformations.map(birthday) {
        SimpleDateFormat("EE, d MMM yyy").format(it)
    }
    fun setNewDateTime(year: Int, month: Int, dayOfMonth: Int) {
        birthday.postValue(Date(year - 1900, month, dayOfMonth))
        _openDatePicker.value = false
    }

    fun setBloodType(type: User.BloodType) {
        bloodType.postValue(type)
    }

    fun setGender(type: User.Gender) {
        gender.postValue(type)
    }

    fun submit()
    {
        val repo = InjectorUtils.getUserRepository(getApplication())
        viewModelScope.launch {
            val sharedPreferences = getApplication<Application>().getSharedPreferences(getApplication<Application>().getString(R.string.pref_donorprefs), Context.MODE_PRIVATE)
            sharedPreferences.edit().run {
                putInt(getApplication<Application>().getString(R.string.pref_currentuser_key), 0)
                commit()
            }

            val user = User(0, name.value!!, birthday.value!!, gender.value!!, bloodType.value!!);
            if(repo.checkUserExists(user.userId))
                repo.updateUser(user)
            else
                repo.addUser(user)
        }
    }
}