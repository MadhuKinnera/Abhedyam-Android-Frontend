package com.madhu.projectkapp1.data.datasource

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresExtension
import com.madhu.projectkapp1.data.api.ApiService
import com.madhu.projectkapp1.data.entity.JwtResponse
import okhttp3.MultipartBody
import retrofit2.Response
import javax.inject.Inject

class UploadImageDataSourceImpl @Inject constructor(
    val apiService: ApiService
) : UploadImageDataSource {

    val tag = "UploadImageDataSourceImpl"

    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    override suspend fun uploadImageToCloudinary(image: MultipartBody.Part): Response<JwtResponse> {
        Log.d(tag, "Uploading Image To Cloudinary ")
        return apiService.uploadImageToCloudinary(image)

    }
}