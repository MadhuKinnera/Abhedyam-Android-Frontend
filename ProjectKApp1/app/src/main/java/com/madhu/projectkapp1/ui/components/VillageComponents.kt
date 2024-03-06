package com.madhu.projectkapp1.ui.components

import android.net.Uri
import android.util.Log
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.madhu.projectkapp1.R
import com.madhu.projectkapp1.data.entity.JwtResponse
import com.madhu.projectkapp1.data.entity.NameAndId
import com.madhu.projectkapp1.data.entity.Village
import com.madhu.projectkapp1.data.entity.VillageWiseResponse
import com.madhu.projectkapp1.ui.theme.Purple40
import com.madhu.projectkapp1.ui.theme.acmeFontFamily
import com.madhu.projectkapp1.ui.viewmodel.UploadImageViewModel
import com.madhu.projectkapp1.ui.viewmodel.VillageViewModel
import com.madhu.projectkapp1.utility.IdType
import com.madhu.projectkapp1.utility.ResourceState
import kotlinx.coroutines.CoroutineScope


@Composable
fun DisplayVillage(
    village: Village,
    modifier: Modifier = Modifier,
    villageImageUrl: String? = null,
    backgoundColor: Color = Color.LightGray,
    showTitle: Boolean = false,
    showVillageImage: Boolean = false
) {
    Log.d("DisplayVillage", "Inside Display Village ")


    val villageName = village.villageName
    val mandal = village.mandal
    val district = village.district
    val state = village.state
    val pincode = village.pincode
    val amountGoal = village.amountGoal
    val productGoal = village.productGoal

    var showCard by remember { mutableStateOf(false) }


    Card(
        modifier = modifier
            .fillMaxSize()
            .padding(8.dp)
            .animateContentSize()
            .clickable {
                showCard = !showCard
            },
        colors = CardDefaults.cardColors(
            containerColor = backgoundColor
        ),
        elevation = CardDefaults.elevatedCardElevation()
    ) {

        if (showVillageImage)
            CoilImage(
                imageUrl = villageImageUrl,
                defaultPainter = painterResource(id = R.drawable.default_village),
                size = 154.dp,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
            )


        if (showTitle)
            DisplayTitle(title = villageName)

        if (showCard) {

            Column(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {


                if (mandal != null && mandal != "")
                    SideBySideText(key = "Mandal", value = mandal)
                if (district != null && district != "")
                    SideBySideText(key = "District", value = district)
                if (state != null && state != "")
                    SideBySideText(key = "State", value = state)
                if (pincode != null && pincode != 0)
                    SideBySideText(key = "Pin code", value = pincode.toString())
                if (amountGoal!=null && amountGoal != 0)
                    SideBySideText(key = "Amount Goal", value = amountGoal.toString())
                if (productGoal!=null && productGoal != 0)
                    SideBySideText(key = "Product Goal", value = productGoal.toString())

            }
        }

    }
}


@Composable
fun VillageResponseModelDetail(
    villageResponseModel: VillageWiseResponse,
    navController: NavHostController,
    scope: CoroutineScope,
    snackbarHostState: SnackbarHostState
) {


    val village = villageResponseModel.village
    val activeCustomers = villageResponseModel.activeCustomers
    val activeRecords = villageResponseModel.activeRecords


    val goalStatus = villageResponseModel.goalStatus
    val pendingAmount = villageResponseModel.pendingAmount
    val collectedAmount = villageResponseModel.collectedAmount

    val totalActiveRecords = villageResponseModel.totalActiveRecords
    val totalProductSellCount = villageResponseModel.totalProductSellCount

    val totalAmountFromVillage = villageResponseModel.totalAmountFromVillage
    val totalActiveCustomers = villageResponseModel.totalActiveCustomers
    val totalCustomersCount = villageResponseModel.totalCustomersCount
    val totalRecordsCount = villageResponseModel.totalRecordsCount
    val completedRecords = villageResponseModel.completedRecords
    val imageUrl = village.imageUrl

    val map = mutableMapOf<String, String>()

    map["goalStatus"] = goalStatus
    map["totalProductSellCount"] = totalProductSellCount.toString()
    map["pendingAmount"] = pendingAmount.toString()
    map["collectedAmount"] = collectedAmount.toString()
    map["totalActiveRecords"] = totalActiveRecords.toString()
    map["totalAmountFromVillage"] = totalAmountFromVillage.toString()
    map["totalActiveCustomers"] = totalActiveCustomers.toString()
    map["totalCustomersCount"] = totalCustomersCount.toString()
    map["totalRecordsCount"] = totalRecordsCount.toString()
    map["completedRecords"] = completedRecords.toString()

    LazyColumn(
        modifier = Modifier
            .fillMaxSize(),
        contentPadding = PaddingValues(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            DisplayVillage(
                village = village,
                backgoundColor = MaterialTheme.colorScheme.tertiaryContainer,
                showVillageImage = true,
                showTitle = true,
                villageImageUrl = imageUrl
            )
        }

        item {
            DisplayMapElements(
                title = "Village Statistics",
                map = map,
                backgoundColor = MaterialTheme.colorScheme.secondaryContainer
            )
        }

        items(activeCustomers) {
            DisplayCustomer(
                customer = it,
                showTitle = true
            )
        }

        items(activeRecords) {
            DisplayRecord(
                record = it,
                recordStatus = it.dueAmount > 0,
                showRecordImage = true
            )
        }
        if (totalProductSellCount == 0 && totalCustomersCount == 0) {
            item {
                DeleteConfirmationScreen(
                    id = village.villageId!!,
                    idType = IdType.VILLAGE_ID,
                    navController = navController,
                    scope = scope,
                    snackbarHostState = snackbarHostState
                )
            }
        }

    }

}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddVillage(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    snackbarHostState: SnackbarHostState,
    scope: CoroutineScope,
    villageViewModel: VillageViewModel = hiltViewModel(),
    uploadImageViewModel: UploadImageViewModel = hiltViewModel()
) {

    val tag = "AddVillage"


    var villageName by remember { mutableStateOf("") }
    var mandal by remember { mutableStateOf("") }
    var district by remember { mutableStateOf("") }
    var state by remember { mutableStateOf("Telangana") }
    var pincode by remember { mutableStateOf("") }
    var productGoal by remember { mutableStateOf("") }
    var amountGoal by remember { mutableStateOf("") }
    var pickedImageUri by remember { mutableStateOf<Uri?>(null) }


    var isFieldsFilled by remember { mutableStateOf(false) }

    isFieldsFilled = villageName != "" && mandal != ""


    val imageResponse by uploadImageViewModel.uploadResponse.collectAsState()

    var imageUrl by remember { mutableStateOf("") }

    val context = LocalContext.current
    val focusManager = LocalFocusManager.current

    var isInProgress by remember { mutableStateOf(false) }

    var buttonText by remember {
        mutableStateOf("Add Village")
    }


    LaunchedEffect(imageResponse) {

        when (imageResponse) {
            is ResourceState.Success -> {
                imageUrl = (imageResponse as ResourceState.Success<JwtResponse>).data.jwtToken
            }

            else -> {}
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


        CommonOutlineTextField(
            value = villageName,
            onValueChange = { villageName = it },
            label = "Village Name *",
            focusManager = focusManager,
            isMandatory = true,
            description = "Village Name"
        )


        CommonOutlineTextField(
            value = mandal,
            onValueChange = { mandal = it },
            label = "Mandal *",
            focusManager = focusManager,
            description = "Mandal"
        )


        CommonOutlineTextField(
            value = district,
            onValueChange = { district = it },
            label = "District *",
            focusManager = focusManager,
            description = "District"
        )

        CommonOutlineTextField(
            value = state,
            onValueChange = { state = it },
            label = "State",
            focusManager = focusManager,
            description = "State"
        )

        ImageUploadField(
            defaultPainter = painterResource(id = R.drawable.default_village),
            contentDescription = "Village Image"
        ) {
            pickedImageUri = it
        }

        CommonOutlineTextField(
            value = pincode,
            onValueChange = {
                if (it.length <= 6) {
                    pincode = it.filter { char -> char.isDigit() }
                }
            },
            label = "Pincode",
            focusManager = focusManager,
            isNumber = true,
            description = "Pincode"
        )


        CommonOutlineTextField(
            value = productGoal,
            onValueChange = { productGoal = it.filter { char -> char.isDigit() } },
            label = "Product Goal",
            focusManager = focusManager,
            isNumber = true,
            description = "Product Goal"
        )


        OutlinedTextField(
            value = amountGoal,
            onValueChange = { amountGoal = it.filter { char -> char.isDigit() } },
            label = { Text("Amount Goal") },
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Done,
                keyboardType = KeyboardType.Number
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    focusManager.clearFocus(true)
                }
            ),
            modifier = Modifier.semantics {
                contentDescription = "Amount Goal"
            }
        )


        val villageDto = Village(
            villageName = villageName,
            mandal = mandal,
            district = district,
            state = state,
            pincode = pincode.toIntOrNull() ?: 0,
            productGoal = productGoal.toIntOrNull() ?: 0,
            amountGoal = amountGoal.toIntOrNull() ?: 0,
        )



        if (imageUrl != "" && isInProgress) {
            villageDto.imageUrl = imageUrl
            villageViewModel.addVillage(villageDto)
            showSnackBar(
                scope = scope,
                snackbarHostState = snackbarHostState,
                navController = navController,
                message = "Village Added"
            )

            isInProgress = false

        }

        ElevatedButton(
            enabled = isFieldsFilled && !isInProgress,
            onClick = {
                Log.d(tag, "The pickedImage Uri is $pickedImageUri")
                buttonText = "Adding ..."
                if (pickedImageUri != null) {
                    if (!isInProgress)
                        isInProgress = true
                    uploadImageViewModel.uploadImageToCloudinary(
                        context = context,
                        uri = pickedImageUri!!
                    )
                } else {
                    Log.d(tag, "Uri Not Selected")
                    villageViewModel.addVillage(villageDto)
                    showSnackBar(
                        scope = scope,
                        snackbarHostState = snackbarHostState,
                        navController = navController,
                        message = "Village Added"
                    )
                }
                buttonText = "Added"
            },
            shape = RoundedCornerShape(topStart = 15.dp, bottomEnd = 15.dp),
            modifier = Modifier
                .padding(8.dp)
                .height(52.dp)
                .semantics {
                    contentDescription =
                        "Create a new village entry. Provide essential details to keep track of locations associated with your records."
                },
            colors = ButtonDefaults.elevatedButtonColors(containerColor = Purple40)
        ) {
            Text(
                text = buttonText,
                fontSize = 24.sp,
                color = if (isFieldsFilled) Color.White else Color.Black,
                fontFamily = acmeFontFamily,
                fontWeight = FontWeight.Light
            )
        }

    }

}


@Composable
fun HandleVillageDropDown(
    selectedOption: String,
    onSelectedOptionChange: (String) -> Unit,
    onSelectedOptionIdChange: (Int) -> Unit,
    villageViewModel: VillageViewModel = hiltViewModel()
) {
    val tag = "HandleVillageDropDown"

    Log.d(tag, "Inside Village Handle")
    LaunchedEffect(true) {
        villageViewModel.getVillagesNames()
    }

    val villageResponse by villageViewModel.villagesNames.collectAsState()


    when (villageResponse) {
        is ResourceState.Loading -> {
            Log.d(tag, "Loading Product Name Data")
        }

        is ResourceState.Success -> {

            Log.d(tag, "Village Names  Fetched Success ")
            val villageNames =
                (villageResponse as ResourceState.Success<List<NameAndId>>).data



            DropdownMenuExample(
                options = villageNames,
                selectedOption = selectedOption,
                onSelectedOptionChange = onSelectedOptionChange,
                onSelectedOptionIdChange = onSelectedOptionIdChange
            )


        }

        is ResourceState.Error -> {
            Log.d(tag, "Failed To Fetch Villages Names Data")
            Text(text = "No Villages Created", color = Color.Red)
        }
    }


}
