package utils

import kotlinx.serialization.json.Json
import models.CommonResponseModel
import utils.Constants.SECRET_KEY


inline fun <reified T> secureResponse(
    code: String,
    message: String,
    data: T?
): CommonResponseModel<String> {

    val json = Json.encodeToString(data)
    val encrypted = AESUtil.encrypt(json, SECRET_KEY)

    return CommonResponseModel(
        responseCode = code,
        responseMessage = message,
        responseData = encrypted
    )
}