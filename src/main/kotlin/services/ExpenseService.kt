package services

import database.ExpenseDao
import models.AddCustomerExpenses

class ExpenseService(private val expenseDao: ExpenseDao) {

    fun expensesInsert(req: AddCustomerExpenses): String {
        expenseDao.insertUser(
            req.amount ?: 0,
            req.attachMeantBill ?: "",
            req.notesReference ?: "",
            req.transactionDate ?: "",
            req.category ?: "",
            req.transactionType ?: "",
            req.timeStamp ?: "",
            req.userId?:0
        )
        return "Data Saved Successfully"
    }

    fun getExpensesByUser(userid :Int): List<AddCustomerExpenses> {
        return expenseDao.getAllExpenses(userid)
    }
}