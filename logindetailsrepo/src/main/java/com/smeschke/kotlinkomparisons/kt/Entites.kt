package com.smeschke.kotlinkomparisons.kt

/**
 * Created by Scott on 5/21/2017.
 */
data class LoginDetails(val email: String? = null, val username: String? = null) {

    companion object {
        val empty = LoginDetails()
    }

    val isPresent = email != null && username != null
}

enum class LoginMethod {
    CREDENTIALS, FINGERPRINT, PIN, UNKNOWN
}