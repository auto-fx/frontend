package com.example.auto_fx_front.auth.data.remote

import com.example.auto_fx_front.auth.data.remote.SignUpRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Query

interface AuthApiService {

    @POST("/api/authentication-autofx/v1/users/sign-up")
    suspend fun signUp(@Body request: SignUpRequest): Response<Unit>

    @GET("/api/authentication-autofx/v1/users/sign-in")
    suspend fun signIn(
        @Query("email") email: String,
        @Query("password") password: String,
        @Header("accept") accept: String = "application/json"
    ): Response<SignInResponse>
}