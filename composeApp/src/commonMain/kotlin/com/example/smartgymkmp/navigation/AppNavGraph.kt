package com.example.smartgymkmp.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.smartgymkmp.presentation.login.LoginViewModel
import com.example.smartgymkmp.ui.dashboard.DashboardScreen
import com.example.smartgymkmp.ui.login.LoginScreen
import com.example.smartgymkmp.viewmodels.MemberViewModel
import org.koin.compose.koinInject

@Composable
fun AppNavGraph(
    navController: NavHostController,
    routine: MutableState<Map<String, String>>,
) {
    val loginViewModel: LoginViewModel = koinInject()
    val memberViewModel: MemberViewModel = koinInject()

    NavHost(
        navController = navController,
        startDestination = "login"
    ) {
        // Login screen
        composable("login") {
            LoginScreen(
                viewModel = loginViewModel,
                onNavigateToDashboard = {
                    navController.navigate("dashboard") {
                        popUpTo("login") { inclusive = true }
                    }
                }
            )
        }
        val dummyRoutine = mapOf(
            "Monday" to "Chest",
            "Tuesday" to "Back",
            "Wednesday" to "Legs",
            "Thursday" to "Shoulders",
            "Friday" to "Arms",
            "Saturday" to "Core",
            "Sunday" to "Rest"
        )

        composable("dashboard") {
            DashboardScreen(viewModel = memberViewModel)
        }

        composable("memberProfile/{memberId}") { backStackEntry ->
            // Your existing code
        }

        composable("workoutRoutine") {
            // Your existing code
        }
    }
}

