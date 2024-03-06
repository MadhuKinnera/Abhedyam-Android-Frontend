package com.madhu.projectkapp1.data.datasource

import com.madhu.projectkapp1.data.entity.Customer
import com.madhu.projectkapp1.data.entity.CustomerDto
import com.madhu.projectkapp1.data.entity.CustomerPersonalDto
import com.madhu.projectkapp1.data.entity.CustomerResponseModel
import com.madhu.projectkapp1.data.entity.GeneralResponse
import com.madhu.projectkapp1.data.entity.NameAndId
import retrofit2.Response

interface CustomerDataSource {

    //  suspend fun loginUser()

    suspend fun addCustomer(
        token: String,
        customerDto: CustomerDto
    ): Response<GeneralResponse<Customer>>

    suspend fun getCustomers(token: String): Response<GeneralResponse<List<CustomerResponseModel>>>

    suspend fun getCustomerById(
        token: String,
        customerId: Int
    ): Response<GeneralResponse<CustomerResponseModel>>

    suspend fun getCustomersNames(token: String): Response<GeneralResponse<List<NameAndId>>>

    suspend fun searchCustomersByName(
        token: String,
        customerName: String
    ): Response<GeneralResponse<List<CustomerResponseModel>>>


    suspend fun getCustomerPersonalDetails(customerCode: String): Response<GeneralResponse<CustomerPersonalDto>>

    suspend fun deleteCustomer(
        token: String,
        customerId: Int
    ): Response<GeneralResponse<Customer>>

}