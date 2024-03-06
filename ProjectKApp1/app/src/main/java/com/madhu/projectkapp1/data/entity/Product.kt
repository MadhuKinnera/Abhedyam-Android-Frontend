package com.madhu.projectkapp1.data.entity

data class Product(
    val buyedPrice: Int,
    val description: String?,
    var imageUrl: String?=null,
    val productId: Int?=null,
    val productName: String,
    val sellingPrice: Int
)