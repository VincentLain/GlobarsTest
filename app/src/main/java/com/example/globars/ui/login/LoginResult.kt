package com.example.globars.ui.login

/**
 * Authentication result : success (user details) or error message.
 */
data class LoginResult(
        val success: LoggedInUser? = null,
        val error: Int? = null
)