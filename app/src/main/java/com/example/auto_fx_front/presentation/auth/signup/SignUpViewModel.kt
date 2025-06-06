package com.example.auto_fx_front.presentation.auth.signup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.auto_fx_front.auth.domain.repository.AuthRepository
import com.example.auto_fx_front.auth.domain.repository.FakeUser
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class SignUpViewModel(
    private val repository: AuthRepository
) : ViewModel() {

    private val _state = MutableStateFlow(SignUpState())
    val state: StateFlow<SignUpState> = _state

    fun onEvent(event: SignUpEvent) {
        when (event) {
            is SignUpEvent.FirstNameChanged -> _state.value = _state.value.copy(firstName = event.value)
            is SignUpEvent.LastNameChanged -> _state.value = _state.value.copy(lastName = event.value)
            is SignUpEvent.EmailChanged -> _state.value = _state.value.copy(email = event.value)
            is SignUpEvent.CountryCodeChanged -> _state.value = _state.value.copy(countryCode = event.value)
            is SignUpEvent.NumberChanged -> _state.value = _state.value.copy(number = event.value)
            is SignUpEvent.PasswordChanged -> _state.value = _state.value.copy(password = event.value)
            is SignUpEvent.RoleChanged -> _state.value = _state.value.copy(role = event.value)

            is SignUpEvent.Submit -> {
                val validationError = validateInputs()
                if (validationError != null) {
                    _state.value = _state.value.copy(error = validationError)
                    return
                }

                _state.value = _state.value.copy(isLoading = true, error = null)
                viewModelScope.launch {
                    val user = FakeUser(
                        firstName = _state.value.firstName,
                        lastName = _state.value.lastName,
                        email = _state.value.email,
                        countryCode = _state.value.countryCode,
                        number = _state.value.number.toIntOrNull() ?: 0,
                        password = _state.value.password,
                        roles = listOf(_state.value.role)
                    )
                    val result = repository.signUp(user)
                    _state.value = _state.value.copy(
                        isLoading = false,
                        success = result,
                        error = if (!result) "Error al registrar" else null
                    )
                }
            }


            is SignUpEvent.Submit -> {
                val validationError = validateInputs()
                if (validationError != null) {
                    _state.value = _state.value.copy(error = validationError)
                    return
                }

                _state.value = _state.value.copy(isLoading = true, error = null)
                viewModelScope.launch {
                    val user = FakeUser(
                        firstName = _state.value.firstName,
                        lastName = _state.value.lastName,
                        email = _state.value.email,
                        countryCode = _state.value.countryCode,
                        number = _state.value.number.toIntOrNull() ?: 0,
                        password = _state.value.password,
                        roles = listOf(_state.value.role)
                    )
                    val result = repository.signUp(user)
                    _state.value = _state.value.copy(
                        isLoading = false,
                        success = result,
                        error = if (!result) "Error al registrar" else null
                    )
                }
            }

            is SignUpEvent.ClearMessages -> {
                _state.value = _state.value.copy(error = null, success = false)
            }
        }
    }

    private fun validateInputs(): String? {
        val s = _state.value

        if (s.firstName.isBlank() || !s.firstName.matches(Regex("^[A-Za-zÁÉÍÓÚáéíóúñÑ ]+$")))
            return "Nombre inválido"

        if (s.lastName.isBlank() || !s.lastName.matches(Regex("^[A-Za-zÁÉÍÓÚáéíóúñÑ ]+$")))
            return "Apellido inválido"

        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(s.email).matches())
            return "Correo inválido"

        if (!s.countryCode.startsWith("+") || s.countryCode.length !in 2..4)
            return "Código de país inválido"

        if (s.number.length < 6 || !s.number.all { it.isDigit() })
            return "Número telefónico inválido"

        if (s.password.length < 6)
            return "Contraseña muy corta"

        if (s.role.isBlank())
            return "Rol no puede estar vacío"

        return null
    }
}
