package com.example.auto_fx_front.auth.domain.repository

data class FakeUser(
    val firstName: String,
    val lastName: String,
    val email: String,
    val countryCode: String,
    val number: Int,
    val password: String,
    val roles: List<String>
)

interface AuthRepository {
    suspend fun signUp(user: FakeUser): Boolean
    suspend fun signIn(email: String, password: String): Boolean
    suspend fun updateUserData(firstName: String, lastName: String, countryCode: String, number: Int): Boolean
    suspend fun updatePassword(currentPassword: String, newPassword: String): Boolean
}