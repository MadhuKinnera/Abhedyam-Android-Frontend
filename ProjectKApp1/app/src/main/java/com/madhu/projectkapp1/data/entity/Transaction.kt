package com.madhu.projectkapp1.data.entity

data class Transaction(
    val amount: Int,
    val description: String?,
    val referenceImage: String?,
    val  modeOfPayment: String?,
    val timestamp: String,
    val transactionId: Int
)