package com.madhu.projectkapp1.ui.repository

import android.util.Log
import com.google.gson.Gson
import com.madhu.projectkapp1.data.datasource.Login
import com.madhu.projectkapp1.data.entity.ErrorDetails
import com.madhu.projectkapp1.data.entity.JwtResponse
import com.madhu.projectkapp1.utility.ResourceState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class LoginRepository @Inject constructor(
    private val loginDataSource: Login
) {

    val tag = "LoginRepository"

    suspend fun loginUser(email: String, password: String): Flow<ResourceState<JwtResponse>> {

        return flow {
            Log.d(tag, "Loading Login ")
            emit(ResourceState.Loading())

            val response =
                loginDataSource.loginUser(email, password)
            if (response.isSuccessful && response.body() != null) {
                Log.d(tag, "Success ${response.body()!!}")
                emit(ResourceState.Success(response.body()!!))
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


}

