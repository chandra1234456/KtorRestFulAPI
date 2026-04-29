package routes

import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.json.Json
import models.CommonRequestModel
import models.CommonResponseModel
import models.LoginRequest
import models.LoginResponse
import models.LogoutRequest
import models.SignUpRequest
import services.AuthService
import utils.AESUtil
import utils.Constants.SECRET_KEY

fun Route.authRoutes(authService: AuthService) {

    route("/auth") {
        post("/signup") {
            val request = call.receive<CommonRequestModel>()
            val encrypted = request.requestBody
                ?: throw IllegalArgumentException("requestBody is null")
            val decryptedJson = AESUtil.decrypt(encrypted, SECRET_KEY)
            val signUpRequest = Json.decodeFromString<SignUpRequest>(decryptedJson)
            val result = authService.signup(signUpRequest)
            call.respond(result)
        }
    }

    route("/auth") {
        post("/login") {
            try {
                val request = call.receive<CommonRequestModel>()
                val encrypted = request.requestBody
                    ?: throw IllegalArgumentException("requestBody is null")
                val decryptedJson = AESUtil.decrypt(encrypted, SECRET_KEY)
                val loginRequest = Json.decodeFromString<LoginRequest>(decryptedJson)
                val result = authService.loginCheck(loginRequest)
                call.respond(result)
            } catch (e: Exception) {
                e.printStackTrace()

                call.respond(
                    CommonResponseModel<LoginResponse>(
                        responseCode = "500",
                        responseMessage = e.message ?: "Error",
                        responseData = null
                    )
                )
            }
        }
    }
    route("/auth") {
        post("/logout") {
            val request = call.receive<CommonRequestModel>()
            val encrypted = request.requestBody
                ?: throw IllegalArgumentException("requestBody is null")
            val decryptedJson = AESUtil.decrypt(encrypted, SECRET_KEY)
            val logoutRequest = Json.decodeFromString<LogoutRequest>(decryptedJson)
            val result = authService.logoutCheck(logoutRequest)
            call.respond(result)
        }
    }
}