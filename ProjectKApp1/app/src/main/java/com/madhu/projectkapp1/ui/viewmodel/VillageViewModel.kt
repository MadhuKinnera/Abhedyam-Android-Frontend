package com.madhu.projectkapp1.ui.viewmodel

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.madhu.projectkapp1.data.entity.NameAndId
import com.madhu.projectkapp1.data.entity.Village
import com.madhu.projectkapp1.data.entity.VillageWiseResponse
import com.madhu.projectkapp1.data.enums.SearchWidgetState
import com.madhu.projectkapp1.ui.repository.UploadImageRepository
import com.madhu.projectkapp1.ui.repository.VillageRepository
import com.madhu.projectkapp1.utility.ResourceState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VillageViewModel @Inject constructor(
    private val villageRepository: VillageRepository,
    private val uploadImageRepository: UploadImageRepository
) : ViewModel() {


    val tag = "VillageViewModel"


    init {
        Log.d("VillageViewModel", "Village View Model Initiated ")
    }

    private val _addedVillage: MutableStateFlow<ResourceState<Village>> =
        MutableStateFlow(ResourceState.Loading())

    val addedVillage: StateFlow<ResourceState<Village>> = _addedVillage


    private val _villagesData: MutableStateFlow<ResourceState<List<VillageWiseResponse>>> =
        MutableStateFlow(ResourceState.Loading())

    val villagesData: StateFlow<ResourceState<List<VillageWiseResponse>>> = _villagesData


    private val _villageData: MutableStateFlow<ResourceState<VillageWiseResponse>> =
        MutableStateFlow(ResourceState.Loading())


    val villageData: StateFlow<ResourceState<VillageWiseResponse>> = _villageData


    private val _textState: MutableState<String> = mutableStateOf("")

    val textState: State<String> = _textState

    private val _searchWidgetState: MutableState<SearchWidgetState> =
        mutableStateOf(SearchWidgetState.CLOSED)

    val searchWidgetState: State<SearchWidgetState> = _searchWidgetState


    private val _searchedVillages: MutableStateFlow<ResourceState<List<VillageWiseResponse>>> =
        MutableStateFlow(ResourceState.Loading())


    val searchedVillages: StateFlow<ResourceState<List<VillageWiseResponse>>> =
        _searchedVillages

    private val _deletedVillage: MutableStateFlow<ResourceState<Village>> =
        MutableStateFlow(ResourceState.Loading())

    val deletedVillage: StateFlow<ResourceState<Village>> = _deletedVillage


    private val _villagesNames: MutableStateFlow<ResourceState<List<NameAndId>>> =
        MutableStateFlow(ResourceState.Loading())

    val villagesNames: StateFlow<ResourceState<List<NameAndId>>> = _villagesNames


    fun addVillage(village: Village) {
        viewModelScope.launch(Dispatchers.IO) {

            villageRepository.addVillage(village)
                .collectLatest { villageResponse ->
                    _addedVillage.value = villageResponse
                }
        }
    }

    fun getVillagesData() {
        viewModelScope.launch(Dispatchers.IO) {
            villageRepository.getVillagesData()
                .collectLatest { villagesData ->
                    _villagesData.value = villagesData
                }
        }
    }


    fun getVillageDataById(villageId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            villageRepository.getVillageWiseDataById(villageId).collectLatest { villageData ->
                _villageData.value = villageData
            }
        }
    }


    fun updateText(newText: String) {
        _textState.value = newText
    }

    fun updateSearchWidgetState(newSearchWidgetState: SearchWidgetState) {
        _searchWidgetState.value = newSearchWidgetState
    }


    fun searchVillagesByName(name: String) {
        viewModelScope.launch(Dispatchers.IO) {
            villageRepository.searchVillagesByName(name)
                .collectLatest { villages ->
                    _searchedVillages.value = villages
                }
        }
    }

    fun deleteVillage(villageId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            villageRepository.deleteVillage(villageId)
                .collectLatest { villageResponse ->
                    _deletedVillage.value = villageResponse
                }
        }
    }

    fun getVillagesNames() {
        viewModelScope.launch(Dispatchers.IO) {
            villageRepository.getVillagesName()
                .collectLatest { villagesNames ->
                    _villagesNames.value = villagesNames
                }
        }
    }


}