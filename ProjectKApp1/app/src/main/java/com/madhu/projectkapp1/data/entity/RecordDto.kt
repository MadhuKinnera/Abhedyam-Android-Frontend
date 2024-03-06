package com.madhu.projectkapp1.data.entity

import com.madhu.projectkapp1.data.enums.Occassion

data class RecordDto(
    val customerId: Int,
    val description: String?=null,
    val occasion: Occassion,
    val productId: Int,
    val quantity: Int?=null,
    val totalAmount: Int?=null
)