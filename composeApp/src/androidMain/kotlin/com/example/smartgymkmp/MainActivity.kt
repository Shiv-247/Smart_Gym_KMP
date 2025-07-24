package com.example.smartgymkmp

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.smartgymkmp.navigation.AppNavGraph
import com.google.firebase.FirebaseApp

class MainActivity : ComponentActivity() {
    //    lateinit var razorpayLauncher: RazorpayLauncher
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        razorpayLauncher = RazorpayLauncher(this)

        FirebaseApp.initializeApp(this)

        setContent {
            SmartGymApp()
        }
    }

//    override fun onPaymentSuccess(razorpayPaymentId: String?) {
//        razorpayLauncher.onSuccess?.invoke()
//    }

//    override fun onPaymentError(code: Int, response: String?) {
//        razorpayLauncher.onFailure?.invoke(response ?: "Unknown error")
//    }

}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun SmartGymApp() {
    val navController = rememberNavController()
//    val paymentViewModel: PaymentViewModel = viewModel(
//        factory = ViewModelProvider.AndroidViewModelFactory(LocalContext.current.applicationContext as Application)
//    )

//    val viewModel: RoutineViewModel = viewModel(factory = ViewModelProvider.AndroidViewModelFactory(LocalContext.current.applicationContext as Application))

    LaunchedEffect(Unit) {
//        viewModel.loadRoutine()
    }

    val currentDestination = navController
        .currentBackStackEntryAsState().value?.destination?.route

    val routine = rememberSaveable { mutableStateOf<Map<String, String>>(emptyMap()) }

    Scaffold(
        bottomBar = {
            NavigationBar {
                NavigationBarItem(
                    selected = currentDestination == "dashboard",
                    onClick = { navController.navigate("dashboard") },
                    icon = { Icon(Icons.Default.Home, contentDescription = "Dashboard") },
                    label = { Text("Dashboard") }
                )
                NavigationBarItem(
                    selected = currentDestination == "workoutRoutine",
                    onClick = { navController.navigate("workoutRoutine") },
                    icon = { Icon(Icons.Default.DateRange, contentDescription = "Routine") },
                    label = { Text("My Routine") }
                )
            }
        }
    ) { padding ->
        Box(modifier = Modifier.padding(padding)) {
            AppNavGraph(
                navController = navController,
                routine = routine,
            )
        }
    }
}



