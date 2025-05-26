package com.example.auto_fx_front.presentation.auth.update_password

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.auto_fx_front.auth.domain.repository.AuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class UpdatePasswordViewModel(
    private val repository: AuthRepository
) : ViewModel() {

    private val _state = MutableStateFlow(UpdatePasswordState())
    val state: StateFlow<UpdatePasswordState> = _state

    fun onEvent(event: UpdatePasswordEvent) {
        when (event) {
            is UpdatePasswordEvent.CurrentPasswordChanged -> {
                _state.value = _state.value.copy(currentPassword = event.value)
            }

            is UpdatePasswordEvent.NewPasswordChanged -> {
                _state.value = _state.value.copy(newPassword = event.value)
            }

            is UpdatePasswordEvent.Submit -> {
                val error = validateInputs()
                if (error != null) {
                    _state.value = _state.value.copy(error = error)
                    return
                }

                _state.value = _state.value.copy(isLoading = true, error = null)
                viewModelScope.launch {
                    val success = repository.updatePassword(
                        currentPassword = _state.value.currentPassword,
                        newPassword = _state.value.newPassword
                    )
                    _state.value = _state.value.copy(
                        isLoading = false,
                        success = success,
                        error = if (!success) "Contraseña actual incorrecta" else null
                    )
                }
            }

            is UpdatePasswordEvent.ClearMessages -> {
                _state.value = _state.value.copy(error = null, success = false)
            }
        }
    }

    private fun validateInputs(): String? {
        val s = _state.value

        if (s.currentPassword.isBlank())
            return "Debes ingresar tu contraseña actual"

        if (s.newPassword.length < 6)
            return "La nueva contraseña debe tener al menos 6 caracteres"

        return null
    }
}
