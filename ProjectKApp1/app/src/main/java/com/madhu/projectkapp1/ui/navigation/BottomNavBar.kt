package com.madhu.projectkapp1.ui.navigation

import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.madhu.projectkapp1.R


@Composable
fun BottomNavBar(
    navController: NavHostController,
    selectedItem: Screen
) {
    NavigationBar {
        NavigationBarItem(
            selected = selectedItem == Screen.Customers,
            onClick = {
                navController.navigate(Screen.Customers.route)
            },
            label = {
                    Text(
                        text = "Customers",
                        style = MaterialTheme.typography.titleMedium
                    )

            },
            icon = {
                Icon(
                    painter = painterResource(id = R.drawable.customer),
                    contentDescription = "Customers",
                    modifier = Modifier.size(24.dp)
                )
            }
        )
        NavigationBarItem(
            selected = selectedItem == Screen.Products,
            onClick = {
                navController.navigate(Screen.Products.route)
            },
            label = {
                    Text(
                        text = "Products",
                        style = MaterialTheme.typography.titleMedium
                    )

            },
            icon = {
                Icon(
                    painter = painterResource(id = R.drawable.product),
                    contentDescription = "Products",
                    modifier = Modifier.size(24.dp)
                )

            }
        )

        NavigationBarItem(
            selected = selectedItem == Screen.Records,
            onClick = {
                navController.navigate(Screen.Records.route)
            },

            label = {
                    Text(
                        text = "Records",
                        style = MaterialTheme.typography.titleMedium
                    )

            },
            icon = {
                Icon(
                    painter = painterResource(id = R.drawable.orders),
                    contentDescription = "Records",
                    modifier = Modifier.size(24.dp)
                )
            }
        )
        NavigationBarItem(
            selected = selectedItem == Screen.Villages,
            onClick = {
                navController.navigate(Screen.Villages.route)
            },
            label = {

                    Text(
                        text = "Villages",
                        style = MaterialTheme.typography.titleMedium
                    )

            },
            icon = {
                Icon(
                    painter = painterResource(id = R.drawable.village),
                    contentDescription = "Villages",
                    modifier = Modifier.size(24.dp)
                )
            }
        )
    }

}