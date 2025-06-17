// src/main/java/com/example/auto_fx_front/data/remote/IAMService.kt
package com.example.auto_fx_front.auth.data.remote

import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Body
import retrofit2.http.Query

interface IAMService {

    /**
     * GET /api/authentication-autofx/v1/users/sign-in?email={email}&password={password}
     */
    @GET("users/sign-in")
    suspend fun signIn(
        @Query("email") email: String,
        @Query("password") password: String
    ): SignInResponse

    /**
     * POST /api/authentication-autofx/v1/users/sign-up
     * Body JSON anidado como en SignUpRequest
     */
    @POST("users/sign-up")
    suspend fun signUp(
        @Body req: SignUpRequest
    ): SignUpResponse
}
