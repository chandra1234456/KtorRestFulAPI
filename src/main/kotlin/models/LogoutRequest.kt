package models

import kotlinx.serialization.Serializable

@Serializable
data class LogoutRequest(
    val email: String,
)