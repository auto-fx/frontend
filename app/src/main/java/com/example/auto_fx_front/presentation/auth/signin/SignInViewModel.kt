package com.example.auto_fx_front.presentation.auth.signin

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.auto_fx_front.auth.domain.repository.AuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class SignInViewModel(
    private val repository: AuthRepository
) : ViewModel() {

    private val _state = MutableStateFlow(SignInState())
    val state: StateFlow<SignInState> = _state

    fun onEvent(event: SignInEvent) {
        when (event) {
            is SignInEvent.EmailChanged -> {
                _state.value = _state.value.copy(email = event.email)
            }

            is SignInEvent.PasswordChanged -> {
                _state.value = _state.value.copy(password = event.password)
            }

            is SignInEvent.ClearMessages -> {
                _state.value = _state.value.copy(error = null)
            }

            is SignInEvent.Submit -> {
                val error = validateInputs()
                if (error != null) {
                    _state.value = _state.value.copy(error = error)
                    return
                }

                _state.value = _state.value.copy(isLoading = true, error = null)
                viewModelScope.launch {
                    val result = repository.signIn(
                        email = _state.value.email,
                        password = _state.value.password
                    )
                    _state.value = _state.value.copy(
                        isLoading = false,
                        error = if (!result) "Credenciales incorrectas" else null
                    )
                }
            }
        }
    }

    private fun validateInputs(): String? {
        val s = _state.value

        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(s.email).matches())
            return "Correo inválido"

        return null
    }
}
