package com.madhu.projectkapp1.data.entity

data class TransactionDto(
    val amount: Int,
    val description: String?=null,
    val modeOfPayment:String?=null,
    val recordId: Int
)