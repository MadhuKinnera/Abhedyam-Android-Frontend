package com.madhu.projectkapp1.ui.viewmodel

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.madhu.projectkapp1.data.entity.JwtResponse
import com.madhu.projectkapp1.ui.repository.UploadImageRepository
import com.madhu.projectkapp1.utility.CoreUtility
import com.madhu.projectkapp1.utility.ResourceState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UploadImageViewModel @Inject constructor(
    private val uploadImageRepository: UploadImageRepository
) : ViewModel() {

    val tag = "UploadImageViewModel"

    private val _uploadResponse: MutableStateFlow<ResourceState<JwtResponse>> =
        MutableStateFlow(ResourceState.Loading())

    val uploadResponse: StateFlow<ResourceState<JwtResponse>> = _uploadResponse


    fun uploadImageToCloudinary(uri: Uri, context: Context) {
        viewModelScope.launch(Dispatchers.IO) {
            Log.d(tag, "Inside Upload Cloudinary method")
            viewModelScope.launch(Dispatchers.IO) {

                Log.d(tag,"Uri is $uri")

                val image = CoreUtility.uploadToCloudinary(context, uri)

                uploadImageRepository.uploadImageToCloudinary(image).collectLatest { jwtRes ->
                    _uploadResponse.value = jwtRes
                    Log.d(tag, "The uploaded image view model res is ${uploadResponse.value}")
                }
            }
        }

    }

}