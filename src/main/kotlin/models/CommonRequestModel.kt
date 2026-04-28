package models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CommonRequestModel(
    val requestBody: String?,
)