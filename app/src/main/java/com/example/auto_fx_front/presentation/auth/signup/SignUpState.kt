package com.example.auto_fx_front.presentation.auth.signup

data class SignUpState(
    val firstName: String = "",
    val lastName: String = "",
    val email: String = "",
    val countryCode: String = "",
    val number: String = "",
    val password: String = "",
    val role: String = "",
    val isLoading: Boolean = false,
    val success: Boolean = false,
    val error: String? = null
)
