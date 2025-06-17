// src/main/java/com/example/auto_fx_front/data/remote/SignInResponse.kt
package com.example.auto_fx_front.auth.data.remote

data class SignInResponse(
    val id: Int,
    val username: String,
    val token: String,
    val role: String
)
