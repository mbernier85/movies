package im.bernier.movies.datasource

import android.content.Context
import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.nio.charset.Charset
import java.security.KeyStore
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey
import javax.crypto.spec.GCMParameterSpec
import javax.inject.Inject

/**
 * Handles encryption and decryption
 */
interface CryptographyManager {
    fun getInitializedCipherForEncryption(keyName: String): Cipher

    fun getInitializedCipherForDecryption(
        keyName: String,
        initializationVector: ByteArray,
    ): Cipher

    /**
     * The Cipher created with [getInitializedCipherForEncryption] is used here
     */
    fun encryptData(
        plaintext: String,
        cipher: Cipher,
    ): CiphertextWrapper

    /**
     * The Cipher created with [getInitializedCipherForDecryption] is used here
     */
    fun decryptData(
        ciphertext: ByteArray,
        cipher: Cipher,
    ): String

    fun persistCiphertextWrapperToSharedPrefs(
        ciphertextWrapper: CiphertextWrapper,
        context: Context,
        filename: String,
        mode: Int,
        prefKey: String,
    )

    fun getCiphertextWrapperFromSharedPrefs(
        context: Context,
        filename: String,
        mode: Int,
        prefKey: String,
    ): CiphertextWrapper?
}

const val KEY_SIZE = 256
const val ANDROID_KEYSTORE = "AndroidKeyStore"
const val ENCRYPTION_BLOCK_MODE = KeyProperties.BLOCK_MODE_GCM
const val ENCRYPTION_PADDING = KeyProperties.ENCRYPTION_PADDING_NONE
const val ENCRYPTION_ALGORITHM = KeyProperties.KEY_ALGORITHM_AES

/**
 * To get an instance of this private CryptographyManagerImpl class, use the top-level function
 * fun CryptographyManager(): CryptographyManager = CryptographyManagerImpl()
 */
class CryptographyManagerImpl
    @Inject
    constructor() : CryptographyManager {
        override fun getInitializedCipherForEncryption(keyName: String): Cipher {
            val cipher = getCipher()
            val secretKey = getOrCreateSecretKey(keyName)
            cipher.init(Cipher.ENCRYPT_MODE, secretKey)
            return cipher
        }

        override fun getInitializedCipherForDecryption(
            keyName: String,
            initializationVector: ByteArray,
        ): Cipher {
            val cipher = getCipher()
            val secretKey = getOrCreateSecretKey(keyName)
            cipher.init(Cipher.DECRYPT_MODE, secretKey, GCMParameterSpec(128, initializationVector))
            return cipher
        }

        override fun encryptData(
            plaintext: String,
            cipher: Cipher,
        ): CiphertextWrapper {
            val ciphertext = cipher.doFinal(plaintext.toByteArray(Charset.forName("UTF-8")))
            return CiphertextWrapper(ciphertext, cipher.iv)
        }

        override fun decryptData(
            ciphertext: ByteArray,
            cipher: Cipher,
        ): String {
            val plaintext = cipher.doFinal(ciphertext)
            return String(plaintext, Charset.forName("UTF-8"))
        }

        private fun getCipher(): Cipher {
            val transformation = "$ENCRYPTION_ALGORITHM/$ENCRYPTION_BLOCK_MODE/$ENCRYPTION_PADDING"
            return Cipher.getInstance(transformation)
        }

        private fun getOrCreateSecretKey(keyName: String): SecretKey {
            // If Secretkey was previously created for that keyName, then grab and return it.
            val keyStore = KeyStore.getInstance(ANDROID_KEYSTORE)
            keyStore.load(null) // Keystore must be loaded before it can be accessed
            keyStore.getKey(keyName, null)?.let { return it as SecretKey }

            // if you reach here, then a new SecretKey must be generated for that keyName
            val paramsBuilder =
                KeyGenParameterSpec.Builder(
                    keyName,
                    KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT,
                )
            paramsBuilder.apply {
                setBlockModes(ENCRYPTION_BLOCK_MODE)
                setEncryptionPaddings(ENCRYPTION_PADDING)
                setKeySize(KEY_SIZE)
                setUserAuthenticationRequired(false)
            }

            val keyGenParams = paramsBuilder.build()
            val keyGenerator =
                KeyGenerator.getInstance(
                    KeyProperties.KEY_ALGORITHM_AES,
                    ANDROID_KEYSTORE,
                )
            keyGenerator.init(keyGenParams)
            return keyGenerator.generateKey()
        }

        @OptIn(ExperimentalSerializationApi::class)
        override fun persistCiphertextWrapperToSharedPrefs(
            ciphertextWrapper: CiphertextWrapper,
            context: Context,
            filename: String,
            mode: Int,
            prefKey: String,
        ) {
            val json = Json.encodeToString(ciphertextWrapper)
            context
                .getSharedPreferences(filename, mode)
                .edit()
                .putString(prefKey, json)
                .apply()
        }

        @OptIn(ExperimentalSerializationApi::class)
        override fun getCiphertextWrapperFromSharedPrefs(
            context: Context,
            filename: String,
            mode: Int,
            prefKey: String,
        ): CiphertextWrapper {
            val json = context.getSharedPreferences(filename, mode).getString(prefKey, null) ?: ""
            return Json.decodeFromString<CiphertextWrapper>(json)
        }
    }

@Serializable
data class CiphertextWrapper(
    val ciphertext: ByteArray,
    val initializationVector: ByteArray,
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as CiphertextWrapper

        if (!ciphertext.contentEquals(other.ciphertext)) return false
        return initializationVector.contentEquals(other.initializationVector)
    }

    override fun hashCode(): Int {
        var result = ciphertext.contentHashCode()
        result = 31 * result + initializationVector.contentHashCode()
        return result
    }
}
