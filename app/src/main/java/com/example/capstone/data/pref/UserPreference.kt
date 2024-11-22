package com.example.capstone.data.pref

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

// Kelas untuk mengelola penyimpanan data user menggunakan DataStore
val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "session")

class UserPreference private constructor(private val dataStore: DataStore<Preferences>) {

    suspend fun saveUser(user: UserModel) {
        dataStore.edit { preferences ->
            preferences[NAME_KEY] = user.name
            preferences[EMAIL_KEY] = user.email
            preferences[PASSWORD_KEY] = user.password // Simpan password
            preferences[TOKEN_KEY] = user.token
            preferences[IS_LOGIN_KEY] = false // Default false saat register
        }
    }
    suspend fun setProfile(user: UserModel) {
        dataStore.edit { preferences ->
            preferences[NAME_KEY] = user.name
            preferences[EMAIL_KEY] = user.email
            preferences[PHOTO_URI_KEY] = user.photoUri
            preferences[GENDER_KEY] = user.gender
            preferences[BIRTH_KEY] = user.birth
        }
    }

    suspend fun login(email: String, password: String): Boolean {
        var isValid = false
        dataStore.edit { preferences ->
            val savedEmail = preferences[EMAIL_KEY] ?: ""
            val savedPassword = preferences[PASSWORD_KEY] ?: ""

            if (email == savedEmail && password == savedPassword) {
                preferences[IS_LOGIN_KEY] = true
                isValid = true
            }
        }
        return isValid
    }

    fun getSession(): Flow<UserModel> {
        return dataStore.data.map { preferences ->
            UserModel(
                preferences[NAME_KEY] ?: "",
                preferences[EMAIL_KEY] ?: "",
                preferences[PASSWORD_KEY] ?: "",
                preferences[PHOTO_URI_KEY] ?: "",
                preferences[GENDER_KEY] ?: "",
                preferences[BIRTH_KEY] ?: "",
                preferences[TOKEN_KEY] ?: "",
                preferences[IS_LOGIN_KEY] ?: false
            )
        }
    }

    suspend fun logout() {
        dataStore.edit { preferences ->
            preferences.clear()
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: UserPreference? = null
        private val NAME_KEY = stringPreferencesKey("name")
        private val EMAIL_KEY = stringPreferencesKey("email")
        private val PASSWORD_KEY = stringPreferencesKey("password")
        private val PHOTO_URI_KEY = stringPreferencesKey("photoUri")
        private val GENDER_KEY = stringPreferencesKey("gender")
        private val BIRTH_KEY = stringPreferencesKey("birth")
        private val TOKEN_KEY = stringPreferencesKey("token")
        private val IS_LOGIN_KEY = booleanPreferencesKey("isLogin")

        fun getInstance(dataStore: DataStore<Preferences>): UserPreference {
            return INSTANCE ?: synchronized(this) {
                val instance = UserPreference(dataStore)
                INSTANCE = instance
                instance
            }
        }
    }
}