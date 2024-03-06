package com.madhu.projectkapp1.data.entity

data class CustomerResponseModel(
    val customer: Customer,
    val customerFlag: String,
    val description: String?,
    val products: List<Product>,
    val recordStatus: Boolean,
    val saleRecords: List<SaleRecord>,
    val totalAmount: Int,
    val totalPaidAmount: Int,
    val totalProducts: Int,
    val totalRemaininAmount: Int,
    val transactions: List<Transaction>
)