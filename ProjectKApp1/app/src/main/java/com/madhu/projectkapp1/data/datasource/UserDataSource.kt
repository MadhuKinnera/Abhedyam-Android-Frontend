package com.madhu.projectkapp1.data.datasource

import com.madhu.projectkapp1.data.entity.GeneralResponse
import com.madhu.projectkapp1.data.entity.User
import com.madhu.projectkapp1.data.entity.UserDto
import retrofit2.Response

interface UserDataSource {

    suspend fun adduser(userDto: UserDto): Response<GeneralResponse<User>>
}