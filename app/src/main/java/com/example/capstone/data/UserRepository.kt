package com.example.capstone.data

import com.example.capstone.data.pref.UserModel
import com.example.capstone.data.pref.UserPreference
import kotlinx.coroutines.flow.Flow

// Repository untuk mengatur data user
class UserRepository private constructor(
    private val userPreference: UserPreference
) {
    suspend fun registerUser(email: String, password: String) {
        val user = UserModel(email, password)
        userPreference.saveUser(user)
    }

    suspend fun loginUser(email: String, password: String): Boolean {
        return userPreference.login(email, password)
    }

    fun getSession(): Flow<UserModel> {
        return userPreference.getSession()
    }

    suspend fun logout() {
        userPreference.logout()
    }

    companion object {
        @Volatile
        private var instance: UserRepository? = null
        fun getInstance(
            userPreference: UserPreference
        ): UserRepository =
            instance ?: synchronized(this) {
                instance ?: UserRepository(userPreference)
            }.also { instance = it }
    }
}