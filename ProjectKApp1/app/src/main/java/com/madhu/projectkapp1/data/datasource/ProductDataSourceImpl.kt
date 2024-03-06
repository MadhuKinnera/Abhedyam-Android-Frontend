package com.madhu.projectkapp1.data.datasource

import android.util.Log
import com.madhu.projectkapp1.data.api.ApiService
import com.madhu.projectkapp1.data.entity.GeneralResponse
import com.madhu.projectkapp1.data.entity.NameAndId
import com.madhu.projectkapp1.data.entity.Product
import com.madhu.projectkapp1.data.entity.ProductResponseModel
import retrofit2.Response
import javax.inject.Inject

class ProductDataSourceImpl @Inject constructor(
    private val apiService: ApiService,
) : ProductDataSource {

    private val tag = "ProductDataSourceImpl"


    override suspend fun addProduct(
        token: String,
        productDto: Product
    ): Response<GeneralResponse<Product>> {
        Log.d(tag, "Inside Add Product ")
        return apiService.addProduct(token, productDto)
    }


    override suspend fun getProducts(token: String): Response<GeneralResponse<List<ProductResponseModel>>> {
        Log.d(tag, "Inside get Products ")
        return apiService.getProducts(token)
    }

    override suspend fun getProductById(
        token: String,
        productId: Int
    ): Response<GeneralResponse<ProductResponseModel>> {
        Log.d(tag, "Inside get Product By Id With ProductId $productId ")
        return apiService.getProductById(token, productId)
    }

    override suspend fun getProductsNames(token: String): Response<GeneralResponse<List<NameAndId>>> {
        Log.d(tag, "Inside get Products Names ")
        return apiService.getProductsNames(token)
    }

    override suspend fun searchProductsByName(
        token: String,
        productName: String
    ): Response<GeneralResponse<List<ProductResponseModel>>> {
        Log.d(tag, "Inside Search Products By Name $productName ")
        return apiService.searchProductsByName(token, productName)
    }

    override suspend fun deleteProduct(
        token: String,
        productId: Int
    ): Response<GeneralResponse<Product>> {
        Log.d(tag, "Inside Delete Products By Id $productId ")
        return apiService.deleteProduct(token, productId)

    }

}