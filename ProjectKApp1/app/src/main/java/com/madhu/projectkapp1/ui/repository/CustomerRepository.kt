package com.madhu.projectkapp1.ui.repository

import android.util.Log
import com.google.gson.Gson
import com.madhu.projectkapp1.data.datasource.CustomerDataSource
import com.madhu.projectkapp1.data.entity.Customer
import com.madhu.projectkapp1.data.entity.CustomerDto
import com.madhu.projectkapp1.data.entity.CustomerPersonalDto
import com.madhu.projectkapp1.data.entity.CustomerResponseModel
import com.madhu.projectkapp1.data.entity.ErrorDetails
import com.madhu.projectkapp1.data.entity.NameAndId
import com.madhu.projectkapp1.utility.ResourceState
import com.madhu.projectkapp1.utility.TokenManager
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class CustomerRepository @Inject constructor(
    private val customerDataSource: CustomerDataSource,
    private val tokenManager: TokenManager
) {

    private val tag = "CustomerRepository"


    suspend fun addCustomer(customerDto: CustomerDto): Flow<ResourceState<Customer>> {

        return flow {
            Log.d(tag, "Loading Customers")
            emit(ResourceState.Loading())

            val response = customerDataSource.addCustomer(tokenManager.authToken!!, customerDto)

            if (response.isSuccessful && response.body() != null) {
                Log.d(tag, "Success ${response.body()!!.data}")
                emit(ResourceState.Success(response.body()!!.data))
            } else {
                val errorBody = response.errorBody()?.string() ?: "Unknown error"
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

    suspend fun getCustomersNames(): Flow<ResourceState<List<NameAndId>>> {
        return flow {
            Log.d(tag, "Loading Customers Names")
            emit(ResourceState.Loading())

            val response = customerDataSource.getCustomersNames(tokenManager.authToken!!)

            if (response.isSuccessful && response.body() != null) {
                Log.d(tag, "Success ${response.body()!!.data}")
                emit(ResourceState.Success(response.body()!!.data))
            } else {
                val errorBody = response.errorBody()?.string() ?: "Unknown error"
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


    suspend fun getCustomers(): Flow<ResourceState<List<CustomerResponseModel>>> {
        return flow {
            Log.d(tag, "Loading Customers")
            emit(ResourceState.Loading())

            val response = customerDataSource.getCustomers(tokenManager.authToken!!)

            if (response.isSuccessful && response.body() != null) {
                Log.d(tag, "Success ${response.body()!!.data}")
                emit(ResourceState.Success(response.body()!!.data))
            } else {
                val errorBody = response.errorBody()?.string() ?: "Unknown error"
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
                    var errorText = "Invalid Login"
                    if (errorResponse != null) {
                        errorText = errorResponse.message
                    }
                    Log.d(tag, "Error Code $errorCode - Error Message $errorText")
                    emit(ResourceState.Error(errorText))
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

    suspend fun getCustomerById(customerId: Int): Flow<ResourceState<CustomerResponseModel>> {
        return flow {
            Log.d(tag, "Loading Customer Data with customer Id $customerId")
            emit(ResourceState.Loading())

            val response = customerDataSource.getCustomerById(tokenManager.authToken!!, customerId)

            if (response.isSuccessful && response.body() != null) {
                Log.d(tag, "Success ${response.body()!!.data}")
                emit(ResourceState.Success(response.body()!!.data))
            } else {
                val errorBody = response.errorBody()?.string() ?: "Unknown error"
                Log.d(tag, "Error $errorBody")
                emit(ResourceState.Error("Error Fetching data $errorBody"))
            }
        }.catch { e ->
            Log.d(tag, "Inside Catch Block")
            Log.d(tag, "Error message: ${e.message}")
            Log.d(tag, "Stack trace: ${e.printStackTrace()}")
            emit(ResourceState.Error(e.message ?: "Some Exception is There...!"))
        }


    }


    suspend fun searchCustomersByname(customerName: String): Flow<ResourceState<List<CustomerResponseModel>>> {
        return flow {
            Log.d(tag, "Loading Customers")
            emit(ResourceState.Loading())

            val response =
                customerDataSource.searchCustomersByName(tokenManager.authToken!!, customerName)

            if (response.isSuccessful && response.body() != null) {
                Log.d(tag, "Success ${response.body()!!.data}")
                emit(ResourceState.Success(response.body()!!.data))
            } else {
                val errorBody = response.errorBody()?.string() ?: "Unknown error"
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
                    var errorText = "UnAuthorized Request"
                    if (errorResponse != null) {
                        errorText = errorResponse.message
                    }
                    Log.d(tag, "Error Code $errorCode - Error Message $errorText")
                    emit(ResourceState.Error(errorText))
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

    suspend fun getCustomerPersonalDetails(customerCode: String): Flow<ResourceState<CustomerPersonalDto>> {
        return flow {
            Log.d(tag, "Loading Customers")
            emit(ResourceState.Loading())

            val response =
                customerDataSource.getCustomerPersonalDetails(customerCode)

            Log.d(tag,"Response is ${response.isSuccessful} and $response")

            if (response.isSuccessful && response.body() != null) {
                Log.d(tag, "Success ${response.body()!!.data}")
                emit(ResourceState.Success(response.body()!!.data))
            } else {
                val errorBody = response.errorBody()?.string() ?: "Unknown error"
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
                Log.d(tag,"Exception is type Http")
                // Get the response from the exception
                val response = e.response()

                if (response != null) {
                    // Extract the error details from the response
                    val errorCode = response.code()
                    val errorMessage = response.errorBody()?.string() ?: "Unknown error"

                    val gson = Gson()
                    val errorResponse = gson.fromJson(errorMessage, ErrorDetails::class.java)
                    var errorText = "UnAuthorized Request"
                    if (errorResponse != null) {
                        errorText = errorResponse.message
                    }
                    Log.d(tag, "Error Code $errorCode - Error Message $errorText")
                    emit(ResourceState.Error(errorText))
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

    suspend fun deleteCustomer(customerId: Int): Flow<ResourceState<Customer>> {

        return flow {
            Log.d(tag, "Loading Deleting Customer")
            emit(ResourceState.Loading())

            val response = customerDataSource.deleteCustomer(
                token = tokenManager.authToken!!,
                customerId = customerId
            )

            if (response.isSuccessful && response.body() != null) {
                Log.d(tag, "Success ${response.body()!!.data}")
                emit(ResourceState.Success(response.body()!!.data))
            } else {
                val errorBody = response.errorBody()?.string() ?: "Unknown error"
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


}