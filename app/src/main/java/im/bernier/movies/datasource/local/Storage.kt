package im.bernier.movies.datasource.local

import android.content.Context
import android.content.Context.MODE_PRIVATE
import androidx.datastore.core.DataStore
import androidx.datastore.core.Serializer
import androidx.datastore.dataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import im.bernier.movies.crypto.CryptographyManager
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import timber.log.Timber
import java.io.InputStream
import java.io.OutputStream

const val KEY_NAME = "im.bernier.movies.key3"
const val PREF_KEY_SESSION = "im.bernier.movies.session.id"
const val PREF_KEY_ACCOUNT = "im.bernier.movies.account.id"
const val FILE_NAME = "im.bernier.movies.session.file.name"

@Serializable
data class Settings(
    val accountId: String = "",
    val sessionId: String = "",
)

object SettingsSerializer : Serializer<Settings> {
    override suspend fun readFrom(input: InputStream): Settings = Json.decodeFromString(input.readBytes().decodeToString())

    override suspend fun writeTo(
        t: Settings,
        output: OutputStream,
    ) {
        output.write(Json.encodeToString(t).encodeToByteArray())
    }

    override val defaultValue: Settings
        get() = Settings()
}

val Context.dataStore: DataStore<Settings> by dataStore(
    fileName = "settings.json",
    serializer = SettingsSerializer,
)

class Storage
    @Inject
    constructor(
        private val crypto: CryptographyManager,
        @param:ApplicationContext private val context: Context,
    ) {
        fun accountId(): Flow<String> = context.dataStore.data.map { settings -> settings.accountId }

        fun sessionId(): Flow<String> = context.dataStore.data.map { settings -> settings.sessionId }

        suspend fun updateSesionId(sessionId: String) {
            context.dataStore.updateData { settings ->
                settings.copy(sessionId = sessionId)
            }
        }

        suspend fun updateAccountId(accountId: String) {
            context.dataStore.updateData { settings ->
                settings.copy(accountId = accountId)
            }
        }

        fun setAccountId(accountId: String) {
            set(PREF_KEY_ACCOUNT, accountId)
        }

        fun getAccountId(): String = get(PREF_KEY_ACCOUNT)

        fun setSessionId(sessionId: String) {
            set(PREF_KEY_SESSION, sessionId)
        }

        fun getSessionId(): String =
            try {
                get(PREF_KEY_SESSION)
            } catch (e: Exception) {
                Timber.e(e.message)
                ""
            }

        private fun set(
            key: String,
            value: String,
        ) {
            val data =
                crypto.encryptData(value, crypto.getInitializedCipherForEncryption(KEY_NAME))
            crypto.persistCiphertextWrapperToSharedPrefs(
                data,
                context,
                FILE_NAME,
                MODE_PRIVATE,
                key,
            )
        }

        private fun get(key: String): String {
            val cypherTextWrapper =
                crypto.getCiphertextWrapperFromSharedPrefs(
                    context,
                    FILE_NAME,
                    MODE_PRIVATE,
                    key,
                ) ?: return ""
            return crypto.decryptData(
                cypherTextWrapper.ciphertext,
                crypto.getInitializedCipherForDecryption(KEY_NAME, cypherTextWrapper.initializationVector),
            )
        }

        fun clear() {
            setAccountId("")
            setSessionId("")
        }
    }
