package com.madhu.projectkapp1.data.entity

data class CustomerPersonalDto(
    val creditorName: String,
    val creditorPhoneNumber: String,
    val creditorQRImageUrl: String,
    val creditorProfileImageUrl:String,
    val customer: Customer,
    val productNames: List<String>,
    val recordStatus: Boolean,
    val saleRecords: List<SaleRecord>,
    val totalAmount: Int,
    val totalPaidAmount: Int,
    val totalProducts: Int,
    val totalRecords: Int,
    val totalRemaininAmount: Int,
    val totalTransactions: Int,
    val transactions: List<Transaction>
)