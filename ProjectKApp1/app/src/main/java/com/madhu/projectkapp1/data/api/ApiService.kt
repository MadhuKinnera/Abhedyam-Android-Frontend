package com.madhu.projectkapp1.data.api

import com.madhu.projectkapp1.data.entity.Customer
import com.madhu.projectkapp1.data.entity.CustomerDto
import com.madhu.projectkapp1.data.entity.CustomerPersonalDto
import com.madhu.projectkapp1.data.entity.CustomerResponseModel
import com.madhu.projectkapp1.data.entity.GeneralResponse
import com.madhu.projectkapp1.data.entity.JwtResponse
import com.madhu.projectkapp1.data.entity.NameAndId
import com.madhu.projectkapp1.data.entity.Product
import com.madhu.projectkapp1.data.entity.ProductResponseModel
import com.madhu.projectkapp1.data.entity.RecordDto
import com.madhu.projectkapp1.data.entity.RecordResponseModel
import com.madhu.projectkapp1.data.entity.SaleRecord
import com.madhu.projectkapp1.data.entity.Transaction
import com.madhu.projectkapp1.data.entity.TransactionDto
import com.madhu.projectkapp1.data.entity.User
import com.madhu.projectkapp1.data.entity.UserDto
import com.madhu.projectkapp1.data.entity.Village
import com.madhu.projectkapp1.data.entity.VillageWiseResponse
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    //login api

    @GET("auth/login")
    suspend fun login(
        @Query("email") email: String,
        @Query("password") password: String
    ): Response<JwtResponse>

    //customer apis

    @POST("customer/addCustomer")
    suspend fun addCustomer(
        @Header("Authorization") token: String,
        @Body customerDto: CustomerDto
    ): Response<GeneralResponse<Customer>>

    @GET("customer/getCustomerResponseModels")
    suspend fun getCustomers(
        @Header("Authorization") token: String
    ): Response<GeneralResponse<List<CustomerResponseModel>>>


    @GET("customer/getCustomerResponseModelByCustomerId/{customerId}")
    suspend fun getCustomerById(
        @Header("Authorization") token: String,
        @Path("customerId") customerId: Int
    ): Response<GeneralResponse<CustomerResponseModel>>


    @GET("customer/getCustomersName")
    suspend fun getCustomersNames(
        @Header("Authorization") token: String,
    ): Response<GeneralResponse<List<NameAndId>>>


    @GET("customer/getCustomersByCustomerNameContains/{customerName}")
    suspend fun searchCustomersByName(
        @Header("Authorization") token: String,
        @Path("customerName") customerName: String
    ): Response<GeneralResponse<List<CustomerResponseModel>>>


    @GET("customer/getCustomerPersonalInformation/{customerCode}")
    suspend fun getCustomerPersonalDetails(
        @Path("customerCode") customerCode: String,
    ): Response<GeneralResponse<CustomerPersonalDto>>


    @DELETE("customer/deleteCustomer/{customerId}")
    suspend fun deleteCustomer(
        @Header("Authorization") token: String,
        @Path("customerId") customerId: Int,
    ): Response<GeneralResponse<Customer>>


    //Products apis

    @POST("product/addProduct")
    suspend fun addProduct(
        @Header("Authorization") token: String,
        @Body productDto: Product
    ): Response<GeneralResponse<Product>>


    @GET("product/getProductResponseModels")
    suspend fun getProducts(
        @Header("Authorization") token: String
    ): Response<GeneralResponse<List<ProductResponseModel>>>


    @GET("product/getProductResponseModelByRank/{productId}")
    suspend fun getProductById(
        @Header("Authorization") token: String,
        @Path("productId") productId: Int
    ): Response<GeneralResponse<ProductResponseModel>>


    @GET("product/getProductsName")
    suspend fun getProductsNames(
        @Header("Authorization") token: String,
    ): Response<GeneralResponse<List<NameAndId>>>

    @GET("product/getProductContainingProductName/{productName}")
    suspend fun searchProductsByName(
        @Header("Authorization") token: String,
        @Path("productName") productName: String
    ): Response<GeneralResponse<List<ProductResponseModel>>>

    @DELETE("product/deleteProduct/{productId}")
    suspend fun deleteProduct(
        @Header("Authorization") token: String,
        @Path("productId") productId: Int
    ): Response<GeneralResponse<Product>>


    //record apis

    @GET("record/getRecordResponseModels")
    suspend fun getRecords(
        @Header("Authorization") token: String
    ): Response<GeneralResponse<List<RecordResponseModel>>>


    @GET("record/getRecordResponeModelByRecordId/{recordId}")
    suspend fun getRecordById(
        @Header("Authorization") token: String,
        @Path("recordId") recordId: Int
    ): Response<GeneralResponse<RecordResponseModel>>


    @POST("record/addRecord")
    suspend fun addRecord(
        @Header("Authorization") token: String,
        @Body recordDto: RecordDto
    ): Response<GeneralResponse<SaleRecord>>


    @GET("record/getRecordsByRecordIdOrCustomerNameOrProductName/{recordId}/{customerName}/{productName}")
    suspend fun searchRecordsByName(
        @Header("Authorization") token: String,
        @Path("recordId") recordId: Int,
        @Path("customerName") customerName: String,
        @Path("productName") productName: String
    ): Response<GeneralResponse<List<RecordResponseModel>>>


    //village apis


    @GET("village/getVillageWiseData")
    suspend fun getVillageWiseData(
        @Header("Authorization") token: String
    ): Response<GeneralResponse<List<VillageWiseResponse>>>


    @GET("village/getVillageWiseDataByVillageId/{villageId}")
    suspend fun getVillageWiseDataByVillageId(
        @Header("Authorization") token: String,
        @Path("villageId") villageId: Int
    ): Response<GeneralResponse<VillageWiseResponse>>


    @POST("village/addVillage")
    suspend fun addVillage(
        @Header("Authorization") token: String,
        @Body villageDto: Village
    ): Response<GeneralResponse<Village>>


    @GET("village/getVillageWiseDataByVillageName/{villageName}")
    suspend fun searchVillagesByName(
        @Header("Authorization") token: String,
        @Path("villageName") villageName: String
    ): Response<GeneralResponse<List<VillageWiseResponse>>>


    @DELETE("village/deleteVillage/{villageId}")
    suspend fun deleteVillage(
        @Header("Authorization") token: String,
        @Path("villageId") villageId: Int
    ): Response<GeneralResponse<Village>>


    @GET("village/getVillagesName")
    suspend fun getVillagesNames(
        @Header("Authorization") token: String,
    ): Response<GeneralResponse<List<NameAndId>>>


    //transaction apis

    @POST("transaction/addTransaction")
    suspend fun addTransaction(
        @Header("Authorization") token: String,
        @Body transactionDto: TransactionDto
    ): Response<GeneralResponse<Transaction>>


    //utils apis


    @Multipart
    @POST("cloudinary/uploadImage")
    suspend fun uploadImageToCloudinary(
        @Part image: MultipartBody.Part
    ): Response<JwtResponse>


    //user apis

    @POST("user/addUser")
    suspend fun adduser(
        @Body userDto: UserDto
    ): Response<GeneralResponse<User>>


}
