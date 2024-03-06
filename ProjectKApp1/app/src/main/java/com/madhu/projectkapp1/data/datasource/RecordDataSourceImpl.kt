package com.madhu.projectkapp1.data.datasource

import android.util.Log
import com.madhu.projectkapp1.data.api.ApiService
import com.madhu.projectkapp1.data.entity.GeneralResponse
import com.madhu.projectkapp1.data.entity.RecordDto
import com.madhu.projectkapp1.data.entity.RecordResponseModel
import com.madhu.projectkapp1.data.entity.SaleRecord
import retrofit2.Response
import javax.inject.Inject

class RecordDataSourceImpl @Inject constructor(
    private val apiService: ApiService,
) : RecordDataSource {

    private val tag = "RecordDataSourceImpl"


    override suspend fun getRecords(token: String): Response<GeneralResponse<List<RecordResponseModel>>> {
        Log.d(tag, "Inside get Records ")
        return apiService.getRecords(token)
    }

    override suspend fun getRecordById(
        token: String,
        recordId: Int
    ): Response<GeneralResponse<RecordResponseModel>> {
        Log.d(tag, "Inside get Record By recordId $recordId ")
        return apiService.getRecordById(token, recordId)
    }

    override suspend fun addRecord(
        token: String,
        recordDto: RecordDto
    ): Response<GeneralResponse<SaleRecord>> {
        Log.d(tag, "Inside Add Record ")
        return apiService.addRecord(token, recordDto)
    }

    override suspend fun searchRecordsByName(
        token: String,
        recordId: Int,
        customerName: String,
        productName: String
    ): Response<GeneralResponse<List<RecordResponseModel>>> {
        Log.d(tag, "Inside Search Records with $recordId $customerName $productName")
        return apiService.searchRecordsByName(
            token,
            recordId = recordId,
            customerName = customerName,
            productName = productName
        )
    }
}