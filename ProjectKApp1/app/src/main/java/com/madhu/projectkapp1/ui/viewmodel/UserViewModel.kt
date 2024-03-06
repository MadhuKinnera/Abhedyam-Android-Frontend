package com.madhu.projectkapp1.ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.madhu.projectkapp1.data.entity.User
import com.madhu.projectkapp1.data.entity.UserDto
import com.madhu.projectkapp1.ui.repository.UserRepository
import com.madhu.projectkapp1.utility.ResourceState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {


    val tag = "UserViewModel"

    init {
        Log.d(tag, "User View Model Initiated ")
    }

    private val _createdUser: MutableStateFlow<ResourceState<User>> =
        MutableStateFlow(ResourceState.Loading())

    val createdUser: StateFlow<ResourceState<User>> =
        _createdUser


    fun addUser(userDto: UserDto) {
        viewModelScope.launch(Dispatchers.IO) {
            userRepository.addUser(userDto)
                .collectLatest { user ->
                    _createdUser.value = user
                }
        }
    }


}