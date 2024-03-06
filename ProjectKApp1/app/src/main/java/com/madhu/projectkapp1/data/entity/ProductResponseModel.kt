package com.madhu.projectkapp1.data.entity


data class ProductResponseModel(
    val collectedAmount: Int,
    val pendingAmount: Int,
    val product: Product,
    val productSellCount: Int,
    val totalSelledAmount: Int,
    val villageWiseCount: List<VillageWiseCount>
)