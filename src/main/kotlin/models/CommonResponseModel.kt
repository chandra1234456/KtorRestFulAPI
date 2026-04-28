package models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class CommonResponseModel<T>(
    val responseCode: String,
    val responseMessage: String,
    val responseData: T? = null
)