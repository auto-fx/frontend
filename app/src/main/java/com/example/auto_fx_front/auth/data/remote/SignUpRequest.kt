// src/main/java/com/example/auto_fx_front/data/remote/SignUpRequest.kt
package com.example.auto_fx_front.auth.data.remote

data class Name(
    val firstName: String,
    val lastName: String
)

data class EmailAddress(
    val address: String
)

data class PhoneNumber(
    val countryCode: String,
    val number: Int
)

/**
 * Petición de registro:
 * {
 *   "name": { "firstName": "...", "lastName": "..." },
 *   "emailAddress": { "address": "..." },
 *   "phoneNumber": { "countryCode": "...", "number": ... },
 *   "password": "...",
 *   "roles": ["ROLE_USER"]
 * }
 */
data class SignUpRequest(
    val name: Name,
    val emailAddress: EmailAddress,
    val phoneNumber: PhoneNumber,
    val password: String,
    val roles: List<String>
)
