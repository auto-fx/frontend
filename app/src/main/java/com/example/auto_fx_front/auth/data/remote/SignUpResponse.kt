// src/main/java/com/example/auto_fx_front/data/remote/SignUpResponse.kt
package com.example.auto_fx_front.auth.data.remote

data class NameResponse(
    val firstName: String,
    val lastName: String
)

data class EmailAddressResponse(
    val address: String
)

data class PhoneNumberResponse(
    val countryCode: String,
    val number: Int
)

/**
 * Respuesta de registro:
 * {
 *   "id": 10,
 *   "name": { "firstName": "...", "lastName": "..." },
 *   "phoneNumber": { "countryCode": "...", "number": ... },
 *   "emailAddress": { "address": "..." },
 *   "roles": ["ROLE_USER"]
 * }
 */
data class SignUpResponse(
    val id: Int,
    val name: NameResponse,
    val phoneNumber: PhoneNumberResponse,
    val emailAddress: EmailAddressResponse,
    val roles: List<String>
)
