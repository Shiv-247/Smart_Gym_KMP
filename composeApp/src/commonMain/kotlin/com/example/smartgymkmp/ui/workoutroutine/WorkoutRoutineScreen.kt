//package com.example.smartgymkmp.ui.workoutroutine
//
//import androidx.annotation.RequiresApi
//import androidx.compose.animation.AnimatedVisibility
//import androidx.compose.animation.core.LinearEasing
//import androidx.compose.animation.core.animateFloat
//import androidx.compose.animation.core.infiniteRepeatable
//import androidx.compose.animation.core.rememberInfiniteTransition
//import androidx.compose.animation.core.tween
//import androidx.compose.animation.fadeIn
//import androidx.compose.animation.slideInVertically
//import androidx.compose.foundation.Canvas
//import androidx.compose.foundation.background
//import androidx.compose.foundation.layout.*
//import androidx.compose.foundation.lazy.LazyColumn
//import androidx.compose.foundation.lazy.itemsIndexed
//import androidx.compose.foundation.rememberScrollState
//import androidx.compose.foundation.shape.RoundedCornerShape
//import androidx.compose.foundation.verticalScroll
//import androidx.compose.material3.*
//import androidx.compose.runtime.*
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.draw.shadow
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.text.font.FontWeight
//import androidx.compose.ui.unit.dp
//import androidx.compose.foundation.layout.FlowRow
//import androidx.compose.foundation.layout.ExperimentalLayoutApi
//import androidx.compose.ui.geometry.Offset
//import androidx.compose.ui.graphics.drawscope.Stroke
//import kotlin.math.PI
//import kotlin.math.sin
//
//@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
//@Composable
//fun WorkoutRoutineScreen(
//    modifier: Modifier = Modifier,
//    onSave: (Map<String, String>) -> Unit
//) {
//    val daysOfWeek = listOf("Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday")
//    val bodyParts = listOf("Chest", "Back", "Legs", "Arms", "Shoulders", "Abs", "Rest")
//
//    val selectedRoutine = remember { mutableStateMapOf<String, String>() }
//    val isVisible = remember { mutableStateOf(false) }
//
//    LaunchedEffect(Unit) {
//        isVisible.value = true
//    }
//
//    Box(modifier = modifier.fillMaxSize()) {
//        AnimatedPlannerBackground()
//
//        AnimatedVisibility(
//            visible = isVisible.value,
//            enter = fadeIn() + slideInVertically()
//        ) {
//            Column(
//                modifier = Modifier
//                    .fillMaxSize()
//                    .padding(16.dp)
//                    .verticalScroll(rememberScrollState())
//            ) {
//                Text(
//                    "Plan Your Weekly Workout",
//                    style = MaterialTheme.typography.headlineSmall,
//                    color = Color.White
//                )
//                Spacer(modifier = Modifier.height(16.dp))
//
//                daysOfWeek.forEach { day ->
//                    Card(
//                        modifier = Modifier
//                            .fillMaxWidth()
//                            .padding(vertical = 8.dp),
//                        shape = RoundedCornerShape(16.dp),
//                        colors = CardDefaults.cardColors(
//                            containerColor = Color(0xFF1E1E1E).copy(alpha = 0.4f)
//                        )
//                    ) {
//                        Column(modifier = Modifier.padding(12.dp)) {
//                            Text(
//                                text = day,
//                                style = MaterialTheme.typography.titleMedium,
//                                color = Color.White
//                            )
//                            Spacer(modifier = Modifier.height(8.dp))
//                            FlowRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
//                                bodyParts.forEach { part ->
//                                    val isSelected = selectedRoutine[day] == part
//                                    FilterChip(
//                                        selected = isSelected,
//                                        onClick = { selectedRoutine[day] = part },
//                                        label = {
//                                            Text(
//                                                part,
//                                                color = if (isSelected) Color.Cyan else Color.White.copy(alpha = 0.7f),
//                                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
//                                            )
//                                        },
//                                        shape = RoundedCornerShape(50),
//                                        colors = FilterChipDefaults.filterChipColors(
//                                            containerColor = if (isSelected) Color(0xFF00E5FF).copy(alpha = 0.3f) else Color.Transparent
//                                        )
//                                    )
//                                }
//                            }
//                        }
//                    }
//                }
//
//                Spacer(modifier = Modifier.height(24.dp))
//                Button(
//                    onClick = { onSave(selectedRoutine.toMap()) },
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .padding(top = 16.dp)
//                        .shadow(
//                            elevation = 12.dp,
//                            spotColor = Color(0xFF00E5FF),
//                            ambientColor = Color(0xFF00E5FF)
//                        ),
//                    shape = RoundedCornerShape(50),
//                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF00E5FF))
//                ) {
//                    Text("Save Routine", color = Color.Black, fontWeight = FontWeight.Bold)
//                }
//            }
//        }
//    }
//}
//
//val exerciseMap = mapOf(
//    "Chest" to listOf("Bench Press", "Incline Dumbbell Press", "Cable Crossover"),
//    "Back" to listOf("Deadlift", "Lat Pulldown", "Seated Row"),
//    "Legs" to listOf("Squat", "Leg Press", "Lunges"),
//    "Arms" to listOf("Bicep Curl", "Tricep Pushdown", "Hammer Curl"),
//    "Shoulders" to listOf("Shoulder Press", "Lateral Raise", "Front Raise"),
//    "Abs" to listOf("Crunches", "Leg Raises", "Plank")
//)
//
//fun getExercisesForDay(routine: Map<String, String>, day: String): List<String> {
//    val part = routine[day] ?: return emptyList()
//    return exerciseMap[part] ?: emptyList()
//}
//
//@Composable
//fun MemberDashboard(routine: Map<String, String>) {
//    val today = getTodayName()
//    val suggestions = getExercisesForDay(routine, today)
//
//    Column(modifier = Modifier.fillMaxWidth().padding(16.dp)) {
//        if (suggestions.isNotEmpty()) {
//            val timelineItems = suggestions.map {
//                TimelineItem(
//                    title = it,
//                    description = "Do 3 sets of 12 reps",
//                    color = MaterialTheme.colorScheme.primary
//                )
//            }
//
//            LazyColumn(
//                verticalArrangement = Arrangement.spacedBy(16.dp),
//                modifier = Modifier.fillMaxWidth()
//            ) {
//                itemsIndexed(timelineItems) { index, item ->
//                    AnimatedTimelineItem(
//                        item = item,
//                        animationDelay = index * 300L
//                    )
//                }
//            }
//        } else {
//            Text(
//                text = "Enjoy your rest day!",
//                style = MaterialTheme.typography.bodyLarge,
//                color = Color.White
//            )
//        }
//    }
//}
//
//@RequiresApi(Build.VERSION_CODES.O)
//fun getTodayName(): String {
//    return java.time.LocalDate.now().dayOfWeek.name.lowercase().replaceFirstChar { it.uppercase() }
//}
//
//@Composable
//fun AnimatedPlannerBackground() {
//    val infiniteTransition = rememberInfiniteTransition()
//    val pulse by infiniteTransition.animateFloat(
//        initialValue = 0f,
//        targetValue = 2 * PI.toFloat(),
//        animationSpec = infiniteRepeatable(
//            animation = tween(8000, easing = LinearEasing)
//        )
//    )
//    val shimmer by infiniteTransition.animateFloat(
//        initialValue = 0f,
//        targetValue = 1f,
//        animationSpec = infiniteRepeatable(
//            animation = tween(3000, easing = LinearEasing)
//        )
//    )
//
//    Canvas(modifier = Modifier
//        .fillMaxSize()
//        .background(Color(0xFF0A0A0A))
//    ) {
//        val width = size.width
//        val height = size.height
//        val center = Offset(width / 2, height / 2)
//
//        // Glow aura behind center
//        drawCircle(
//            color = Color(0xFF00E5FF).copy(alpha = 0.07f),
//            radius = width * 0.7f + sin(pulse) * 20f,
//            center = center
//        )
//
//        // Animated soft grid
//        val spacing = 48f
//        for (x in 0..(width / spacing).toInt()) {
//            for (y in 0..(height / spacing).toInt()) {
//                val alpha = 0.02f + (0.02f * sin(pulse + x + y))
//                drawCircle(
//                    color = Color.White.copy(alpha = alpha),
//                    radius = 2f,
//                    center = Offset(x * spacing, y * spacing)
//                )
//            }
//        }
//
//        // Faint pulse rings
//        for (i in 1..3) {
//            val radius = 100f + i * 60f + sin(pulse + i) * 5f
//            val alpha = 0.02f * (4 - i)
//            drawCircle(
//                color = Color(0xFF00E5FF).copy(alpha = alpha),
//                radius = radius,
//                center = center,
//                style = Stroke(width = 1.dp.toPx())
//            )
//        }
//
//        // Shimmer bar
//        val shimmerY = height * shimmer
//        drawLine(
//            color = Color(0xFF00E5FF).copy(alpha = 0.06f),
//            start = Offset(0f, shimmerY),
//            end = Offset(width, shimmerY),
//            strokeWidth = 2.dp.toPx()
//        )
//    }
//}
//
//
