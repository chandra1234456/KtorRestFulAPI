package database

import models.AddCustomerExpenses
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction

class ExpenseDao {
    fun insertUser(
        amount: Int,
        attachMeantBill: String,
        notesReference: String,
        transactionDate: String,
        category: String,
        transactionType: String,
        timeStamp: String,
        userId: Int,
    ) {
        transaction {
            Expenses.insert {
                it[Expenses.amount] = amount
                it[Expenses.attachMeantBill] = attachMeantBill
                it[Expenses.notesReference] = notesReference
                it[Expenses.transactionDate] = transactionDate
                it[Expenses.transactionType] = transactionType
                it[Expenses.timeStamp] = timeStamp
                it[Expenses.category] = category
                it[Expenses.userId] = userId
            }
        }
    }
    fun getAllExpenses(userId: Int): List<AddCustomerExpenses> {
        return transaction {
            Expenses.selectAll()
                .where { Expenses.userId eq userId }
                .map {
                AddCustomerExpenses(
                    amount = it[Expenses.amount],
                    attachMeantBill = it[Expenses.attachMeantBill],
                    category = it[Expenses.category],
                    notesReference = it[Expenses.notesReference],
                    transactionDate = it[Expenses.transactionDate],
                    transactionType = it[Expenses.transactionType],
                    timeStamp = it[Expenses.timeStamp],
                    userId = it[Expenses.userId]
                )
            }
        }
    }
}