// src/main/java/com/example/auto_fx_front/data/store/TokenDataStore.kt
package com.example.auto_fx_front.auth.data.store

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class TokenDataStore(private val ds: DataStore<Preferences>) {
    private val KEY_TOKEN = stringPreferencesKey("token")
    private val KEY_KEEP  = booleanPreferencesKey("keepSignedIn")

    val token: Flow<String?> = ds.data.map { it[KEY_TOKEN] }
    val keepSignedIn: Flow<Boolean> = ds.data.map { it[KEY_KEEP] ?: false }

    suspend fun saveToken(token: String) = ds.edit { it[KEY_TOKEN] = token }
    suspend fun clearToken() = ds.edit { it.remove(KEY_TOKEN) }
    suspend fun saveKeepSignedIn(keep: Boolean) = ds.edit { it[KEY_KEEP] = keep }
}
