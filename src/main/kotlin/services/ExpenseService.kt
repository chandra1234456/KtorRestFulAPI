package services

import database.ExpenseDao
import kotlinx.serialization.json.Json
import models.AddCustomerExpenses
import models.CommonResponseModel
import utils.AESUtil
import utils.Constants.SECRET_KEY
import utils.ResponseCodes
import utils.validateExpense

class ExpenseService(private val expenseDao: ExpenseDao) {

    fun expensesInsert(req: AddCustomerExpenses): CommonResponseModel<String> {
        val error = validateExpense(req)
        if (error != null) {
            return CommonResponseModel(
                responseCode = error.code,
                responseMessage = error.message,
                responseData = null
            )
        }
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
        return CommonResponseModel(
            ResponseCodes.SUCCESS,
            "Success",
            "Data Saved Successfully"
        )
    }

    fun getExpensesByUser(userid: Int): CommonResponseModel<String> {
        val expenses = expenseDao.getAllExpenses(userid)
        val json = Json.encodeToString(expenses)
        val encryptedJson = AESUtil.encrypt(json, SECRET_KEY)
        return CommonResponseModel(
            ResponseCodes.SUCCESS,
            "Success",
            encryptedJson
        )
    }
}