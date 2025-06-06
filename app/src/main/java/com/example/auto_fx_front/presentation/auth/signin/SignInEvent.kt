package com.example.auto_fx_front.presentation.auth.signin

sealed class SignInEvent {
    data class EmailChanged(val email: String) : SignInEvent()
    data class PasswordChanged(val password: String) : SignInEvent()
    object Submit : SignInEvent()
    object ClearMessages : SignInEvent()

}