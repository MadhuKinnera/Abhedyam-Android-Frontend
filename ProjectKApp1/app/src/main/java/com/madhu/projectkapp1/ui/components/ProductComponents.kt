package com.madhu.projectkapp1.ui.components

import android.net.Uri
import android.util.Log
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.madhu.projectkapp1.R
import com.madhu.projectkapp1.data.entity.JwtResponse
import com.madhu.projectkapp1.data.entity.NameAndId
import com.madhu.projectkapp1.data.entity.Product
import com.madhu.projectkapp1.data.entity.ProductResponseModel
import com.madhu.projectkapp1.data.entity.VillageWiseCount
import com.madhu.projectkapp1.ui.theme.Purple40
import com.madhu.projectkapp1.ui.theme.acmeFontFamily
import com.madhu.projectkapp1.ui.viewmodel.ProductViewModel
import com.madhu.projectkapp1.ui.viewmodel.UploadImageViewModel
import com.madhu.projectkapp1.utility.IdType
import com.madhu.projectkapp1.utility.ResourceState
import kotlinx.coroutines.CoroutineScope

@Composable
fun DisplayProduct(
    product: Product,
    modifier: Modifier = Modifier,
    backgoundColor: Color = Color.LightGray,
    showTitle: Boolean = false,
    showProductImage: Boolean = true,
    showContent: Boolean = false,
    showBoughtPrice:Boolean = true
) {

    val productName = product.productName
    val productUrl = product.imageUrl
    val productDescription = product.description
    val buyedPrice = product.buyedPrice
    val sellingPrice = product.sellingPrice

    var showCard by remember { mutableStateOf(showContent) }

    Card(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
            .animateContentSize()
            .clickable {
                showCard = !showCard
            },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = backgoundColor
        ),
        elevation = CardDefaults.elevatedCardElevation()

    ) {

        if (showProductImage)
            CoilImage(
                imageUrl = productUrl,
                defaultPainter = painterResource(id = R.drawable.default_product),
                size = 154.dp,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
            )

        if (showTitle)
            DisplayTitle(productName)

        if (showCard) {
            Column(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                if(productDescription!=null && productDescription!="")
                    SideBySideText(key = "P Description", value = productDescription)

                if(showBoughtPrice) {
                    SideBySideText(key = "BoughtPrice", value = buyedPrice.toString())
                }

                SideBySideText(key = "SellingPrice", value = sellingPrice.toString())
            }
        }
    }
}

@Composable
fun ProductResponseModelDetail(
    productResponseModel: ProductResponseModel,
    navController: NavHostController,
    scope: CoroutineScope,
    snackbarHostState: SnackbarHostState
) {


    val product = productResponseModel.product

    val villagesWiseCount = productResponseModel.villageWiseCount

    val productSellCount = productResponseModel.productSellCount
    val collectedAmount = productResponseModel.collectedAmount
    val pendingAmount = productResponseModel.pendingAmount
    val productSoldAmount = productResponseModel.totalSelledAmount

    val map = mutableMapOf<String, String>()
    map["productSellCount"] = productSellCount.toString()
    map["productSoldAmount"] = productSoldAmount.toString()
    map["pendingAmount"] = pendingAmount.toString()
    map["collectedAmount"] = collectedAmount.toString()


    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(4.dp),
        horizontalAlignment = Alignment.CenterHorizontally

    ) {

        item {
            DisplayProduct(
                product = product,
                backgoundColor = MaterialTheme.colorScheme.primaryContainer,
                showTitle = true
            )
        }

        item {
            DisplayMapElements(
                title = "Product Statistics ", map = map
            )
        }

        items(villagesWiseCount) { villageWiseCount ->
            DisplayProductVillageWise(
                villageWiseCount = villageWiseCount,
                backgoundColor = MaterialTheme.colorScheme.tertiaryContainer
            )
        }

        if (productSellCount == 0) {
            item {
                DeleteConfirmationScreen(
                    id = product.productId!!,
                    idType = IdType.PRODUCT_ID,
                    navController = navController,
                    scope = scope,
                    snackbarHostState = snackbarHostState
                )
            }
        }


    }


}


