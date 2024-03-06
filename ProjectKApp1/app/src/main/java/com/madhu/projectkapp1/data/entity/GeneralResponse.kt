package com.madhu.projectkapp1.data.entity

data class GeneralResponse<T>(
    val data: T,
    val message: String,
    val status: Int
)