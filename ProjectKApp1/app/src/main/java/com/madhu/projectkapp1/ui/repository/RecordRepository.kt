package com.madhu.projectkapp1.ui.repository

import android.util.Log
import com.google.gson.Gson
import com.madhu.projectkapp1.data.datasource.RecordDataSource
import com.madhu.projectkapp1.data.entity.ErrorDetails
import com.madhu.projectkapp1.data.entity.RecordDto
import com.madhu.projectkapp1.data.entity.RecordResponseModel
import com.madhu.projectkapp1.data.entity.SaleRecord
import com.madhu.projectkapp1.utility.ResourceState
import com.madhu.projectkapp1.utility.TokenManager
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class RecordRepository @Inject constructor(
    private val recordDataSource: RecordDataSource,
    private val tokenManager: TokenManager
) {


    private val tag = "RecordRepository"

    suspend fun getRecords(): Flow<ResourceState<List<RecordResponseModel>>> {

        return flow {
            Log.d(tag, "Loading Records ")
            emit(ResourceState.Loading())

            val response = recordDataSource.getRecords(tokenManager.authToken!!)

            if (response.isSuccessful && response.body() != null) {
                Log.d(tag, "Success ${response.body()!!.data}")
                emit(ResourceState.Success(response.body()!!.data))
            } else {
                val errorBody = response.errorBody()!!.string()
                Log.d(tag, "Error $errorBody")
                val gson = Gson()
                val errorResponse = gson.fromJson(errorBody, ErrorDetails::class.java)
                Log.d(tag, "Error Response $errorResponse")
                var errorText = "UnAuthorized Request"
                if (errorResponse != null) {
                    errorText = errorResponse.message
                }
                emit(ResourceState.Error(errorText))
            }
        }.catch { e ->
            Log.d(tag, "Inside Catch Block")

            // Check if the exception is an instance of HttpException
            if (e is retrofit2.HttpException) {
                // Get the response from the exception
                val response = e.response()

                if (response != null) {
                    // Extract the error details from the response
                    val errorCode = response.code()
                    val errorMessage = response.errorBody()?.string() ?: "Unknown error"

                    val gson = Gson()
                    val errorResponse = gson.fromJson(errorMessage, ErrorDetails::class.java)
                    var errorText = "Non-Http Error"
                    if (errorResponse != null) {
                        errorText = errorResponse.message
                    }
                    Log.e(tag, "HTTP $errorCode - $errorText", e)
                    emit(ResourceState.Error("HTTP $errorCode - $errorText"))
                } else {
                    Log.e(tag, "HTTP exception with null response", e)
                    emit(ResourceState.Error("Unknown HTTP error"))
                }
            } else {
                // Handle other types of exceptions
                Log.e(tag, "Non-HTTP exception: ${e.message}", e)
                emit(ResourceState.Error(e.message ?: "Some Exception is There...!"))
            }

        }

    }

    suspend fun getRecordById(recordId: Int): Flow<ResourceState<RecordResponseModel>> {

        return flow {
            Log.d(tag, "Loading Record Data with record Id $recordId")
            emit(ResourceState.Loading())

            val response = recordDataSource.getRecordById(tokenManager.authToken!!,recordId)

            if (response.isSuccessful && response.body() != null) {
                Log.d(tag, "Success ${response.body()!!.data}")
                emit(ResourceState.Success(response.body()!!.data))
            } else {
                Log.d(tag, "Error ${response.errorBody()!!.string()}")
                emit(ResourceState.Error("Error Fetching data ${response.errorBody()?.string()}"))
            }
        }.catch { e ->
            emit(ResourceState.Error(e.message ?: "Some Exception is There...!"))
        }

    }

    suspend fun addRecord(recordDto: RecordDto): Flow<ResourceState<SaleRecord>> {

        return flow {
            Log.d(tag, "Loading Records ")
            emit(ResourceState.Loading())

            val response = recordDataSource.addRecord(tokenManager.authToken!!,recordDto)

            if (response.isSuccessful && response.body() != null) {
                Log.d(tag, "Success ${response.body()!!.data}")
                emit(ResourceState.Success(response.body()!!.data))
            } else {
                val errorBody = response.errorBody()!!.string()
                Log.d(tag, "Error $errorBody")
                val gson = Gson()
                val errorResponse = gson.fromJson(errorBody, ErrorDetails::class.java)
                Log.d(tag, "Error Response $errorResponse")
                val errorText = errorResponse.message
                emit(ResourceState.Error(errorText))
            }
        }.catch { e ->
            Log.d(tag, "Inside Catch Block")

            // Check if the exception is an instance of HttpException
            if (e is retrofit2.HttpException) {
                // Get the response from the exception
                val response = e.response()

                if (response != null) {
                    // Extract the error details from the response
                    val errorCode = response.code()
                    val errorMessage = response.errorBody()?.string() ?: "Unknown error"

                    val gson = Gson()
                    val errorResponse = gson.fromJson(errorMessage, ErrorDetails::class.java)

                    Log.e(tag, "HTTP $errorCode - ${errorResponse.message}", e)
                    emit(ResourceState.Error("HTTP $errorCode - ${errorResponse.message}"))
                } else {
                    Log.e(tag, "HTTP exception with null response", e)
                    emit(ResourceState.Error("Unknown HTTP error"))
                }
            } else {
                // Handle other types of exceptions
                Log.e(tag, "Non-HTTP exception: ${e.message}", e)
                emit(ResourceState.Error(e.message ?: "Some Exception is There...!"))
            }

        }

    }


    suspend fun searchRecordsByName(
        recordId: Int,
        customerName: String,
        productName: String
    ): Flow<ResourceState<List<RecordResponseModel>>> {

        return flow {
            Log.d(tag, "Loading Records ")
            emit(ResourceState.Loading())

            val response = recordDataSource.searchRecordsByName(tokenManager.authToken!!,recordId, customerName, productName)

            if (response.isSuccessful && response.body() != null) {
                Log.d(tag, "Success ${response.body()!!.data}")
                emit(ResourceState.Success(response.body()!!.data))
            } else {
                val errorBody = response.errorBody()!!.string()
                Log.d(tag, "Error $errorBody")
                val gson = Gson()
                val errorResponse = gson.fromJson(errorBody, ErrorDetails::class.java)
                Log.d(tag, "Error Response $errorResponse")
                var errorText = "UnAuthorized Request"
                if (errorResponse != null) {
                    errorText = errorResponse.message
                }
                emit(ResourceState.Error(errorText))
            }
        }.catch { e ->
            Log.d(tag, "Inside Catch Block")

            // Check if the exception is an instance of HttpException
            if (e is retrofit2.HttpException) {
                // Get the response from the exception
                val response = e.response()

                if (response != null) {
                    // Extract the error details from the response
                    val errorCode = response.code()
                    val errorMessage = response.errorBody()?.string() ?: "Unknown error"

                    val gson = Gson()
                    val errorResponse = gson.fromJson(errorMessage, ErrorDetails::class.java)
                    var errorText = "Non-Http Error"
                    if (errorResponse != null) {
                        errorText = errorResponse.message
                    }
                    Log.e(tag, "HTTP $errorCode - $errorText", e)
                    emit(ResourceState.Error("HTTP $errorCode - $errorText"))
                } else {
                    Log.e(tag, "HTTP exception with null response", e)
                    emit(ResourceState.Error("Unknown HTTP error"))
                }
            } else {
                // Handle other types of exceptions
                Log.e(tag, "Non-HTTP exception: ${e.message}", e)
                emit(ResourceState.Error(e.message ?: "Some Exception is There...!"))
            }

        }

    }


}