package com.example.auto_fx_front.auth.data

import com.example.auto_fx_front.auth.domain.repository.AuthRepository
import com.example.auto_fx_front.auth.domain.repository.FakeUser

class FakeAuthRepository : AuthRepository {

    private var currentUser: FakeUser? = null

    override suspend fun signUp(user: FakeUser): Boolean {
        currentUser = user
        return true
    }

    override suspend fun signIn(email: String, password: String): Boolean {
        return currentUser?.let {
            it.email == email && it.password == password
        } ?: false
    }

    override suspend fun updateUserData(
        firstName: String,
        lastName: String,
        countryCode: String,
        number: Int
    ): Boolean {
        currentUser = currentUser?.copy(
            firstName = firstName,
            lastName = lastName,
            countryCode = countryCode,
            number = number
        )
        return currentUser != null
    }

    override suspend fun updatePassword(currentPassword: String, newPassword: String): Boolean {
        return if (currentUser?.password == currentPassword) {
            currentUser = currentUser?.copy(password = newPassword)
            true
        } else {
            false
        }
    }
}
