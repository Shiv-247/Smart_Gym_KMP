package com.example.smartgymkmp.ui.dashboard

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.smartgymkmp.ui.dashboard.composables.HeroSection
import com.example.smartgymkmp.ui.dashboard.composables.LifestyleSection
import com.example.smartgymkmp.ui.dashboard.composables.MotivationSection
import com.example.smartgymkmp.ui.dashboard.composables.WorkoutHubSection
import com.example.smartgymkmp.viewmodels.MemberViewModel
import kotlinx.coroutines.launch
import kotlin.compareTo

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(viewModel: MemberViewModel) {
    val scrollState = rememberScrollState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("SmartGym Dashboard") },
                actions = {
                    IconButton(onClick = { /* TODO */ }) {
                        Icon(Icons.Default.Notifications, contentDescription = "Notifications")
                    }
                    IconButton(onClick = { /* TODO */ }) {
                        Icon(Icons.Default.Settings, contentDescription = "Settings")
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { /* TODO */ },
                containerColor = MaterialTheme.colorScheme.primary
            ) {
                Icon(Icons.Default.Add, contentDescription = "Quick Actions")
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(scrollState)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            // Top Section: Hero / Quick Info
            HeroSection(viewModel)

            // Middle Section: Smart Workout Hub
            WorkoutHubSection()

            // Bottom Section: Lifestyle & Metrics
            LifestyleSection()

            // Motivation / Social
            MotivationSection()
        }
    }
}

// Data models for dummy data
data class Exercise(
    val name: String,
    val description: String,
    val sets: String,
    val reps: String,
    val difficulty: String,
    val icon: ImageVector
)

data class Achievement(
    val title: String,
    val description: String,
    val icon: String,
    val color: Color
)

// Dummy data
val dummyExercises = listOf(
    Exercise(
        name = "Bench Press",
        description = "Chest compound movement",
        sets = "4 sets",
        reps = "8-10 reps",
        difficulty = "Medium",
        icon = Icons.Default.FitnessCenter
    ),
    Exercise(
        name = "Pull-Ups",
        description = "Back & biceps",
        sets = "3 sets",
        reps = "8-12 reps",
        difficulty = "Hard",
        icon = Icons.Default.FitnessCenter
    ),
    Exercise(
        name = "Shoulder Press",
        description = "Deltoid isolation",
        sets = "3 sets",
        reps = "10-12 reps",
        difficulty = "Medium",
        icon = Icons.Default.FitnessCenter
    ),
    Exercise(
        name = "Squats",
        description = "Lower body compound",
        sets = "4 sets",
        reps = "8-10 reps",
        difficulty = "Hard",
        icon = Icons.Default.FitnessCenter
    )
)

val dummyAchievements = listOf(
    Achievement(
        title = "Consistency King",
        description = "Worked out 30 days in a row",
        icon = "👑",
        color = Color(0xFFFF9800)
    ),
    Achievement(
        title = "Strength Master",
        description = "Increased weights by 20%",
        icon = "💪",
        color = Color(0xFF4CAF50)
    ),
    Achievement(
        title = "Early Bird",
        description = "5 morning workouts completed",
        icon = "🌅",
        color = Color(0xFF2196F3)
    )
)