@Composable
fun DisplayProductVillageWise(
    villageWiseCount: VillageWiseCount,
    backgoundColor: Color = Color.LightGray
) {

    val modifier1 = Modifier
        .fillMaxWidth()
        .padding(20.dp)
        .clip(shape = RoundedCornerShape(16.dp))
        .background(backgoundColor)
        .padding(12.dp)
    Column(
        modifier = modifier1
    ) {
        SideBySideText(
            key = villageWiseCount.villageName,
            value = villageWiseCount.count.toString()
        )
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddProduct(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    snackbarHostState: SnackbarHostState,
    scope: CoroutineScope,
    productViewModel: ProductViewModel = hiltViewModel(),
    uploadImageViewModel: UploadImageViewModel = hiltViewModel()
) {


    val tag = "AddProduct"

    var productName by remember { mutableStateOf("") }
    var buyedPrice by remember { mutableStateOf("") }
    var sellingPrice by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var pickedImageUri by remember { mutableStateOf<Uri?>(null) }

    var isFieldsFilled by remember { mutableStateOf(false) }

    isFieldsFilled = productName != "" && buyedPrice != "" && sellingPrice != ""

    var imageUrl by remember { mutableStateOf("") }

    var isInProgess by remember { mutableStateOf(false) }

    val focusManager = LocalFocusManager.current
    val context = LocalContext.current


    val imageResponse by uploadImageViewModel.uploadResponse.collectAsState()

    LaunchedEffect(imageResponse) {
        if (imageUrl == "") {
            when (imageResponse) {

                is ResourceState.Success -> {
                    imageUrl = (imageResponse as ResourceState.Success<JwtResponse>).data.jwtToken
                }

                else -> {}
            }

        }
    }


    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
            .animateContentSize(),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {


        ImageUploadField(
            defaultPainter = painterResource(id = R.drawable.default_product),
            contentDescription = "Product Image"
        ){
            pickedImageUri = it
        }
        Log.d(tag, "The Picked Image Uri is $pickedImageUri")


        CommonOutlineTextField(
            value = productName,
            onValueChange = { productName = it },
            label = "Product Name *",
            focusManager = focusManager,
            isMandatory = true,
            description = "Product Name"
        )
        CommonOutlineTextField(
            value = buyedPrice,
            onValueChange = { buyedPrice = it.filter { char -> char.isDigit() } },
            label = "Bought Price *",
            focusManager = focusManager,
            isMandatory = true,
            isNumber = true,
            description = "Bought Price"
        )



        CommonOutlineTextField(
            value = sellingPrice,
            onValueChange = { sellingPrice = it.filter { char -> char.isDigit() } },
            label = "Selling Price *",
            focusManager = focusManager,
            isMandatory = true,
            isNumber = true,
            description = "Selling Price"
        )



        OutlinedTextField(
            value = description,
            onValueChange = { description = it },
            label = { Text("Description") },
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    focusManager.clearFocus(true)
                }
            ),
            maxLines = 1,
            modifier = Modifier
                .horizontalScroll(rememberScrollState())
                .semantics {
                    contentDescription = "Description"
                }
        )
        val productDto = Product(
            productName = productName,
            buyedPrice = buyedPrice.toIntOrNull() ?: 0,
            sellingPrice = sellingPrice.toIntOrNull() ?: 0,
            description = description
        )

        if (imageUrl != "" && isInProgess) {
            productDto.imageUrl = imageUrl
            productViewModel.addProduct(productDto)
            showSnackBar(
                scope = scope,
                snackbarHostState = snackbarHostState,
                navController = navController,
                message = "Product Added "
            )
            isInProgess = false
        }


        ElevatedButton(
            enabled = isFieldsFilled && !isInProgess,
            onClick = {
                Log.d(tag, "The pickedImage Uri is $pickedImageUri")

                if (!isInProgess)
                    isInProgess = true

                if (pickedImageUri != null) {
                    uploadImageViewModel.uploadImageToCloudinary(pickedImageUri!!, context)
                    Log.d(tag, "After Calling Uploading")
                } else {
                    Log.d(tag, "Uri Not Selected")
                    productViewModel.addProduct(productDto)
                    showSnackBar(
                        scope = scope,
                        snackbarHostState = snackbarHostState,
                        navController = navController,
                        message = "Product Added "
                    )
                }
            },
            shape = RoundedCornerShape(topStart = 15.dp, bottomEnd = 15.dp),
            modifier = Modifier
                .padding(8.dp)
                .height(52.dp)
                .semantics {
                    contentDescription =
                        "Expand your product catalog by adding a new item. Enter product details for accurate inventory management."
                },
            colors = ButtonDefaults.elevatedButtonColors(
                containerColor = Purple40
            )
        ) {
            Text(
                "Add Product",
                fontSize = 24.sp,
                color = if (isFieldsFilled) Color.White else Color.Black,
                fontFamily = acmeFontFamily,
                fontWeight = FontWeight.Light
            )
        }

    }
}

@Composable
fun HandleProductDropDown(
    selectedOption: String,
    onSelectedOptionChange: (String) -> Unit,
    onSelectedOptionIdChange: (Int) -> Unit,
    productViewModel: ProductViewModel = hiltViewModel()
) {
    val tag = "HandleProductDropDown"

    Log.d(tag, "Inside Product Handle")
    LaunchedEffect(true) {
        productViewModel.getProductsNames()
    }

    val productResponse by productViewModel.productsNames.collectAsState()


    when (productResponse) {
        is ResourceState.Loading -> {
            Log.d(tag, "Loading Product Name Data")
        }

        is ResourceState.Success -> {

            Log.d(tag, "Product Names  Fetched Success ")
            val productsNames =
                (productResponse as ResourceState.Success<List<NameAndId>>).data


            DropdownMenuExample(
                options = productsNames,
                selectedOption = selectedOption,
                onSelectedOptionChange = onSelectedOptionChange,
                onSelectedOptionIdChange = onSelectedOptionIdChange
            )

        }

        is ResourceState.Error -> {
            Log.d(tag, "Failed To Fetch Products Names Data")
        }
    }


}



