package services

import database.UserDao
import models.CommonResponseModel
import models.LoginRequest
import models.LoginResponse
import models.LogoutRequest
import models.SignUpRequest
import models.ValidationError
import utils.JwtService
import utils.ResponseCodes
import utils.hashPassword
import utils.secureResponse
import utils.validateLogin
import utils.validateRegister

class AuthService(private val userDao: UserDao) {

    fun signup(req: SignUpRequest): CommonResponseModel<String> {

        val error = validateRegister(req)
        if (error != null) return CommonResponseModel(
            responseCode = error.code,
            responseMessage = error.message,
            responseData = "null"
        )

        if (userDao.findByEmail(req.email)) {
            return CommonResponseModel(
                responseCode = ResponseCodes.VALIDATION_ERROR,
                responseMessage = "User already exists",
                responseData = "null"
            )
        }

        if (userDao.findByUsername(req.username)) {
            return CommonResponseModel(
                responseCode = ResponseCodes.VALIDATION_ERROR,
                responseMessage = "Username already exists",
                responseData = "null"
            )
        }

        val hashed = hashPassword(req.password)

        userDao.insertUser(req.username, req.email, hashed, false)

        return CommonResponseModel(
            ResponseCodes.SUCCESS,
            "Success",
            "Signup Successful"
        )
    }
    fun loginCheck(req: LoginRequest): CommonResponseModel<String> {
        validateLogin(req)?.let { error ->
            return CommonResponseModel(
                responseCode = error.code,
                responseMessage = error.message,
                responseData = "null"
            )
        }

        val user = userDao.findUserByEmail(req.email)
            ?: return CommonResponseModel(
                responseCode = ResponseCodes.VALIDATION_ERROR,
                responseMessage = "User not found",
                responseData = "null"
            )
        if (req.password != user.passwordHash) {
            return CommonResponseModel(
                responseCode = ResponseCodes.VALIDATION_ERROR,
                responseMessage = "Invalid password",
                responseData = "null"
            )
        }
        if (user.isUserLoggedIn){
            return CommonResponseModel(
                responseCode = ResponseCodes.VALIDATION_ERROR,
                responseMessage = "User Already LoggedIn, Please Logout & Try Again",
                responseData = "null"
            )
        }

        val token = JwtService.generateToken(user.id, user.email)

        val loginResponse = LoginResponse(
            status = "Login successful",
            token = token,
            userEmail = user.email,
            username = user.username
        )
        // 🔥 IMPORTANT: update login flag
        userDao.updateLoginStatus(user.email, true)
        return secureResponse(
            ResponseCodes.SUCCESS,
            "Success",
            loginResponse
        )
    }
    fun logoutCheck(request : LogoutRequest): CommonResponseModel<String>{
        if (request.email.isEmpty()) {
            return CommonResponseModel(
                responseCode = ResponseCodes.VALIDATION_ERROR,
                responseMessage = "Email should Not empty",
                responseData = "null"
            )
        }
        if (!request.email.contains("@")) {
            return CommonResponseModel(
                responseCode = ResponseCodes.VALIDATION_ERROR,
                responseMessage = "Invalid email",
                responseData = "null"
            )
        }
        if (!userDao.findByEmail(request.email)) {
            return CommonResponseModel(
                responseCode = ResponseCodes.VALIDATION_ERROR,
                responseMessage = "User not exists",
                responseData = "null"
            )
        }

        userDao.updateLoginStatus(request.email, false)
        return CommonResponseModel(
            ResponseCodes.SUCCESS,
            "Success",
            "Logout Successful"
        )
    }
}