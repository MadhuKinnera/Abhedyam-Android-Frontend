package com.madhu.projectkapp1.data.datasource

import com.madhu.projectkapp1.data.entity.GeneralResponse
import com.madhu.projectkapp1.data.entity.Transaction
import com.madhu.projectkapp1.data.entity.TransactionDto
import retrofit2.Response

interface TransactionDataSource {

    suspend fun addTransaction(token:String,transactionDto: TransactionDto): Response<GeneralResponse<Transaction>>
}