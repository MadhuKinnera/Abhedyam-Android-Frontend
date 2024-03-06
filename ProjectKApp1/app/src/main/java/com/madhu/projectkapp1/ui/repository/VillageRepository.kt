package com.madhu.projectkapp1.ui.repository

import android.util.Log
import com.google.gson.Gson
import com.madhu.projectkapp1.data.datasource.VillageDataSource
import com.madhu.projectkapp1.data.entity.ErrorDetails
import com.madhu.projectkapp1.data.entity.NameAndId
import com.madhu.projectkapp1.data.entity.Village
import com.madhu.projectkapp1.data.entity.VillageWiseResponse
import com.madhu.projectkapp1.utility.ResourceState
import com.madhu.projectkapp1.utility.TokenManager
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class VillageRepository @Inject constructor(
    private val villageDataSource: VillageDataSource,
    private val tokenManager: TokenManager
) {

    private val tag = "VillageRepository"


    suspend fun addVillage(village: Village): Flow<ResourceState<Village>> {

        return flow {
            Log.d(tag, "Loading Add Village  ")
            emit(ResourceState.Loading())

            val response = villageDataSource.addVillage(tokenManager.authToken!!, village)

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


    suspend fun getVillagesData(): Flow<ResourceState<List<VillageWiseResponse>>> {

        return flow {
            Log.d(tag, "Loading VillageWise Data ")
            emit(ResourceState.Loading())

            val response = villageDataSource.getVillageWiseData(tokenManager.authToken!!)

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


    suspend fun getVillageWiseDataById(villageId: Int): Flow<ResourceState<VillageWiseResponse>> {

        return flow {
            Log.d(tag, "Loading VillageWise Data with village Id $villageId")
            emit(ResourceState.Loading())

            val response =
                villageDataSource.getVillageWiseDataByVillageId(tokenManager.authToken!!, villageId)

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


    suspend fun searchVillagesByName(villageName: String): Flow<ResourceState<List<VillageWiseResponse>>> {

        return flow {
            Log.d(tag, "Loading VillageWise Data ")
            emit(ResourceState.Loading())

            val response =
                villageDataSource.searchVillagesByName(tokenManager.authToken!!, villageName)

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


    suspend fun deleteVillage(villageId: Int): Flow<ResourceState<Village>> {

        return flow {
            Log.d(tag, "Loading Delete Village  ")
            emit(ResourceState.Loading())

            val response = villageDataSource.deleteVillage(tokenManager.authToken!!, villageId)

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

    suspend fun getVillagesName(): Flow<ResourceState<List<NameAndId>>> {
        return flow {
            Log.d(tag, "Loading Village Names")
            emit(ResourceState.Loading())

            Log.d(tag,"Token is ${tokenManager.authToken}")
            val response = villageDataSource.getVillagesNames(tokenManager.authToken!!)

            Log.d(tag,"The response is ${response.isSuccessful} ${response.body()}")

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


}