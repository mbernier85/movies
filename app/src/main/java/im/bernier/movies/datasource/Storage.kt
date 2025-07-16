package im.bernier.movies.datasource

import android.content.Context
import android.content.Context.MODE_PRIVATE
import dagger.hilt.android.qualifiers.ApplicationContext
import timber.log.Timber
import javax.inject.Inject

const val KEY_NAME = "im.bernier.movies.key3"
const val PREF_KEY_SESSION = "im.bernier.movies.session.id"
const val PREF_KEY_ACCOUNT = "im.bernier.movies.account.id"
const val FILE_NAME = "im.bernier.movies.session.file.name"

class Storage
    @Inject
    constructor(
        private val crypto: CryptographyManager,
        @param:ApplicationContext private val context: Context,
    ) {
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
                Timber.e(e)
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
    }
