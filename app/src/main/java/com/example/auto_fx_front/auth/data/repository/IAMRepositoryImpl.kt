// src/main/java/com/example/auto_fx_front/data/repository/IAMRepositoryImpl.kt
package com.example.auto_fx_front.auth.data.repository

import com.example.auto_fx_front.auth.data.remote.IAMService
import com.example.auto_fx_front.auth.data.remote.SignUpRequest
import com.example.auto_fx_front.auth.data.remote.SignInResponse
import com.example.auto_fx_front.auth.data.remote.SignUpResponse
import com.example.auto_fx_front.auth.data.remote.PhoneNumber
import com.example.auto_fx_front.auth.data.remote.EmailAddress
import com.example.auto_fx_front.auth.data.remote.Name
import com.example.auto_fx_front.auth.data.store.TokenDataStore
import kotlinx.coroutines.flow.Flow

class IAMRepositoryImpl(
    private val service: IAMService,
    private val store: TokenDataStore
) : IAMRepository {

    override suspend fun signIn(email: String, password: String): SignInResponse =
        service.signIn(email, password)

    override suspend fun signUp(
        name: Name,
        emailAddress: EmailAddress,
        phoneNumber: PhoneNumber,
        password: String,
        roles: List<String>
    ): SignUpResponse =
        service.signUp(SignUpRequest(name, emailAddress, phoneNumber, password, roles))

    override val token: Flow<String?> = store.token
    override val keepSignedIn: Flow<Boolean> = store.keepSignedIn

    override suspend fun saveToken(token: String) { store.saveToken(token) }
    override suspend fun clearToken()        { store.clearToken() }
    override suspend fun saveKeepSignedIn(keep: Boolean) { store.saveKeepSignedIn(keep) }
}
