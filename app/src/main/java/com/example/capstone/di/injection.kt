package com.example.capstone.di

import android.content.Context
import com.example.capstone.data.UserRepository
import com.example.capstone.data.pref.UserPreference
import com.example.capstone.data.pref.dataStore

// Kelas untuk dependency injection
object Injection {
    // Menyediakan instance UserRepository
    fun provideRepository(context: Context): UserRepository {
        val pref = UserPreference.getInstance(context.dataStore)
        return UserRepository.getInstance(pref)
    }
}