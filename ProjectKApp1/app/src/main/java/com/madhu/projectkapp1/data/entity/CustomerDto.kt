package com.madhu.projectkapp1.data.entity


data class CustomerDto(
    var customerName: String = "",
    var profileImage: String?=null,
    var age: Int? = null,
    var profession: String? = "",
    var mobileNo: String? = "",
    var email: String? = null,
    var description: String? = "",
    var addressDto: AddressDto? = AddressDto(),
    var keywords: List<String>? = null
)




