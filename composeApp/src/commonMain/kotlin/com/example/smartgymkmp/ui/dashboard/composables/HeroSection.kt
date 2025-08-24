package com.example.smartgymkmp.ui.dashboard.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.smartgymkmp.domain.model.ActivityLevel
import com.example.smartgymkmp.viewmodels.MemberViewModel
import kotlin.compareTo
import kotlin.text.toInt

@Composable
fun HeroSection(
    viewModel: MemberViewModel,
    memberId: String = "1"
) {
    var selectedMood by remember { mutableStateOf(2) }
    val moods = listOf("😴", "😐", "😊", "💪", "🔥")
    val calories by viewModel.calories.collectAsState()

    val activityLevel = remember { ActivityLevel.MODERATE }

    LaunchedEffect(memberId) {
        viewModel.ensureMemberExists(memberId)
        viewModel.calculateCalories(memberId, activityLevel)
    }


    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            // Greeting
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        "Good morning, Alex",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        "Let's crush today's workout!",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                Box(
                    modifier = Modifier
                        .size(50.dp)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.primaryContainer)
                ) {
                    Icon(
                        Icons.Default.Person,
                        contentDescription = "Profile",
                        modifier = Modifier
                            .size(30.dp)
                            .align(Alignment.Center),
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
            }

            Spacer(Modifier.height(10.dp))

            // Daily goal rings
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                GoalRing(
                    title = "Calories",
                    current = calories.tdee.let { if (it.isNaN() || it <= 0) 0 else it.toInt() },
                    target = calories.tdee.let { if (it.isNaN() || it <= 0) 2000 else (it * 1.1).toInt() },
                    color = Color(0xFF4CAF50)
                )
                GoalRing(
                    title = "Steps",
                    current = 6240,
                    target = 10000,
                    color = Color(0xFF2196F3)
                )
                GoalRing(
                    title = "Water",
                    current = 4,
                    target = 8,
                    color = Color(0xFF03A9F4)
                )
            }

            // Mood Selector
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    "How are you feeling today?",
                    style = MaterialTheme.typography.bodyMedium
                )
                Spacer(modifier = Modifier.width(8.dp))
                LazyRow(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    items(moods.size) { index ->
                        Box(
                            modifier = Modifier
                                .padding(4.dp)
                                .clip(CircleShape)
                                .background(
                                    if (selectedMood == index)
                                        MaterialTheme.colorScheme.primaryContainer
                                    else
                                        Color.Transparent
                                )
                                .clickable { selectedMood = index }
                                .padding(8.dp)
                        ) {
                            Text(
                                text = moods[index],
                                fontSize = 24.sp
                            )
                        }
                    }
                }
            }

            if (selectedMood > 2) {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    "AI Suggestion: You're feeling energetic! Try increasing your workout intensity by 10% today.",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}
