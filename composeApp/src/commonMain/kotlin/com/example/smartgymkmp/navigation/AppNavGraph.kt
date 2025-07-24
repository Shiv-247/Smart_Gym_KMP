package com.example.smartgymkmp.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.smartgymkmp.ui.dashboard.DashboardScreen

@Composable
fun AppNavGraph(
    navController: NavHostController,
    routine: MutableState<Map<String, String>>,
) {
//    val localContext = LocalContext.current
//    val paymentStatus = remember {
//        mutableStateOf(
//            PaymentStatus(
//                isDue = true,
//                amount = 500,
//                planName = "Monthly",
//                dueDate = LocalDate.of(2025, 8, 1)
//            )
//        )
//    }

    NavHost(navController = navController, startDestination = "dashboard") {
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
            DashboardScreen(
                navController = navController,
                routine = dummyRoutine,
//                paymentStatus = PaymentStatus(
//                    isDue = paymentViewModel.lastPayment.value == null,
//                    amount = 500,
//                    planName = "Monthly",
//                    dueDate = LocalDate.of(2025, 8, 1)
//                ),
//                razorpayLauncher = razorpayLauncher, // 👈 Pass here
//                paymentViewModel = paymentViewModel // 👈 Pass here
            )
        }




        composable("memberManagement") {
//            MemberManagementScreen(navController)
        }

        composable("memberProfile/{memberId}") { backStackEntry ->
//            val memberId = backStackEntry.arguments?.getString("memberId") ?: ""
//            MemberProfileScreen(navController, memberId = memberId)
        }

        composable("workoutRoutine") {
//            WorkoutRoutineScreen { routine ->
//                viewModel.saveRoutine(routine)
//                navController.popBackStack()
//            }
        }

    }
}

