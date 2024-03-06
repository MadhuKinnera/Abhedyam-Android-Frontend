package com.madhu.projectkapp1.data.datasource

import com.madhu.projectkapp1.data.entity.GeneralResponse
import com.madhu.projectkapp1.data.entity.NameAndId
import com.madhu.projectkapp1.data.entity.Product
import com.madhu.projectkapp1.data.entity.ProductResponseModel
import retrofit2.Response

interface ProductDataSource {


    suspend fun addProduct(token: String, productDto: Product): Response<GeneralResponse<Product>>

    suspend fun getProducts(token: String): Response<GeneralResponse<List<ProductResponseModel>>>

    suspend fun getProductById(
        token: String,
        productId: Int
    ): Response<GeneralResponse<ProductResponseModel>>

    suspend fun getProductsNames(token: String): Response<GeneralResponse<List<NameAndId>>>

    suspend fun searchProductsByName(
        token: String,
        productName: String
    ): Response<GeneralResponse<List<ProductResponseModel>>>

    suspend fun deleteProduct(token: String, productId: Int): Response<GeneralResponse<Product>>

}