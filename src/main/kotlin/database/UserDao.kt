package database

import models.User
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.update

class UserDao {

    fun findByEmail(email: String): Boolean {
        return transaction {
            SignUpUsers
                .selectAll()
                .where { SignUpUsers.email eq email }
                .count() > 0
        }
    }
    fun findByUsername(userName: String): Boolean {
        return transaction {
            SignUpUsers
                .selectAll()
                .where { SignUpUsers.username eq userName }
                .count() > 0
        }
    }

    fun insertUser(username: String, email: String, password: String,isLoggedIn : Boolean) {
        transaction {
            SignUpUsers.insert {
                it[SignUpUsers.username] = username
                it[SignUpUsers.email] = email
                it[SignUpUsers.passwordHash] = password
                it[SignUpUsers.isUserLoggedIn] = isLoggedIn
            }
        }
    }

    fun findUserByEmail(email: String): User? {
        return transaction {
            SignUpUsers
                .selectAll()
                .where { SignUpUsers.email eq email }
                .map {
                    User(
                        id = it[SignUpUsers.id],
                        username = it[SignUpUsers.username],
                        email = it[SignUpUsers.email],
                        passwordHash = it[SignUpUsers.passwordHash],
                        isUserLoggedIn = it[SignUpUsers.isUserLoggedIn]
                    )
                }
                .singleOrNull()
        }
    }

    fun updateLoginStatus(userEmail: String, status: Boolean) {
        transaction {
            SignUpUsers.update({ SignUpUsers.email eq userEmail }) {
                it[isUserLoggedIn] = status
            }
        }
    }

}