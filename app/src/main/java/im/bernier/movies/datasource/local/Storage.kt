package im.bernier.movies.datasource.local

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ActivityRetainedScoped
import im.bernier.movies.crypto.CryptographyManager
import jakarta.inject.Inject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

const val KEY_NAME = "im.bernier.movies.key3"

@Serializable
data class Settings(
    val accountId: String = "",
    val sessionId: String = "",
)

@ActivityRetainedScoped
class Storage
    @Inject
    constructor(
        private val crypto: CryptographyManager,
        @param:ApplicationContext private val context: Context,
    ) {
        val settingsFlow: StateFlow<Settings>

        init {
            settingsFlow =
                get()
                    .map {
                        try {
                            Json.decodeFromString<Settings>(it)
                        } catch (_: Exception) {
                            Settings()
                        }
                    }.stateIn(CoroutineScope(Dispatchers.Default), SharingStarted.Eagerly, Settings())
        }

        suspend fun setSettings(value: Settings) {
            set(Json.encodeToString(value))
        }

        fun getSessionId() = settingsFlow.value.sessionId

        fun getAccountId() = settingsFlow.value.accountId

        suspend fun setSessionId(value: String) {
            val settings = settingsFlow.value.copy(sessionId = value)
            setSettings(settings)
        }

        suspend fun setAccountId(value: String) {
            val settings = settingsFlow.value.copy(accountId = value)
            setSettings(settings)
        }

        private suspend fun set(value: String) {
            val data =
                crypto.encryptData(
                    plaintext = value,
                    cipher = crypto.getInitializedCipherForEncryption(KEY_NAME),
                )
            crypto.persistCiphertextWrapperToSharedPrefs(
                ciphertextWrapper = data,
                context = context,
            )
        }

        private fun get(): Flow<String> =
            crypto
                .getCiphertextWrapperFromSharedPrefs(
                    context = context,
                ).map {
                    if (it.initializationVector.isEmpty()) return@map ""
                    crypto.decryptData(it.ciphertext, crypto.getInitializedCipherForDecryption(KEY_NAME, it.initializationVector))
                }

        suspend fun clear() {
            setSettings(Settings())
        }
    }
