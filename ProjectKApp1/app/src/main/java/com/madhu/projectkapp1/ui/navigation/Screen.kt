package com.madhu.projectkapp1.ui.navigation

sealed class Screen(val route: String) {

    object Customers : Screen("customers_screen")
    object CustomerDetail : Screen("customer_detail_screen/{customerId}")
    object Products : Screen("products_screen")
    object ProductDetail : Screen("product_detail_screen/{productId}")
    object Records : Screen("records_screen")
    object RecordDetail : Screen("record_detail_screen/{recordId}")
    object Villages : Screen("villages_screen")
    object VillageDetail : Screen("village_detail_screen/{villageId}")
    object AddCustomer : Screen("add_customer_screen")
    object AddVillage : Screen("add_village_screen")
    object AddProduct : Screen("add_product_screen")
    object AddRecord : Screen("add_record_screen")
    object DisplayCustomersNames : Screen("display_customer_names_screen")
    object Login : Screen("login_screen")
    object LoginResponse : Screen("login_response_screen/{email}/{password}")
    object CreateUser : Screen("create_user_screen")
    object CustomerLogin : Screen("customer_login")
    object CustomerInfo : Screen("customer_info/{customerCode}")

}


const val CUSTOMER_ID = "customerId"
const val PRODUCT_ID = "productId"
const val RECORD_ID = "recordId"
const val VILLAGE_ID = "villageId"
const val CUSTOMER_CODE = "customerCode"


const val EMAIL = "email"
const val PASSWORD = "password"


const val CUSTOMER_ROUTE = "customer"
const val PRODUCT_ROUTE = "product"
const val RECORD_ROUTE = "record"
const val VILLAGE_ROUTE = "village"
const val AUTHENTICATION_ROUTE = "authentication"


