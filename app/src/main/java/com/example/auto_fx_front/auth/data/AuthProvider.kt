package com.example.auto_fx_front.auth.data

import android.content.Context
import com.example.auto_fx_front.auth.data.remote.AuthApiService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object AuthProvider {

    private lateinit var sessionManager: SessionManager

    fun init(context: Context) {
        sessionManager = SessionManager(context)
    }

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://api-gateway-server-production.up.railway.app")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val api = retrofit.create(AuthApiService::class.java)

    val realRepository by lazy {
        RealAuthRepository(api, sessionManager)
    }
}