package com.madhu.projectkapp1.data.entity

data class SaleRecord(
    val description: String?,
    val dueAmount: Int,
    val endDate: String?,
    val occasion: String,
    val product: Product,
    val quantity: Int,
    val recordId: Int,
    val remainders: List<Remainder>,
    val startDate: String,
    val timestamp: String,
    val totalAmount: Int,
    val transactions: List<Transaction>
)
