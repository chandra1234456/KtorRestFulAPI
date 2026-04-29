package routes

import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import io.ktor.server.routing.route
import kotlinx.serialization.json.Json
import models.AddCustomerExpenses
import models.CommonRequestModel
import models.CommonResponseModel
import services.ExpenseService
import utils.AESUtil
import utils.Constants.SECRET_KEY
import utils.ResponseCodes

fun Route.expenseRoutes(authService: ExpenseService) {

    route("/expense") {
        post("/addExpense") {
            val request = call.receive<CommonRequestModel>()
            val encrypted = request.requestBody
                ?: throw IllegalArgumentException("requestBody is null")
            val decryptedJson = AESUtil.decrypt(encrypted, SECRET_KEY)
            val addExpenseRequest = Json.decodeFromString<AddCustomerExpenses>(decryptedJson)
            val result = authService.expensesInsert(addExpenseRequest)
            call.respond(result)
        }
    }
    route("/expense") {
        get("/getExpenses/{userId}") {
            val userId = call.parameters["userId"]?.toIntOrNull()
            if (userId == null || userId <= 0) {
                call.respond(
                    CommonResponseModel(
                        responseCode = ResponseCodes.VALIDATION_ERROR,
                        responseMessage = "Invalid userId",
                        responseData = "null"
                    )
                )
                return@get
            }
            val result = authService.getExpensesByUser(userId)
            call.respond(result)
        }
    }
}