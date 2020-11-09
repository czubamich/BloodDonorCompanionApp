package com.mczuba.blooddonorcompanion.vm.greeting

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.mczuba.blooddonorcompanion.R
import com.mczuba.blooddonorcompanion.data.User
import com.mczuba.blooddonorcompanion.util.InjectorUtils
import kotlinx.coroutines.launch
import java.util.*

class GreetRegisterViewModel(application: Application) : AndroidViewModel(application) {
    private val _name = MutableLiveData("")
    private val _date = MutableLiveData<Date>()
    private val _gender = MutableLiveData(User.Gender.Male)
    private val _bloodtype = MutableLiveData(User.BloodType.Unknown)

    val name: LiveData<String> = _name
    val date: LiveData<String> = _name
    val gender: LiveData<String> = _name
    val bloodtype: LiveData<String> = _name

    fun submit()
    {
        val repo = InjectorUtils.getUserRepository(getApplication())
        viewModelScope.launch {
            val sharedPreferences = getApplication<Application>().getSharedPreferences(getApplication<Application>().getString(R.string.pref_donorprefs), Context.MODE_PRIVATE)
            sharedPreferences.edit().run {
                putInt(getApplication<Application>().getString(R.string.pref_currentuser_key), 0)
                commit()
            }

            val cal = Calendar.getInstance()
            cal.set(1998,2,5)
            val user = User(0, "Adam", cal.time, User.Gender.Male, User.BloodType.Aplus);
            if(repo.checkUserExists(user.userId))
                repo.updateUser(user)
            else
                repo.addUser(user)
        }
    }
}