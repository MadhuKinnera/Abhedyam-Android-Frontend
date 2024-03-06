package com.madhu.projectkapp1

import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.madhu.projectkapp1.ui.navigation.SetUpNavGraph
import com.madhu.projectkapp1.ui.theme.ProjectKApp1Theme
import com.madhu.projectkapp1.ui.viewmodel.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    val tag = "MainActivity"
    private val loginViewModel: LoginViewModel by viewModels()

    lateinit var navController: NavHostController

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen().apply {
            setKeepOnScreenCondition {
                Log.d(tag, "The is ready value is ${loginViewModel.isReady.value}")
                !loginViewModel.isReady.value
            }
        }
        setContent {
            ProjectKApp1Theme {
                navController = rememberNavController()
                SetUpNavGraph(navController = navController)
            }
        }
    }

}
