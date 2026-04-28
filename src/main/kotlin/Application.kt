package com.chandra

import configureDatabases
import database.ExpenseDao
import database.UserDao
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.calllogging.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.routing.*
import io.netty.handler.logging.LogLevel
import kotlinx.serialization.json.Json
import org.slf4j.event.Level
import plugin.configureSerialization
import routes.authRoutes
import routes.insertExpenses
import services.AuthService
import services.ExpenseService

fun main(args: Array<String>) {
    EngineMain.main(args)
}

fun Application.module() {
    install(ContentNegotiation) {
        json(Json {
            prettyPrint = true
            isLenient = true
        })
    }
    configureSerialization()
    configureDatabases()
   // configureSecurity()
    //configureRouting()
    /*install(Authentication) {
        jwt("auth-jwt") {
            verifier(verifier)  // IMPORTANT: same secret/algorithm
            validate { credential ->
                val userId = credential.payload.getClaim("userId").asString()
                val email = credential.payload.getClaim("email").asString()

                if (userId != null && email != null) {
                    JWTPrincipal(credential.payload)
                } else null
            }
        }
    }*/
    install(CallLogging) {
        level = Level.INFO
    }

    val userDao = UserDao()
    val authService = AuthService(userDao)

    val expenseDao = ExpenseDao()
    val expenseService = ExpenseService(expenseDao)

    routing {
        authRoutes(authService)
        insertExpenses(expenseService)
    }


}
