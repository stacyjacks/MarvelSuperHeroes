package kurmakaeva.anastasia.marvelsuperheroes.network

import java.security.MessageDigest

const val BASE_URL = "https://gateway.marvel.com:443"

const val API_KEY = "apikey"
const val TIMESTAMP = "ts"
const val HASH = "hash"
const val TIMEZONE = "UTC"

val String.md5: String
    get() {
        val bytes = MessageDigest.getInstance("MD5").digest(this.toByteArray())
        return bytes.joinToString("") {
            "%02x".format(it)
        }
    }
