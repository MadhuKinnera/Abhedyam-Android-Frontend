package com.madhu.projectkapp1.ui.repository

import android.util.Log
import com.google.gson.Gson
import com.madhu.projectkapp1.data.datasource.ProductDataSource
import com.madhu.projectkapp1.data.entity.ErrorDetails
import com.madhu.projectkapp1.data.entity.NameAndId
import com.madhu.projectkapp1.data.entity.Product
import com.madhu.projectkapp1.data.entity.ProductResponseModel
import com.madhu.projectkapp1.utility.ResourceState
import com.madhu.projectkapp1.utility.TokenManager
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ProductRepository @Inject constructor(
    private val productDataSource: ProductDataSource,
    private val tokenManager: TokenManager
) {

    private val tag = "ProductRepository"

    suspend fun addProduct(productDto: Product): Flow<ResourceState<Product>> {

        return flow {
            Log.d(tag, "Adding  Product ")
            emit(ResourceState.Loading())

            val response = productDataSource.addProduct(tokenManager.authToken!!, productDto)

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

    suspend fun getProductsNames(): Flow<ResourceState<List<NameAndId>>> {
        return flow {
            Log.d(tag, "Loading Product Names")
            emit(ResourceState.Loading())

            val response = productDataSource.getProductsNames(tokenManager.authToken!!)

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
                Log.d(tag, " Error Message $errorText")
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

    suspend fun getProducts(): Flow<ResourceState<List<ProductResponseModel>>> {

        return flow {
            Log.d(tag, "Loading Products ")
            emit(ResourceState.Loading())

            val response = productDataSource.getProducts(tokenManager.authToken!!)

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
                Log.d(tag, " Error Message $errorText")
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


    suspend fun getProductById(productId: Int): Flow<ResourceState<ProductResponseModel>> {

        return flow {
            Log.d(tag, "Loading Product Data with product Id $productId")
            emit(ResourceState.Loading())

            val response = productDataSource.getProductById(tokenManager.authToken!!, productId)

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
                Log.d(tag, "Error Message $errorText")
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


    suspend fun searchProductsByName(productName: String): Flow<ResourceState<List<ProductResponseModel>>> {

        return flow {
            Log.d(tag, "Loading Products ")
            emit(ResourceState.Loading())

            val response =
                productDataSource.searchProductsByName(tokenManager.authToken!!, productName)

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
                Log.d(tag, " Error Message $errorText")
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

    suspend fun deleteProduct(productId: Int): Flow<ResourceState<Product>> {

        return flow {
            Log.d(tag, "Loding Delete  Product ")
            emit(ResourceState.Loading())

            val response = productDataSource.deleteProduct(tokenManager.authToken!!, productId)

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


}