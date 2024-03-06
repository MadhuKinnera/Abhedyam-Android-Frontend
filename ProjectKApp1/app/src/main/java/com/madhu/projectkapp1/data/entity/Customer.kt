package com.madhu.projectkapp1.data.entity

data class Customer(
    val address: Address?,
    val age: Int?,
    val customerId: Int,
    val customerName: String,
    val description: String?,
    val email: String?,
    val customerCode:String?,
    val flag: String,
    val keywords: List<String>?,
    val mobileNo: String,
    val profession: String?,
    var profileImageUrl: String?
)