package com.example.capstone.ui.signup

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.capstone.data.UserRepository
import kotlinx.coroutines.launch

class SignupViewModel(private val repository: UserRepository) : ViewModel() {
    private val _registerResult = MutableLiveData<Boolean>()
    val registerResult: LiveData<Boolean> = _registerResult

    fun register(email: String, password: String) {
        viewModelScope.launch {
            try {
                repository.registerUser(email, password)
                _registerResult.value = true
            } catch (e: Exception) {
                _registerResult.value = false
            }
        }
    }
}