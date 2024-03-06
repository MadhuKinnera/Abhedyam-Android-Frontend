package com.madhu.projectkapp1.data.datasource

import android.util.Log
import com.madhu.projectkapp1.data.api.ApiService
import com.madhu.projectkapp1.data.entity.Customer
import com.madhu.projectkapp1.data.entity.CustomerDto
import com.madhu.projectkapp1.data.entity.CustomerPersonalDto
import com.madhu.projectkapp1.data.entity.CustomerResponseModel
import com.madhu.projectkapp1.data.entity.GeneralResponse
import com.madhu.projectkapp1.data.entity.NameAndId
import retrofit2.Response
import javax.inject.Inject

class CustomerDataSourceImpl @Inject constructor(
    private val apiService: ApiService,
) : CustomerDataSource {

    private val tag = "CustomerDataSourceImpl"

    init {
        Log.d(tag, "Inside Customer DataSource Impl init ")

    }


    override suspend fun addCustomer(
        token: String,
        customerDto: CustomerDto
    ): Response<GeneralResponse<Customer>> {
        Log.d(tag, "Inside Add Customer")
        Log.d(tag, "The token is $token")
        Log.d(tag, "Customer Dto is $customerDto")
        return apiService.addCustomer(token, customerDto)
    }


    override suspend fun getCustomers(token: String): Response<GeneralResponse<List<CustomerResponseModel>>> {
        Log.d(tag, "Inside get Customers ")
        return apiService.getCustomers(token)
    }

    override suspend fun getCustomerById(
        token: String,
        customerId: Int
    ): Response<GeneralResponse<CustomerResponseModel>> {
        Log.d(tag, "Inside get Customer By Id With customerId $customerId ")
        return apiService.getCustomerById(token, customerId)
    }

    override suspend fun getCustomersNames(token: String): Response<GeneralResponse<List<NameAndId>>> {
        Log.d(tag, "Inside get Customers Names ")
        return apiService.getCustomersNames(token)
    }

    override suspend fun searchCustomersByName(
        token: String,
        customerName: String
    ): Response<GeneralResponse<List<CustomerResponseModel>>> {
        Log.d(tag, "Searching Customers By $customerName ")
        return apiService.searchCustomersByName(token, customerName)
    }

    override suspend fun getCustomerPersonalDetails(customerCode: String): Response<GeneralResponse<CustomerPersonalDto>> {
        Log.d(tag, "getting Customer Personal Info with $customerCode ")
        Log.d(tag,"Before Response")
        val res  =  apiService.getCustomerPersonalDetails(customerCode)
        Log.d(tag,"After Response ${res.isSuccessful} $res")
        return res
    }

    override suspend fun deleteCustomer(
        token: String,
        customerId: Int
    ): Response<GeneralResponse<Customer>> {
        Log.d(tag, "Deleting Customer With Customer Id $customerId")
        return apiService.deleteCustomer(customerId = customerId, token = token)
    }


}