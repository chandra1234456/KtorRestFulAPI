package models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AddCustomerExpenses(
    val amount: Int? = null,
    @SerialName("attachMeantBill")
    val attachMeantBill: String? = null,
    val category: String? = null,
    val notesReference: String? = null,
    val transactionDate: String? = null,
    val transactionType: String? = null,
    val timeStamp: String? = null,
    @SerialName("user_id")
    val userId: Int? = null
)