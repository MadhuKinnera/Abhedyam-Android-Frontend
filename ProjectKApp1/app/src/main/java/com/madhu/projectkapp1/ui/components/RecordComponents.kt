package com.madhu.projectkapp1.ui.components

import android.util.Log
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.madhu.projectkapp1.R
import com.madhu.projectkapp1.data.entity.RecordDto
import com.madhu.projectkapp1.data.entity.RecordResponseModel
import com.madhu.projectkapp1.data.entity.SaleRecord
import com.madhu.projectkapp1.data.enums.Occassion
import com.madhu.projectkapp1.ui.theme.Purple40
import com.madhu.projectkapp1.ui.theme.acmeFontFamily
import com.madhu.projectkapp1.ui.viewmodel.RecordViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch


@Composable
fun DisplayRecord(
    record: SaleRecord,
    recordStatus: Boolean = true,
    modifier: Modifier = Modifier,
    activeColor: Color = Color.Magenta,
    inActiveColor: Color = Color.LightGray,
    backgoundColor: Color = Color.LightGray,
    showContent: Boolean = false,
    showRecordImage: Boolean = false
) {

    val product = record.product
    val transactions = record.transactions
    val recordId = record.recordId
    val endDate = record.endDate
    val occassion = record.occasion
    val description = record.description
    val quantity = record.quantity
    val dueAmount = record.dueAmount
    val startDate = record.startDate
    val timestamp = record.timestamp
    val totalAmount = record.totalAmount

    Log.d("DisplayRecord","total Amount is $totalAmount")

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

        if (showRecordImage) {
            CoilImage(
                imageUrl = record.product.imageUrl,
                defaultPainter = painterResource(id = R.drawable.default_record),
                modifier = Modifier.align(Alignment.CenterHorizontally),
                showBorder = dueAmount!=0
            )
        }
        DisplayTitle(title = "Record Details : $recordId")

        if (showCard) {

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                DisplayProduct(
                    product = product,
                    backgoundColor = MaterialTheme.colorScheme.primaryContainer,
                    modifier = Modifier.fillMaxWidth(),
                    showProductImage = false,
                    showTitle = true,
                    showBoughtPrice = false
                )

                Spacer(modifier = Modifier.height(12.dp))

                val rightColor = if (recordStatus) activeColor else inActiveColor

                SideBySideText(key = "Record Id", value = recordId.toString())
                if(description!=null && description!="") {
                    SideBySideText(
                        key = "R Description",
                        value = description.toString(),
                        rightColor = rightColor
                    )
                }
                SideBySideText(key = "Start Date", value = startDate, rightColor = rightColor)
                endDate?.let {
                    SideBySideText(key = "End Date", value = it, rightColor = rightColor)
                }
                SideBySideText(key = "Occassion", value = occassion, rightColor = rightColor)
                SideBySideText(
                    key = "Due Amount",
                    value = dueAmount.toString(),
                    rightColor = rightColor
                )
                SideBySideText(
                    key = "Bought Price",
                    value = totalAmount.toString(),
                    rightColor = rightColor
                )
                SideBySideText(
                    key = "Quantity",
                    value = quantity.toString(),
                    rightColor = rightColor
                )
                SideBySideText(
                    key = "Timestamp",
                    value = timestamp.substring(0, 19),
                    rightColor = rightColor
                )


                transactions.forEach { transaction ->
                    DisplayTransaction(
                        transaction = transaction,
                        backgoundColor = MaterialTheme.colorScheme.secondaryContainer
                    )
                }


            }
        }
    }


}


