package com.madhu.projectkapp1.data.entity

data class VillageWiseResponse(
    val activeCustomers: List<Customer>,
    val activeRecords: List<SaleRecord>,
    val collectedAmount: Int,
    val completedRecords: Int,
    val goalStatus: String,
    val pendingAmount: Int,
    val totalActiveCustomers: Int,
    val totalActiveRecords: Int,
    val totalAmountFromVillage: Int,
    val totalCustomersCount: Int,
    val totalProductSellCount: Int,
    val totalRecordsCount: Int,
    val village: Village
)