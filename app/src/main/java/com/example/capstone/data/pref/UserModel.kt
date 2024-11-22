package com.example.capstone.data.pref

data class UserModel(
    val name: String,
    val email: String,
    val password: String,
    val photoUri: String = "",
    val gender: String = "",
    val birth: String = "",
    val token: String = "",
    val isLogin: Boolean = false
)
