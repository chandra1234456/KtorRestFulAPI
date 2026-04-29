package database

import org.jetbrains.exposed.sql.Table

object SignUpUsers : Table() {
    val id = integer("id").autoIncrement()
    val username = varchar("username", 50)
    val email = varchar("email", 100).uniqueIndex()
    val passwordHash = text("password_hash")
    val isUserLoggedIn = bool("isuserloggedin")

    override val primaryKey = PrimaryKey(id)
}
