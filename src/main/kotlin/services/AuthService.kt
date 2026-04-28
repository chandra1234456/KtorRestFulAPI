package services

import database.UserDao
import kotlinx.serialization.json.Json
import models.CommonResponseModel
import models.LoginRequest
import models.LoginResponse
import models.SignUpRequest
import utils.AESUtil
import utils.Constants.SECRET_KEY
import utils.JwtService
import utils.hashPassword
import utils.secureResponse
import utils.validateLogin
import utils.validateRegister

class AuthService(private val userDao: UserDao) {

    fun signup(req: SignUpRequest): String {

        // 1. Validate
        val error = validateRegister(req)
        if (error != null) return error

        // 2. Check existing user
        if (userDao.findByEmail(req.email)) {
            return "User already exists"
        }
        if (userDao.findByEmail(req.username)) {
            return "Username already exists"
        }

        // 3. Hash password
        val hashed = hashPassword(req.password)

        // 4. Insert
        userDao.insertUser(req.username, req.email, hashed)

        return "Signup successful"
    }
    fun loginCheck(req: LoginRequest): CommonResponseModel<String> {

        validateLogin(req)?.let { error ->
            return secureResponse(
                code = "400",
                message = error,
                data = LoginResponse(
                    status = error,
                    token = null,
                    userEmail = null,
                    username = null
                )
            )
        }

        val user = userDao.findUserByEmail(req.email)
            ?: return secureResponse(
                "404",
                "User not found",
                LoginResponse("User not found", null, null, null)
            )

        if (req.password != user.passwordHash) {
            return secureResponse(
                "401",
                "Invalid password",
                LoginResponse("Invalid password", null, null, null)
            )
        }

        val token = JwtService.generateToken(user.id, user.email)

        val loginResponse = LoginResponse(
            status = "Login successful",
            token = token,
            userEmail = user.email,
            username = user.username
        )

        return secureResponse(
            "000",
            "Success",
            loginResponse
        )
    }
}