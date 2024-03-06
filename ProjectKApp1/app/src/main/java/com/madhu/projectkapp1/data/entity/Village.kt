package com.madhu.projectkapp1.data.entity

data class Village(
    val amountGoal: Int?,
    val district: String?,
    val mandal: String,
    val pincode: Int?,
    val productGoal: Int?,
    val state: String?,
    val villageId: Int?=null,
    val villageName: String,
    var imageUrl : String?=null
)
