// src/main/java/com/example/auto_fx_front/presentation/viewmodel/AuthViewModel.kt
package com.example.auto_fx_front.auth.presentation.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.auto_fx_front.auth.data.remote.EmailAddress
import com.example.auto_fx_front.auth.data.remote.Name
import com.example.auto_fx_front.auth.data.remote.PhoneNumber
import com.example.auto_fx_front.auth.data.remote.SignInResponse
import com.example.auto_fx_front.auth.data.repository.IAMRepository
import kotlinx.coroutines.launch

/**
 * Representa el estado de la UI en los flujos de login y registro.
 */
sealed class AuthUIState {
    object Idle       : AuthUIState()
    object Loading    : AuthUIState()
    object Success    : AuthUIState()
    data class Error(val msg: String) : AuthUIState()
}

class AuthViewModel(
    private val repo: IAMRepository
) : ViewModel() {

    // --- Campos para LOGIN (GET ?email=&password=) ---
    var emailAddr     by mutableStateOf("")
    var password      by mutableStateOf("")
    var keepSignedIn  by mutableStateOf(false)

    // --- Campos para SIGN UP (POST JSON anidado) ---
    var firstName     by mutableStateOf("")
    var lastName      by mutableStateOf("")
    var countryCode   by mutableStateOf("")
    var phoneNumber   by mutableStateOf("")  // almacenaremos como String y luego convertiremos a Int

    // Estado compartido de la UI
    var uiState by mutableStateOf<AuthUIState>(AuthUIState.Idle)
        private set

    /** Ejecuta el login GET /users/sign-in?email=...&password=... */
    fun login(onSuccess: (SignInResponse) -> Unit) {
        viewModelScope.launch {
            uiState = AuthUIState.Loading
            try {
                val resp = repo.signIn(emailAddr, password)
                // Guardamos token y preferencia
                repo.saveToken(resp.token)
                repo.saveKeepSignedIn(keepSignedIn)
                uiState = AuthUIState.Success
                onSuccess(resp)
            } catch (e: Exception) {
                uiState = AuthUIState.Error(e.localizedMessage ?: "Error de login")
            }
        }
    }

    /** Ejecuta el registro POST /users/sign-up con el JSON anidado */
    fun signUp(onSuccess: () -> Unit) {
        viewModelScope.launch {
            uiState = AuthUIState.Loading
            try {
                // Construimos los objetos anidados
                val nameObj        = Name(firstName.trim(), lastName.trim())
                val emailObj       = EmailAddress(emailAddr.trim())
                val phoneNumberInt = phoneNumber.toIntOrNull() ?: 0
                val phoneObj       = PhoneNumber(countryCode.trim(), phoneNumberInt)

                repo.signUp(
                    name         = nameObj,
                    emailAddress = emailObj,
                    phoneNumber  = phoneObj,
                    password     = password,
                    roles        = listOf("ROLE_USER")
                )
                uiState = AuthUIState.Success
                onSuccess()
            } catch (e: Exception) {
                uiState = AuthUIState.Error(e.localizedMessage ?: "Error de registro")
            }
        }
    }
}
