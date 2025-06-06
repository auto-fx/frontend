package com.example.auto_fx_front.auth.data

import com.example.auto_fx_front.auth.data.remote.*
import com.example.auto_fx_front.auth.domain.repository.AuthRepository
import com.example.auto_fx_front.auth.domain.repository.FakeUser

class RealAuthRepository(
    private val api: AuthApiService,
    private val sessionManager: SessionManager
) : AuthRepository {

    override suspend fun signUp(user: FakeUser): Boolean {
        val request = SignUpRequest(
            name = Name(user.firstName, user.lastName),
            emailAddress = EmailAddress(user.email),
            phoneNumber = PhoneNumber(user.countryCode, user.number),
            password = user.password,
            roles = listOf("ROLE_USER") // forzado por backend
        )
        val response = api.signUp(request)
        return response.isSuccessful
    }

    override suspend fun signIn(email: String, password: String): Boolean {
        val response = api.signIn(email, password)
        return if (response.isSuccessful && response.body() != null) {
            sessionManager.saveToken(response.body()!!.token)
            true
        } else {
            false
        }
    }

    override suspend fun updateUserData(firstName: String, lastName: String, countryCode: String, number: Int): Boolean = false
    override suspend fun updatePassword(currentPassword: String, newPassword: String): Boolean = false
}