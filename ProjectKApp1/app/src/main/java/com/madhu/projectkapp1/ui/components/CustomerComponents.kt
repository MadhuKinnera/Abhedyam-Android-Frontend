package com.madhu.projectkapp1.ui.components

import android.annotation.SuppressLint
import android.net.Uri
import android.util.Log
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.material3.Scaffold
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
import com.madhu.projectkapp1.data.entity.AddressDto
import com.madhu.projectkapp1.data.entity.Customer
import com.madhu.projectkapp1.data.entity.CustomerDto
import com.madhu.projectkapp1.data.entity.CustomerResponseModel
import com.madhu.projectkapp1.data.entity.JwtResponse
import com.madhu.projectkapp1.data.entity.NameAndId
import com.madhu.projectkapp1.ui.theme.Purple40
import com.madhu.projectkapp1.ui.theme.acmeFontFamily
import com.madhu.projectkapp1.ui.viewmodel.CustomerViewModel
import com.madhu.projectkapp1.ui.viewmodel.UploadImageViewModel
import com.madhu.projectkapp1.utility.IdType
import com.madhu.projectkapp1.utility.ResourceState
import kotlinx.coroutines.CoroutineScope


@Composable
fun CustomerResponseModelDetail(
    customerResponseModel: CustomerResponseModel,
    modifier: Modifier = Modifier,
    navController: NavHostController,
    scope: CoroutineScope,
    snackbarHostState: SnackbarHostState
) {


    val customer = customerResponseModel.customer
    val address = customerResponseModel.customer.address
    val records = customerResponseModel.saleRecords
    val recordStatus = customerResponseModel.recordStatus
    val totalAmount = customerResponseModel.totalAmount
    val totalPaidAmount = customerResponseModel.totalPaidAmount
    val totalRecords = records.size
    val totalRemainingAmount = customerResponseModel.totalRemaininAmount
    val totalProducts = customerResponseModel.totalProducts

    val map = mutableMapOf<String, String>()

    map["Total Amount"] = totalAmount.toString()
    map["Total PaidAmount"] = totalPaidAmount.toString()
    map["Total RemainingAmount"] = totalRemainingAmount.toString()
    map["Total Products"] = totalProducts.toString()
    map["Total Records"] = totalRecords.toString()

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(4.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        items(listOf(customer)) {
            DisplayCustomer(
                customer = customer,
                backgoundColor = Color.LightGray,
                showTitle = true,
                showContent = true,
                showProfileImage = true
            )
        }

        item {
            DisplayMapElements(
                title = "Amount Details",
                map = map,
                backgoundColor = MaterialTheme.colorScheme.secondaryContainer
            )
        }



        address?.let {
            item {
                DisplayAddress(address = it)
            }

        }
        items(records) { record ->
            DisplayRecord(
                record = record,
                backgoundColor = MaterialTheme.colorScheme.tertiaryContainer,
                recordStatus = recordStatus
            )
        }

        if (totalRecords == 0) {
            item {
                DeleteConfirmationScreen(
                    id = customer.customerId,
                    idType = IdType.CUSTOMER_ID,
                    navController = navController,
                    scope = scope,
                    snackbarHostState = snackbarHostState
                )
            }
        }

    }
}


