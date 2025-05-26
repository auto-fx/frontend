package com.example.auto_fx_front.presentation.auth.update_password

sealed class UpdatePasswordEvent {
    data class CurrentPasswordChanged(val value: String) : UpdatePasswordEvent()
    data class NewPasswordChanged(val value: String) : UpdatePasswordEvent()
    object Submit : UpdatePasswordEvent()
    object ClearMessages : UpdatePasswordEvent()

}