package routes

import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import io.ktor.server.routing.route
import models.AddCustomerExpenses
import services.ExpenseService

fun Route.insertExpenses(authService: ExpenseService) {

    route("/expense") {
        post("/addExpense") {
            val request = call.receive<AddCustomerExpenses>()
            println("RAW JSON: $request")
            val result = authService.expensesInsert(request)
            call.respond(result)
        }
    }
    route("/expense") {
        get("/getExpenses/{userId}") {
            val userId = call.parameters["userId"]?.toInt()
            val result = authService.getExpensesByUser(userId!!)
            call.respond(result)
        }
    }
}