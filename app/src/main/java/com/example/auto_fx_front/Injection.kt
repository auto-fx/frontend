// src/main/java/com/example/auto_fx_front/Injection.kt
package com.example.auto_fx_front

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.preferencesDataStoreFile
import com.example.auto_fx_front.auth.data.remote.AuthInterceptor
import com.example.auto_fx_front.auth.data.remote.IAMService
import com.example.auto_fx_front.auth.data.repository.IAMRepository
import com.example.auto_fx_front.auth.data.repository.IAMRepositoryImpl
import com.example.auto_fx_front.auth.data.store.TokenDataStore
import com.example.auto_fx_front.auth.presentation.viewmodel.AuthViewModelFactory
import com.example.auto_fx_front.auth.presentation.viewmodel.SplashViewModelFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import kotlin.apply
import kotlin.jvm.java

/**
 * Service locator manual: construye Retrofit, DataStore, repositorio y factories.
 */
object Injection {
    private const val BASE_URL =
        "https://api-gateway-server-production.up.railway.app/api/authentication-autofx/v1/"

    private lateinit var dataStore: DataStore<Preferences>

    /** Inicializar DataStore. Llamar desde tu Activity antes de usar cualquier ViewModel. */
    fun init(context: Context) {
        dataStore = PreferenceDataStoreFactory.create {
            context.preferencesDataStoreFile("auth_prefs")
        }
    }

    /** Retrofit + OkHttp + Moshi */
    private fun provideRetrofit(): Retrofit {
        val logging = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        val client = OkHttpClient.Builder()
            .addInterceptor(logging)
            .addInterceptor(AuthInterceptor(TokenDataStore(dataStore)))
            .build()

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
    }

    /** Servicio de red */
    private val iamService: IAMService by lazy {
        provideRetrofit().create(IAMService::class.java)
    }

    /** TokenDataStore */
    private val tokenStore: TokenDataStore by lazy {
        TokenDataStore(dataStore)
    }

    /** Repositorio */
    private val iamRepository: IAMRepository by lazy {
        IAMRepositoryImpl(iamService, tokenStore)
    }

    /** Factory para AuthViewModel */
    fun provideAuthViewModelFactory(): AuthViewModelFactory =
        AuthViewModelFactory(iamRepository)

    /** Factory para SplashViewModel */
    fun provideSplashViewModelFactory(): SplashViewModelFactory =
        SplashViewModelFactory(iamRepository)
}
