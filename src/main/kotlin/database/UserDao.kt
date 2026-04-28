package database

import models.User
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction

class UserDao {

    fun findByEmail(email: String): Boolean {
        return transaction {
            SignUpUsers
                .selectAll()
                .where { SignUpUsers.email eq email }
                .count() > 0
        }
    }

    fun insertUser(username: String, email: String, password: String) {
        transaction {
            SignUpUsers.insert {
                it[SignUpUsers.username] = username
                it[SignUpUsers.email] = email
                it[SignUpUsers.passwordHash] = password
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
                        passwordHash = it[SignUpUsers.passwordHash]
                    )
                }
                .singleOrNull()
        }
    }
}