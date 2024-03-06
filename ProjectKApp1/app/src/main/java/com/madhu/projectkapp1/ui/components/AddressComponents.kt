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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.madhu.projectkapp1.data.entity.Address

@Composable
fun DisplayAddress(
    address: Address,
    modifier: Modifier = Modifier,
    backgoundColor: Color = Color.LightGray,
) {
    Log.d("DisplayAddress", "Inside Display Address ")


    val street = address.street
    val landmark = address.landmark
    val description = address.description

    val village = address.village

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


        Column(
            modifier = modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            DisplayVillage(
                village = village,
                showTitle = true,
                backgoundColor = MaterialTheme.colorScheme.secondaryContainer
            )
            Spacer(
                modifier = Modifier
                    .height(8.dp)
            )

            DisplayTitle(title = "Address")

            if (showCard) {
                Divider(thickness = 8.dp)

                if (street!=null && street != "")
                    SideBySideText(key = "Street", value = street)

                if (landmark!=null && landmark != "")
                    SideBySideText(key = "LandMark", value = landmark)

                if (description!=null && description != "")
                    SideBySideText(key = "Description", value = description)

            }
        }

    }


}
