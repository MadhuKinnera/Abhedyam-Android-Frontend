package com.madhu.projectkapp1.data.entity

data class User(
    val customers: List<Customer>,
    val email: String,
    val fullName: String,
    val phoneNumber: String?,
    val profileImageUrl: String?,
    val qrImageUrl: String?,
    val products: List<Product>,
    val userId: Int,
    val villages: List<Village>
)