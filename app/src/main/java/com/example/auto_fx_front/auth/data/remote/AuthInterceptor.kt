// src/main/java/com/example/auto_fx_front/data/remote/AuthInterceptor.kt
package com.example.auto_fx_front.auth.data.remote

import com.example.auto_fx_front.auth.data.store.TokenDataStore
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor(private val tokenStore: TokenDataStore) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val reqBuilder = chain.request().newBuilder()
        runBlocking {
            tokenStore.token.first()?.let { token ->
                reqBuilder.addHeader("Authorization", "Bearer $token")
            }
        }
        reqBuilder.addHeader("Content-Type", "application/json")
        return chain.proceed(reqBuilder.build())
    }
}
