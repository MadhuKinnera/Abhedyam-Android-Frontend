package com.madhu.projectkapp1.data.datasource

import android.util.Log
import com.madhu.projectkapp1.data.api.ApiService
import com.madhu.projectkapp1.data.entity.GeneralResponse
import com.madhu.projectkapp1.data.entity.Transaction
import com.madhu.projectkapp1.data.entity.TransactionDto
import retrofit2.Response
import javax.inject.Inject

class TransactionDataSourceImpl @Inject constructor(
    private val apiService: ApiService,
    private val login: Login
) : TransactionDataSource {

    val tag = "TransactionDataSourceImpl"

    init {
        Log.d(tag, "Inside TransactionDataSourceImpl class")
    }


    override suspend fun addTransaction(
        token: String,
        transactionDto: TransactionDto
    ): Response<GeneralResponse<Transaction>> {
        Log.d(tag, "Inside Add Transaction ")
        return apiService.addTransaction(token, transactionDto)
    }
}