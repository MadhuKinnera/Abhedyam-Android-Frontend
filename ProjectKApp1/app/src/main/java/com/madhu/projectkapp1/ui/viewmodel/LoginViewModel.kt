package com.madhu.projectkapp1.ui.viewmodel

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.madhu.projectkapp1.data.entity.JwtResponse
import com.madhu.projectkapp1.ui.repository.LoginRepository
import com.madhu.projectkapp1.utility.ResourceState
import com.madhu.projectkapp1.utility.TokenManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginRepository: LoginRepository,
    private val tokenManager: TokenManager

) : ViewModel() {

    init {
viewModelScope.launch {
    delay(2000)
    _isReady.value = true
}

    }


    val tag = "LoginViewModel"

    private val _jwtResponse: MutableStateFlow<ResourceState<JwtResponse>> =
        MutableStateFlow(ResourceState.Loading())

    val jwtResponse: StateFlow<ResourceState<JwtResponse>> = _jwtResponse

    private val _customerCode = mutableStateOf("")

    val customerCode: State<String> = _customerCode

    private val _isReady = MutableStateFlow(false)

    val isReady = _isReady.asStateFlow()


    fun loginUser(email: String, password: String) {
        Log.d(tag, "Inside Login method")
        viewModelScope.launch(Dispatchers.IO) {
            loginRepository.loginUser(email, password).collectLatest { jwtRes ->
                _jwtResponse.value = jwtRes
                Log.d(tag, "The jwt view model res is ${jwtResponse.value}")
            }
        }
    }

    fun updateIsReadyState() {
        Log.d(tag, "Setting value to true")
        _isReady.value = true
    }

    fun saveTokenInStorage(token: String) {
        Log.d(tag, "Passed token to store in shared preferences is $token")
        if (token.contains("ey")) {
            // clearTokenFromStorage()
            tokenManager.authToken = token
        }
        Log.d(tag, "The Stored token is ${tokenManager.authToken}")
    }

    fun clearTokenFromStorage() {
        Log.d(tag, "Clearing token from storage")
        tokenManager.clearAuthToken()
        Log.d(tag, "The Stored Token is ${tokenManager.authToken}")
    }


    fun isAlreadyLoggedIn(): Boolean {
        val storedToken = tokenManager.authToken
        Log.d(tag, "The Token From Storage is $storedToken")

        return (storedToken != null)
    }


    fun saveCustomerCodeInStorage(customerCode: String) {
        Log.d(tag, "Passed customer code to store in shared preferences is $customerCode")
        tokenManager.customerCode = customerCode
        Log.d(tag, "The Stored customer code is ${tokenManager.customerCode}")
    }

    fun clearCustomerCodeFromStorage() {
        Log.d(tag, "Clearing customer code from storage")
        tokenManager.clearCustomerCode()
        Log.d(tag, "The Stored customer code is ${tokenManager.customerCode}")
    }


    fun isCustomerAlreadyLoggedIn(): Boolean {
        val storedToken = tokenManager.customerCode
        Log.d(tag, "The customer code From Storage is $storedToken")

        return (storedToken != null)


    }


    fun getLoggedInCustomerCode() {
        val customerCode = tokenManager.customerCode
        Log.d(tag, "The customer code is $customerCode")
        if (customerCode != null)
            _customerCode.value = customerCode
    }


}