@Composable
fun RecordResponseModelDetail(recordResponseModel: RecordResponseModel) {


    val record = recordResponseModel.saleRecord
    val customer = recordResponseModel.customer
    val village = recordResponseModel.village


    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(4.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {


        item {
            DisplayRecord(
                record = record,
                showContent = true,
                showRecordImage = true
            )
        }

        item {
            DisplayCustomer(
                customer = customer,
                backgoundColor = MaterialTheme.colorScheme.errorContainer,
                showTitle = true
            )
        }

        item {
            DisplayVillage(
                village = village,
                backgoundColor = MaterialTheme.colorScheme.primaryContainer,
            )
        }


    }

}


@Composable
fun AddRecord(
    modifier: Modifier = Modifier,
    navController: NavController,
    scope: CoroutineScope,
    snackbarHostState: SnackbarHostState,
    recordViewModel: RecordViewModel = hiltViewModel()
) {
    var description by remember { mutableStateOf("") }
    var occasion by remember { mutableStateOf(Occassion.SANKRANTHI_24) }
    var quantity by remember { mutableStateOf("") }
    var totalAmount by remember { mutableStateOf("") }
    var productName by remember { mutableStateOf("Select Product") }
    var productId by remember { mutableIntStateOf(0) }
    var customerId by remember { mutableIntStateOf(0) }
    var customerName by remember { mutableStateOf("Select Customer") }


    var isFieldsFilled by remember { mutableStateOf(false) }

    isFieldsFilled =
        !customerName.contentEquals("Select Customer") && !productName.contentEquals("Select Product")

    val focusManager = LocalFocusManager.current

    val tag = "AddRecord"


    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        HandleCustomerDropDown(
            selectedOption = customerName,
            onSelectedOptionChange = { customerName = it },
            onSelectedOptionIdChange = { customerId = it })

        HandleProductDropDown(
            selectedOption = productName,
            onSelectedOptionChange = { productName = it },
            onSelectedOptionIdChange = { productId = it }
        )
        HandleOccassionDropDown(
            selectedOption = occasion.toString(),
            onSelectedOptionChange = {
                occasion = Occassion.valueOf(it)
            }
        )


        CommonOutlineTextField(
            value = description,
            onValueChange = { description = it },
            label = "Description",
            focusManager = focusManager,
            description = "Description"
        )


        CommonOutlineTextField(
            value = quantity,
            onValueChange = { quantity = it.filter { char -> char.isDigit() } },
            label = "Quantity",
            focusManager = focusManager,
            isNumber = true,
            description = "Quantity"
        )

        CommonOutlineTextField(
            value = totalAmount,
            onValueChange = { totalAmount = it.filter { char -> char.isDigit() } },
            label = "Total Amount",
            focusManager = focusManager,
            isNumber = true,
            imeAction = ImeAction.Done,
            description = "Total Amount"
        )



        Log.d(tag, "The Customer Name is $customerName")
        Log.d(tag, "The Customer Id is $customerId")
        Log.d(tag, "The Product Name is $productName")
        Log.d(tag, "The Product Id is $productId")

        Spacer(modifier = Modifier.height(16.dp))



        ElevatedButton(
            enabled = isFieldsFilled,
            onClick = {

                val recordDto = RecordDto(
                    customerId = customerId,
                    description = description,
                    occasion = occasion,
                    productId = productId,
                    quantity = quantity.toIntOrNull() ?: 0,
                    totalAmount = totalAmount.toIntOrNull() ?: 0
                )

                recordViewModel.addRecord(recordDto)

                scope.launch {
                    val response = snackbarHostState.showSnackbar(
                        message = "Record Added ",
                        actionLabel = "Done",
                        duration = SnackbarDuration.Long
                    )

                    when (response) {
                        SnackbarResult.ActionPerformed -> {
                            navController.popBackStack()
                        }

                        else -> {}
                    }

                }

            },
            shape = RoundedCornerShape(topStart = 15.dp, bottomEnd = 15.dp),
            modifier = Modifier
                .padding(8.dp)
                .height(52.dp)
                .semantics {
                    contentDescription =
                        "Record a new sale with this feature. Capture transaction details and maintain a comprehensive sales history."
                },
            colors = ButtonDefaults.elevatedButtonColors(containerColor = Purple40)
        ) {
            Text(
                "Add Record",
                fontSize = 24.sp,
                color = if (isFieldsFilled) Color.White else Color.Black,
                fontFamily = acmeFontFamily,
                fontWeight = FontWeight.Light
            )
        }
    }
}
