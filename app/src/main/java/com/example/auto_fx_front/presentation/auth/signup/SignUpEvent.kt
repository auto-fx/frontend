package com.example.auto_fx_front.presentation.auth.signup

sealed class SignUpEvent {
    data class FirstNameChanged(val value: String) : SignUpEvent()
    data class LastNameChanged(val value: String) : SignUpEvent()
    data class EmailChanged(val value: String) : SignUpEvent()
    data class CountryCodeChanged(val value: String) : SignUpEvent()
    data class NumberChanged(val value: String) : SignUpEvent()
    data class PasswordChanged(val value: String) : SignUpEvent()
    data class RoleChanged(val value: String) : SignUpEvent()
    object Submit : SignUpEvent()
    object ClearMessages : SignUpEvent()

}
