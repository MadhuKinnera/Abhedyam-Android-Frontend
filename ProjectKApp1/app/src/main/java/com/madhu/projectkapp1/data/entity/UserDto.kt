package com.madhu.projectkapp1.data.entity

data class UserDto(
    val email: String,
    val fullName: String,
    val password: String,
    val phoneNumber: String,
    var qrImageUrl: String?=null,
    var profileImageUrl: String?=null

)