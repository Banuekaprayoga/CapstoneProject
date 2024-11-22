package com.example.capstone.data.pref

data class UserModel(
    val email: String,
    val password: String,
    val token: String = "",
    val isLogin: Boolean = false
)
