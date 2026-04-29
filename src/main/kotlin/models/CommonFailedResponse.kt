package models

import kotlinx.serialization.Serializable

@Serializable
data class CommonFailedResponse<T>(
    val responseCode: String,
    val responseMessage: String,
    val responseData: T? = null
)