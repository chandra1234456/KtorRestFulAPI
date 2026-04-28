package routes

import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.json.Json
import models.AddCustomerExpenses
import models.CommonRequestModel
import models.CommonResponseModel
import models.LoginRequest
import models.LoginResponse
import models.SignUpRequest
import services.AuthService
import services.ExpenseService
import utils.AESUtil
import utils.Constants.SECRET_KEY

fun Route.authRoutes(authService: AuthService) {

    route("/auth") {
        post("/signup") {
            val request = call.receive<SignUpRequest>()
            val result = authService.signup(request)
            call.respond(result)
        }
    }

    route("/auth") {
        post("/login") {
            try {

                val request = call.receive<CommonRequestModel>()
                println("Request: $request")

                val encrypted = request.requestBody
                    ?: throw IllegalArgumentException("requestBody is null")

                val decryptedJson = AESUtil.decrypt(encrypted, SECRET_KEY)
                println("Decrypted Request: $decryptedJson")

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
   /* route("/auth") {
        post("/logout") {
            val request = call.receive<LoginRequest>()
            val result = authService.loginCheck(request)
            call.respond<CommonResponseModel<LoginResponse>>(result)
        }
    }*/
}