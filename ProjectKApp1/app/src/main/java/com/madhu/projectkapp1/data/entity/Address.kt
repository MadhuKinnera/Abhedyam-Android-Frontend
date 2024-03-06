package com.madhu.projectkapp1.data.entity

data class Address(
    val addressId: Int,
    val description: String?,
    val landmark: String?,
    val street: String?,
    val village: Village
)