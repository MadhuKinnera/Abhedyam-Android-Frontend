package com.madhu.projectkapp1.data.datasource

import com.madhu.projectkapp1.data.entity.GeneralResponse
import com.madhu.projectkapp1.data.entity.RecordDto
import com.madhu.projectkapp1.data.entity.RecordResponseModel
import com.madhu.projectkapp1.data.entity.SaleRecord
import retrofit2.Response

interface RecordDataSource {

    suspend fun getRecords(token:String): Response<GeneralResponse<List<RecordResponseModel>>>

    suspend fun getRecordById(token: String,recordId: Int): Response<GeneralResponse<RecordResponseModel>>

    suspend fun addRecord(token: String,recordDto: RecordDto): Response<GeneralResponse<SaleRecord>>

    suspend fun searchRecordsByName(
        token: String,
        recordId: Int,
        customerName: String,
        productName: String
    ): Response<GeneralResponse<List<RecordResponseModel>>>
}