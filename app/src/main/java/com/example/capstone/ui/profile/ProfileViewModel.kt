package com.example.capstone.ui.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.capstone.data.UserRepository
import com.example.capstone.data.pref.UserModel
import kotlinx.coroutines.launch

class ProfileViewModel(private val repository: UserRepository) : ViewModel() {

    private val _profileUpdate = MutableLiveData<Boolean>()
    val profileUpdate: LiveData<Boolean> = _profileUpdate
    fun logout() {
        viewModelScope.launch {
            repository.logout()
        }
    }

    fun getProfile(): LiveData<UserModel> {
        return repository.getSession().asLiveData()
    }

    fun setProfile(name: String, email: String, photoUri: String = "", gender: String = "", birth: String = "") {
        viewModelScope.launch {
            try {
                repository.saveProfile(name, email, photoUri, gender, birth)
                _profileUpdate.value = true
            } catch (e: Exception) {
                _profileUpdate.value = false
            }
        }
    }
}