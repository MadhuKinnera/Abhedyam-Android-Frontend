package com.madhu.projectkapp1.data.datasource

import com.madhu.projectkapp1.data.entity.JwtResponse
import okhttp3.MultipartBody
import retrofit2.Response

interface UploadImageDataSource {

    suspend fun uploadImageToCloudinary(image: MultipartBody.Part): Response<JwtResponse>

}