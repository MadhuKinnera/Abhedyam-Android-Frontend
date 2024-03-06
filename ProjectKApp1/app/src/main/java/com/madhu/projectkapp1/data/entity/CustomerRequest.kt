package com.madhu.projectkapp1.data.entity

data class CustomerRequest(
    val crId: Int,
    val customer: Customer,
    val message: String,
    val referenceImages: List<String>,
    val timestamp: String
)