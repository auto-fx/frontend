// src/main/java/com/example/auto_fx_front/data/repository/IAMRepository.kt
package com.example.auto_fx_front.auth.data.repository

import com.example.auto_fx_front.auth.data.remote.SignInResponse
import com.example.auto_fx_front.auth.data.remote.SignUpResponse
import com.example.auto_fx_front.auth.data.remote.Name
import com.example.auto_fx_front.auth.data.remote.EmailAddress
import com.example.auto_fx_front.auth.data.remote.PhoneNumber
import kotlinx.coroutines.flow.Flow

interface IAMRepository {
    suspend fun signIn(email: String, password: String): SignInResponse

    // Nueva firma anidada
    suspend fun signUp(
        name: Name,
        emailAddress: EmailAddress,
        phoneNumber: PhoneNumber,
        password: String,
        roles: List<String>
    ): SignUpResponse

    val token: Flow<String?>
    val keepSignedIn: Flow<Boolean>

    suspend fun saveToken(token: String)
    suspend fun clearToken()
    suspend fun saveKeepSignedIn(keep: Boolean)
}
