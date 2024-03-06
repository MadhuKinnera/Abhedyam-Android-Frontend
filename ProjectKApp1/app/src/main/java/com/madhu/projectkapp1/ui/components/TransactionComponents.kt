package com.madhu.projectkapp1.ui.components

import android.util.Log
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.madhu.projectkapp1.data.entity.Transaction
import com.madhu.projectkapp1.data.entity.TransactionDto
import com.madhu.projectkapp1.ui.viewmodel.TransactionViewModel


@Composable
fun DisplayTransaction(
    transaction: Transaction,
    modifier: Modifier = Modifier,
    backgoundColor: Color = Color.LightGray,
) {

    val transactionId = transaction.transactionId
    val timestamp = transaction.timestamp
    val amount = transaction.amount
    val paymentMode = transaction.modeOfPayment
    val transactionDescription = transaction.description

    var showCard by remember { mutableStateOf(false) }

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

        DisplayTitle("Amount : $amount")

        if (showCard) {

            Column(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                SideBySideText(key = "Transaction Id", value = transactionId.toString())
                SideBySideText(
                    key = "TimeStamp",
                    value = timestamp.substring(0, 19),
                    rightFontSize = 12.sp
                )

                if (paymentMode != null && paymentMode != "")
                    SideBySideText(key = "Payment Mode", value = paymentMode)


                if (transactionDescription != null && transactionDescription != "")
                    SideBySideText(key = "Description", value = transactionDescription)


            }
        }

    }
}


@Composable
fun AddTransaction(
    showAlert: Boolean,
    recordId: Int,
    onShowAlertClick: (Boolean) -> Unit,
    transactionViewModel: TransactionViewModel = hiltViewModel()
) {
    var amount by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var paymentMode by remember { mutableStateOf("Payment Mode ") }

    val tag = "AddTransaction"

    if (showAlert) {
        AlertWithInputValues(
            onDismiss = { onShowAlertClick(false) },
            onConfirm = {

                val transactionDto =
                    TransactionDto(
                        amount.toIntOrNull() ?: 0,
                        description.ifEmpty { null },
                        paymentMode,
                        recordId
                    )
                Log.d(tag, "Adding Transaction for $transactionDto")
                transactionViewModel.addTransaction(transactionDto)
                onShowAlertClick(false)
            },
            amount = amount,
            onAmountChange = { amount = it },
            description = description,
            onDescriptionChange = { description = it },
            mode = paymentMode,
            onModeChange = { paymentMode = it }
        )
    }

}