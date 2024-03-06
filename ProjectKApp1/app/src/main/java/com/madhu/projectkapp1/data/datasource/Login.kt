package com.madhu.projectkapp1.data.datasource

import android.util.Log
import com.madhu.projectkapp1.data.api.ApiService
import com.madhu.projectkapp1.data.entity.JwtResponse
import retrofit2.Response
import javax.inject.Inject


class Login @Inject constructor(
    private val apiService: ApiService
) {

    private val tag = "Login"

    init {
        Log.d(tag, "Inside Login init ")
    }


    suspend fun loginUser(
        email: String,
        password: String
    ): Response<JwtResponse> {


        Log.d(tag, "Inside login Data Source")

        return apiService.login(email, password)

    }


}
