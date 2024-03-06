package com.madhu.projectkapp1.ui.viewmodel

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.madhu.projectkapp1.data.entity.NameAndId
import com.madhu.projectkapp1.data.entity.Product
import com.madhu.projectkapp1.data.entity.ProductResponseModel
import com.madhu.projectkapp1.data.enums.SearchWidgetState
import com.madhu.projectkapp1.ui.repository.ProductRepository
import com.madhu.projectkapp1.ui.repository.UploadImageRepository
import com.madhu.projectkapp1.utility.ResourceState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductViewModel @Inject constructor(
    private val productRepository: ProductRepository,
    private val uploadImageRepository: UploadImageRepository
) : ViewModel() {


    val tag = "ProductViewModel"


    init {
        Log.d(tag, "Products View Model Initiated ")
    }

    private val _addedProduct: MutableStateFlow<ResourceState<Product>> =
        MutableStateFlow(ResourceState.Loading())

    val addedProduct: StateFlow<ResourceState<Product>> = _addedProduct

    private val _products: MutableStateFlow<ResourceState<List<ProductResponseModel>>> =
        MutableStateFlow(ResourceState.Loading())

    val products: StateFlow<ResourceState<List<ProductResponseModel>>> = _products


    private val _product: MutableStateFlow<ResourceState<ProductResponseModel>> =
        MutableStateFlow(ResourceState.Loading())

    val product: StateFlow<ResourceState<ProductResponseModel>> = _product

    private val _productsNames: MutableStateFlow<ResourceState<List<NameAndId>>> =
        MutableStateFlow(ResourceState.Loading())

    val productsNames: StateFlow<ResourceState<List<NameAndId>>> = _productsNames


    private val _textState: MutableState<String> = mutableStateOf("")

    val textState: State<String> = _textState

    private val _searchWidgetState: MutableState<SearchWidgetState> =
        mutableStateOf(SearchWidgetState.CLOSED)

    val searchWidgetState: State<SearchWidgetState> = _searchWidgetState


    private val _searchedProducts: MutableStateFlow<ResourceState<List<ProductResponseModel>>> =
        MutableStateFlow(ResourceState.Loading())


    val searchedProducts: StateFlow<ResourceState<List<ProductResponseModel>>> =
        _searchedProducts

    private val _deletedProduct: MutableStateFlow<ResourceState<Product>> =
        MutableStateFlow(ResourceState.Loading())

    val deletedProduct: StateFlow<ResourceState<Product>> = _deletedProduct


    fun addProduct(productDto: Product) {
        viewModelScope.launch(Dispatchers.IO) {


            Log.d(tag, "The Product Dto is $productDto")


            productRepository.addProduct(productDto)
                .collectLatest { productResponse ->
                    _addedProduct.value = productResponse
                }
        }
    }

    fun getProducts() {
        viewModelScope.launch(Dispatchers.IO) {
            productRepository.getProducts()
                .collectLatest { products ->
                    _products.value = products
                }
        }
    }


    fun getProductsNames() {
        viewModelScope.launch(Dispatchers.IO) {
            productRepository.getProductsNames()
                .collectLatest { productsName ->
                    _productsNames.value = productsName
                }
        }
    }


    fun getProductById(productId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            productRepository.getProductById(productId).collectLatest { product ->
                _product.value = product
            }
        }
    }


    fun updateText(newText: String) {
        _textState.value = newText
    }

    fun updateSearchWidgetState(newSearchWidgetState: SearchWidgetState) {
        _searchWidgetState.value = newSearchWidgetState
    }


    fun searchProductsByName(name: String) {
        viewModelScope.launch(Dispatchers.IO) {
            productRepository.searchProductsByName(name)
                .collectLatest { products ->
                    _searchedProducts.value = products
                }
        }
    }

    fun deleteProduct(productId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            productRepository.deleteProduct(productId).collectLatest { product ->
                _deletedProduct.value = product
            }
        }
    }


}