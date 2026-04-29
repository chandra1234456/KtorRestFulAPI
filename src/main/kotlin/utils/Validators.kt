package utils

import models.AddCustomerExpenses
import models.LoginRequest
import models.SignUpRequest
import models.ValidationError


fun validateRegister(req: SignUpRequest): ValidationError? {

    if (req.username.isBlank()) {
        return ValidationError("001", "Username is required")
    }

    if (req.username.length < 3) {
        return ValidationError("001", "Username must be at least 3 characters")
    }

    if (req.email.isBlank()) {
        return ValidationError("001", "Email is required")
    }

    if (!req.email.contains("@")) {
        return ValidationError("001", "Invalid email")
    }

    if (req.password.isBlank()) {
        return ValidationError("001", "Password is required")
    }

    if (req.password.length < 6) {
        return ValidationError("001", "Password must be at least 8 characters")
    }

    return null
}

fun validateLogin(req: LoginRequest): ValidationError? {
    if (req.email.isBlank()) {
        return ValidationError("001", "Email is required")
    }

    if (!req.email.matches(Regex("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$"))) {
        return ValidationError("001", "Invalid email format")
    }

    if (req.password.isBlank()) {
        return ValidationError("001", "Password is required")
    }

    if (req.password.length < 6) {
        return ValidationError("001", "Password too short")
    }

    return null
}

fun validateExpense(req: AddCustomerExpenses): ValidationError? {

    if (req.amount == null || req.amount <= 0) {
        return ValidationError("001", "Amount must be greater than 0")
    }

    if (req.category.isNullOrBlank()) {
        return ValidationError("001", "Category is required")
    }

    if (req.transactionType.isNullOrBlank()) {
        return ValidationError("001", "Transaction type is required")
    }

    if (req.transactionDate.isNullOrBlank()) {
        return ValidationError("001", "Transaction date is required")
    }

    if (req.userId == null || req.userId <= 0) {
        return ValidationError("001", "Invalid user")
    }

    return null
}