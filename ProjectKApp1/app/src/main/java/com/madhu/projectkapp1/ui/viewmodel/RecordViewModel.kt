package com.madhu.projectkapp1.ui.viewmodel

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.madhu.projectkapp1.data.entity.RecordDto
import com.madhu.projectkapp1.data.entity.RecordResponseModel
import com.madhu.projectkapp1.data.entity.SaleRecord
import com.madhu.projectkapp1.data.enums.SearchWidgetState
import com.madhu.projectkapp1.ui.repository.RecordRepository
import com.madhu.projectkapp1.utility.ResourceState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecordViewModel @Inject constructor(
    private val recordRepository: RecordRepository
) : ViewModel() {



    init {
        Log.d("RecordViewModel", "Record View Model Initiated ")
    }


    private val _records: MutableStateFlow<ResourceState<List<RecordResponseModel>>> =
        MutableStateFlow(ResourceState.Loading())

    val records: StateFlow<ResourceState<List<RecordResponseModel>>> = _records


    private val _record: MutableStateFlow<ResourceState<RecordResponseModel>> =
        MutableStateFlow(ResourceState.Loading())


    val record: StateFlow<ResourceState<RecordResponseModel>> = _record

    private val _addedRecord: MutableStateFlow<ResourceState<SaleRecord>> =
        MutableStateFlow(ResourceState.Loading())


    val addedRecord: StateFlow<ResourceState<SaleRecord>> = _addedRecord


    private val _textState: MutableState<String> = mutableStateOf("")

    val textState: State<String> = _textState

    private val _searchWidgetState: MutableState<SearchWidgetState> =
        mutableStateOf(SearchWidgetState.CLOSED)

    val searchWidgetState: State<SearchWidgetState> = _searchWidgetState


    private val _searchedRecords: MutableStateFlow<ResourceState<List<RecordResponseModel>>> =
        MutableStateFlow(ResourceState.Loading())


    val searchedRecords: StateFlow<ResourceState<List<RecordResponseModel>>> =
        _searchedRecords


    fun getRecords() {
        viewModelScope.launch(Dispatchers.IO) {
            recordRepository.getRecords()
                .collectLatest { records ->
                    _records.value = records
                }
        }
    }

    fun addRecord(recordDto: RecordDto) {
        viewModelScope.launch(Dispatchers.IO) {
            recordRepository.addRecord(recordDto)
                .collectLatest { recordResponse ->
                    _addedRecord.value = recordResponse
                }
        }
    }


    fun getRecordById(recordId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            recordRepository.getRecordById(recordId).collectLatest { record ->
                _record.value = record
            }
        }
    }


    fun updateText(newText: String) {
        _textState.value = newText
    }

    fun updateSearchWidgetState(newSearchWidgetState: SearchWidgetState) {
        _searchWidgetState.value = newSearchWidgetState
    }


    fun searchRecordByName(recordId: Int, customerName: String, productName: String) {
        viewModelScope.launch(Dispatchers.IO) {
            recordRepository.searchRecordsByName(recordId, customerName, productName)
                .collectLatest { records ->
                    _searchedRecords.value = records
                }
        }
    }


}