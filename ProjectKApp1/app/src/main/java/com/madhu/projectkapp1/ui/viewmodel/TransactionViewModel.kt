package com.madhu.projectkapp1.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.madhu.projectkapp1.data.entity.Transaction
import com.madhu.projectkapp1.data.entity.TransactionDto
import com.madhu.projectkapp1.ui.repository.TransactionRepository
import com.madhu.projectkapp1.utility.ResourceState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TransactionViewModel @Inject constructor(
    private val transactionRepository: TransactionRepository
) : ViewModel() {

    val tag = "TransactionViewModel"

    private val _addedTransaction: MutableStateFlow<ResourceState<Transaction>> =
        MutableStateFlow(ResourceState.Loading())

    val addedTransaction: StateFlow<ResourceState<Transaction>> = _addedTransaction


    fun addTransaction(transactionDto: TransactionDto) {
        viewModelScope.launch(Dispatchers.IO) {
            transactionRepository.addTransaction(transactionDto)
                .collectLatest { transaction ->
                    _addedTransaction.value = transaction
                }
        }
    }


}