package com.example.aeontest.presenter.login

data class LoginState(
    val isLoading: Boolean = false,
    val token: String? = null,
    val error: String = ""
)
