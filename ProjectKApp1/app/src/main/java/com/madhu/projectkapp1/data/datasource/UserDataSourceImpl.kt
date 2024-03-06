package com.madhu.projectkapp1.data.datasource

import android.util.Log
import com.madhu.projectkapp1.data.api.ApiService
import com.madhu.projectkapp1.data.entity.GeneralResponse
import com.madhu.projectkapp1.data.entity.User
import com.madhu.projectkapp1.data.entity.UserDto
import retrofit2.Response
import javax.inject.Inject

class UserDataSourceImpl @Inject constructor(
    private val apiService: ApiService
) : UserDataSource {

    val tag = "UserDataSourceImpl"

    override suspend fun adduser(userDto: UserDto): Response<GeneralResponse<User>> {
        Log.d(tag, "Adding user $userDto")
        return apiService.adduser(userDto)
    }
}