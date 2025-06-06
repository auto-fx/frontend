package com.example.auto_fx_front.auth.data.remote

data class SignInResponse(
    val id: Long,
    val email: String,
    val token: String
)