@SuppressLint("SuspiciousIndentation")
@Composable
fun DisplayCustomer(
    customer: Customer,
    modifier: Modifier = Modifier,
    backgoundColor: Color = Color.LightGray,
    showTitle: Boolean = false,
    showContent: Boolean = false,
    showProfileImage: Boolean = false

) {

    val customerName = customer.customerName
    val customerCode = customer.customerCode
    val email = customer.email
    val mobileNo = customer.mobileNo
    val age = customer.age
    val profession = customer.profession
    val keywords = customer.keywords
    val description = customer.description

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
        if (showProfileImage) {
            val imageColor = android.graphics.Color.parseColor(customer.flag)
            CoilImage(
                imageUrl = customer.profileImageUrl,
                imageColor = imageColor,
                defaultPainter = painterResource(id = R.drawable.default_customer),
                modifier = Modifier.align(Alignment.CenterHorizontally),
                borderWidth = 2.dp
            )
        }

        if (showTitle)
            DisplayTitle(customerName)

        if (showCard) {


            Column(
                modifier = modifier
                    .fillMaxSize()
                    .padding(12.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                if (!showTitle)
                    SideBySideText(key = "Name", value = customerName)

                if (email!=null && email != "")
                    SideBySideText(key = "Email", value = email)
                if (mobileNo != "")
                SideBySideText(key = "Mobile No", value = mobileNo)
                customerCode?.let {
                    SideBySideText(key = "Customer Code", value = it)
                }
                if (age != null && age!=0)
                    SideBySideText(key = "Age", value = age.toString())

                if (profession!=null && profession != "")
                    SideBySideText(key = "Profession", value = profession)

                Log.d("Check", "Keywords are ${keywords?.size}")
                Log.d("Check", "Keywords are $keywords")



                if (keywords?.size!! >1) {
                    val allKeywords = keywords.joinToString(" | ")
                    SideBySideText(key = "Keywords", value = allKeywords)
                }
                if (description!=null && description != "")
                    SideBySideText(key = "Description", value = description)

            }

        }
    }


}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddCustomer(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    scope: CoroutineScope,
    snackbarHostState: SnackbarHostState,
    customerViewModel: CustomerViewModel = hiltViewModel(),
    uploadImageViewModel: UploadImageViewModel = hiltViewModel(),
) {

    val tag = "AddCustomer"


    var customerName by remember { mutableStateOf("") }
    var age by remember { mutableStateOf("") }
    var profession by remember { mutableStateOf("") }
    var mobileNo by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var pickedImageUri by remember { mutableStateOf<Uri?>(null) }


    var street by remember { mutableStateOf("") }
    var landmark by remember { mutableStateOf("") }
    var addressDescription by remember { mutableStateOf("") }
    var villageName by remember { mutableStateOf("Select Village *") }
    var keywordString by remember { mutableStateOf("") }

    var isFieldsFilled by remember { mutableStateOf(false) }

    isFieldsFilled = customerName != "" && villageName != "" && mobileNo != ""

    val context = LocalContext.current
    val focusManager = LocalFocusManager.current

    val imageResponse by uploadImageViewModel.uploadResponse.collectAsState()

    var imageUrl by remember { mutableStateOf("") }

    var isInProgess by remember { mutableStateOf(false) }

    var buttonText by remember { mutableStateOf("Add Customer") }

    LaunchedEffect(imageResponse) {

        Log.d(tag, "Inside Image Response")
        if (imageUrl == "") {
            when (imageResponse) {
                is ResourceState.Success -> {
                    imageUrl = (imageResponse as ResourceState.Success<JwtResponse>).data.jwtToken
                    Log.d(tag, "Success the url is $imageUrl")
                }

                else -> {}
            }

        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {


        HandleVillageDropDown(
            selectedOption = villageName,
            onSelectedOptionChange = { villageName = it },
            onSelectedOptionIdChange = {}
        )

        CommonOutlineTextField(
            value = customerName,
            onValueChange = { customerName = it },
            label = "Customer Name *",
            focusManager = focusManager,
            isMandatory = true,
            description = "Customer Name *"
        )


        CommonOutlineTextField(
            value = mobileNo,
            onValueChange = {
                if (it.length <= 10) {
                    mobileNo = it.filter { char -> char.isDigit() }
                }
            },

            label = "Mobile Number *",
            focusManager = focusManager,
            isMandatory = true,
            isNumber = true,
            description = "Mobile Number"
        )

        CommonOutlineTextField(
            value = street,
            onValueChange = { street = it },
            label = "Street *",
            focusManager = focusManager,
            description = "Street"
        )



        ImageUploadField(contentDescription = "Customer Profile Image ") {
            pickedImageUri = it
        }

        CommonOutlineTextField(
            value = landmark,
            onValueChange = { landmark = it },
            label = "Landmark",
            focusManager = focusManager,
            description = "Landmark"
        )


        CommonOutlineTextField(
            value = description,
            onValueChange = { description = it },
            label = "Cust Desc",
            focusManager = focusManager,
            description = "Customer Description"

        )


        CommonOutlineTextField(
            value = age,
            onValueChange = {
                if (it.length <= 2) {
                    age = it.filter { char -> char.isDigit() }
                }
            },
            label = "Age",
            focusManager = focusManager,
            isNumber = true,
            description = "Age"
        )

        CommonOutlineTextField(
            value = profession,
            onValueChange = { profession = it },
            label = "Profession",
            focusManager = focusManager,
            description = "Profession"
        )



        CommonOutlineTextField(
            value = email,
            onValueChange = { email = it },
            label = "Email",
            focusManager = focusManager,
            description = "Email"
        )










        CommonOutlineTextField(
            value = addressDescription,
            onValueChange = { addressDescription = it },
            label = "Addr Desc",
            focusManager = focusManager,
            description = "Address Description"
        )



        OutlinedTextField(
            value = keywordString,
            onValueChange = { keywordString = it },
            label = {
                Text("Keywords")
            },
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    focusManager.clearFocus(true)
                }
            ),
            modifier = Modifier.semantics {
                contentDescription = "Keywords"
            }
        )


        var keywords: List<String>

        if (keywordString == "") {
            keywords = listOf()
        } else if (keywordString.contains(",")) {
            keywords = keywordString.split(",")
        } else {
            keywords = listOf(keywordString)
        }

        val customerDto = CustomerDto(
            customerName = customerName,
            age = age.toIntOrNull()?:0,
            profession = profession.ifEmpty { null },
            mobileNo = mobileNo.ifEmpty { null },
            email = email.ifEmpty { null },
            description = description.ifEmpty { null },
            addressDto = AddressDto(street.ifEmpty { null }, landmark.ifEmpty { null }, description.ifEmpty { null }, villageName),
            keywords = keywords.ifEmpty { null }
        )




        if (imageUrl != "" && isInProgess) {
            Log.d(tag, "Inside adding Custo with url $imageUrl")
            customerDto.profileImage = imageUrl
            Log.d(tag, "The Cust Dto is $customerDto")
            customerViewModel.addCustomer(customerDto)
            showSnackBar(
                scope = scope,
                snackbarHostState = snackbarHostState,
                navController = navController,
                message = "Customer Added"
            )
            isInProgess = false
        }







        ElevatedButton(
            enabled = isFieldsFilled && !isInProgess,
            onClick = {
                buttonText="Adding..."
                Log.d(tag, "The picked Uri $pickedImageUri")
                if (pickedImageUri != null) {
                    uploadImageViewModel.uploadImageToCloudinary(pickedImageUri!!, context)
                    isInProgess = true
                } else {
                    customerViewModel.addCustomer(customerDto)
                    showSnackBar(
                        scope = scope,
                        snackbarHostState = snackbarHostState,
                        navController = navController,
                        message = "Customer Added"
                    )
                }
                buttonText="Added"

            },
            shape = RoundedCornerShape(topStart = 15.dp, bottomEnd = 15.dp),
            modifier = Modifier
                .padding(8.dp)
                .height(52.dp)
                .semantics {
                    contentDescription =
                        "Add a new customer to your database with ease. Enter relevant details and keep your customer information organized."
                },
            colors = ButtonDefaults.elevatedButtonColors(containerColor = Purple40)
        ) {
            Text(
                 text =buttonText,
                fontSize = 24.sp,
                color = if (isFieldsFilled) Color.White else Color.Black,
                fontFamily = acmeFontFamily,
                fontWeight = FontWeight.Light
            )
        }

    }

}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DisplayCustomersNames(
    customerViewModel: CustomerViewModel = hiltViewModel(),
    navController: NavHostController
) {

    val tag = "CustomerDetailScreen"

    Log.d(tag, "Inside Customer Detail Screen")
    customerViewModel.getCustomersNames()
    val customerResponse by customerViewModel.customersNames.collectAsState()

    Scaffold(
        topBar = {
            BackTopBar(navController = navController, screenTitle = "Customer Names")
        }
    ) { padding ->

        when (customerResponse) {
            is ResourceState.Loading -> {
                Log.d(tag, "Loading Customer Names")
                LoadingScreen()
            }

            is ResourceState.Success -> {
                Column(
                    modifier = Modifier.padding(padding)
                ) {
                    Log.d(tag, "Customer Names Fetched Success ")
                    val customersNames =
                        (customerResponse as ResourceState.Success<List<NameAndId>>).data

                    LazyColumn {
                        items(customersNames.map { it.name }) { name ->
                            Text(text = name)
                        }
                    }


                }
            }

            is ResourceState.Error -> {
                Log.d(tag, "Failed To Fetch Customer Data")
                Column {
                    Text(text = "Some thing went wrong ${(customerResponse as ResourceState.Error<*>).error}")
                }
            }
        }
    }


}


@Composable
fun HandleCustomerDropDown(
    selectedOption: String,
    onSelectedOptionChange: (String) -> Unit,
    onSelectedOptionIdChange: (Int) -> Unit,
    customerViewModel: CustomerViewModel = hiltViewModel()
) {
    val tag = "CustomerDetailScreen"

    Log.d(tag, "Inside Customer Detail Screen")
    LaunchedEffect(true) {
        customerViewModel.getCustomersNames()
    }

    val customerResponse by customerViewModel.customersNames.collectAsState()


    when (customerResponse) {
        is ResourceState.Loading -> {
            Log.d(tag, "Loading Customers Name Data")
        }

        is ResourceState.Success -> {

            Log.d(tag, "Customer Names  Fetched Success ")
            val customersNames =
                (customerResponse as ResourceState.Success<List<NameAndId>>).data


            DropdownMenuExample(
                options = customersNames,
                selectedOption = selectedOption,
                onSelectedOptionChange = onSelectedOptionChange,
                onSelectedOptionIdChange = onSelectedOptionIdChange
            )

        }

        is ResourceState.Error -> {
            Log.d(tag, "Failed To Fetch Customer Data")
        }
    }


}

