package com.example.ufitness.utils

import android.util.Base64
import java.nio.charset.Charset
import java.security.Key
import javax.crypto.Cipher
import javax.crypto.spec.SecretKeySpec

class Encrypt {

    @Throws(Exception::class)
    fun encrypt(value: String): String {
        val key: Key = generateKey()
        val cipher: Cipher = Cipher.getInstance(ALGORITHM)
        cipher.init(Cipher.ENCRYPT_MODE, key)
        val encryptedByteValue: ByteArray =
            cipher.doFinal(value.toByteArray(charset("utf-8")))
        return Base64.encodeToString(encryptedByteValue, Base64.DEFAULT)
    }

    @Throws(Exception::class)
    fun decrypt(value: String?): String {
        val key: Key = generateKey()
        val cipher: Cipher = Cipher.getInstance(ALGORITHM)
        cipher.init(Cipher.DECRYPT_MODE, key)
        val decryptedValue64: ByteArray = Base64.decode(value, Base64.DEFAULT)
        val decryptedByteValue: ByteArray = cipher.doFinal(decryptedValue64)
        return String(decryptedByteValue, Charset.forName("utf-8"))
    }

    @Throws(Exception::class)
    private fun generateKey(): Key {
        return SecretKeySpec(KEY.toByteArray(), ALGORITHM)
    }

    companion object {
        private const val ALGORITHM = "AES"
        private const val KEY = "1Hbfh667adfDEJ78"
    }
}