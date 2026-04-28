package utils


fun hashPassword(password: String): String {
    return  password //BCrypt.hashpw(password, BCrypt.gensalt())
}