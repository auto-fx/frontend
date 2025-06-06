package com.example.auto_fx_front.auth.data.remote

data class SignUpRequest(
    val name: Name,
    val emailAddress: EmailAddress,
    val phoneNumber: PhoneNumber,
    val password: String,
    val roles: List<String>
)

data class Name(val firstName: String, val lastName: String)
data class EmailAddress(val address: String)
data class PhoneNumber(val countryCode: String, val number: Int)