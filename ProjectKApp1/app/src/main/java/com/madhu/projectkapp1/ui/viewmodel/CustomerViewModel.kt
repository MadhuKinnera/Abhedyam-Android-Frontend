package com.madhu.projectkapp1.ui.viewmodel

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.madhu.projectkapp1.data.entity.Customer
import com.madhu.projectkapp1.data.entity.CustomerDto
import com.madhu.projectkapp1.data.entity.CustomerPersonalDto
import com.madhu.projectkapp1.data.entity.CustomerResponseModel
import com.madhu.projectkapp1.data.entity.NameAndId
import com.madhu.projectkapp1.data.enums.SearchWidgetState
import com.madhu.projectkapp1.ui.repository.CustomerRepository
import com.madhu.projectkapp1.utility.ResourceState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class CustomerViewModel @Inject constructor(
    private val customerRepository: CustomerRepository
) : ViewModel() {


    val tag = "CustomerViewModel"

    init {
        Log.d(tag, "Customers View Model Initiated ")
    }


    private val _customerResponseModels: MutableStateFlow<ResourceState<List<CustomerResponseModel>>> =
        MutableStateFlow(ResourceState.Loading())

    val customerResponseModels: StateFlow<ResourceState<List<CustomerResponseModel>>> =
        _customerResponseModels


    private val _customerResponseModel: MutableStateFlow<ResourceState<CustomerResponseModel>> =
        MutableStateFlow(ResourceState.Loading())


    val customerResponseModel: StateFlow<ResourceState<CustomerResponseModel>> =
        _customerResponseModel


    private val _addedCustomer: MutableStateFlow<ResourceState<Customer>> =
        MutableStateFlow(ResourceState.Loading())


    val addedCustomer: StateFlow<ResourceState<Customer>> = _addedCustomer

    private val _customersNames: MutableStateFlow<ResourceState<List<NameAndId>>> =
        MutableStateFlow(ResourceState.Loading())


    val customersNames: StateFlow<ResourceState<List<NameAndId>>> = _customersNames


    private val _textState: MutableState<String> = mutableStateOf("")

    val textState: State<String> = _textState

    private val _searchWidgetState: MutableState<SearchWidgetState> =
        mutableStateOf(SearchWidgetState.CLOSED)

    val searchWidgetState: State<SearchWidgetState> = _searchWidgetState


    private val _searchedCustomers: MutableStateFlow<ResourceState<List<CustomerResponseModel>>> =
        MutableStateFlow(ResourceState.Loading())


    val searchedCustomers: StateFlow<ResourceState<List<CustomerResponseModel>>> =
        _searchedCustomers


    private val _deletedCustomer: MutableStateFlow<ResourceState<Customer>> =
        MutableStateFlow(ResourceState.Loading())

    val deletedCustomer: StateFlow<ResourceState<Customer>> = _deletedCustomer

    fun updateText(newText: String) {
        _textState.value = newText
    }

    fun updateSearchWidgetState(newSearchWidgetState: SearchWidgetState) {
        _searchWidgetState.value = newSearchWidgetState
    }


    private val _loggedInCustomer: MutableStateFlow<ResourceState<CustomerPersonalDto>> =
        MutableStateFlow(ResourceState.Loading())

    val loggedInCustomer: StateFlow<ResourceState<CustomerPersonalDto>> =
        _loggedInCustomer


    fun addCustomer(customerDto: CustomerDto) {
        viewModelScope.launch(Dispatchers.IO) {
            Log.d(tag, "The uploaded Url is ${customerDto.profileImage?.substring(5, 10)}")
            customerRepository.addCustomer(customerDto)
                .collectLatest { customer ->
                    _addedCustomer.value = customer
                }
        }
    }


    fun getCustomers() {
        viewModelScope.launch(Dispatchers.IO) {
            customerRepository.getCustomers()
                .collectLatest { customers ->
                    _customerResponseModels.value = customers
                }
        }
    }

    fun getCustomersNames() {
        viewModelScope.launch(Dispatchers.IO) {
            customerRepository.getCustomersNames()
                .collectLatest { customerName ->
                    _customersNames.value = customerName
                }
        }
    }


    fun getCustomerById(customerId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            customerRepository.getCustomerById(customerId).collectLatest {
                _customerResponseModel.value = it
            }
        }
    }

    fun searchCustomersByName(name: String) {
        viewModelScope.launch(Dispatchers.IO) {
            customerRepository.searchCustomersByname(name)
                .collectLatest { customers ->
                    _searchedCustomers.value = customers
                }
        }
    }


    fun loginCustomer(customerCode: String) {
        viewModelScope.launch(Dispatchers.IO) {
            customerRepository.getCustomerPersonalDetails(customerCode)
                .collectLatest { customerResponse ->
                    _loggedInCustomer.value = customerResponse
                }
        }
    }

    fun deleteCustomer(customerId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            customerRepository.deleteCustomer(customerId = customerId)
                .collectLatest { customer ->
                    _deletedCustomer.value = customer
                }
        }
    }


}