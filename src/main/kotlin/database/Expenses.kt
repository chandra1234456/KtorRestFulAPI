package database

import org.jetbrains.exposed.sql.Table

object Expenses : Table() {
    val id = integer("id").autoIncrement()
    val transactionType = varchar("transactiontype", 50)
    val amount = integer("amount")
    val attachMeantBill = text("attachmeantbill")
    val notesReference = text("notesreference")
    val transactionDate = text("transactiondate")
    val timeStamp = text("timestamp")
    val category = text("category")
    val userId = integer("user_id")

    override val primaryKey = PrimaryKey(id)
}