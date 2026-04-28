package models

import kotlinx.serialization.Serializable

@Serializable
data class LoginResponse(
    val status: String? = null,
    val token: String? = null,
    val userEmail: String? = null,
    val username: String? = null
)