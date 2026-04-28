import io.ktor.server.application.Application
import org.jetbrains.exposed.sql.Database

fun Application.configureDatabases() {

    Database.connect(
        url = "jdbc:postgresql://localhost:5432/myappdb",
        driver = "org.postgresql.Driver",
        user = "postgres",
        password = "1234"
    )
}