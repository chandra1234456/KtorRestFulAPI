package utils

import models.LoginRequest
import models.SignUpRequest


fun validateRegister(req: SignUpRequest): String? {
    if (req.username.length < 3) return "Username too short"
    if (!req.email.contains("@")) return "Invalid email"
    if (req.password.length < 8) return "Password too short"
    return null
}

fun validateLogin(req: LoginRequest): String? {
    if (!req.email.contains("@")) return "Invalid email"
    if (req.password.length < 6) return "Password too short"
    return null
}