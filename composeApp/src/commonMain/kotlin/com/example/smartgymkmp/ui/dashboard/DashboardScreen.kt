package com.example.smartgymkmp.ui.dashboard

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.shape.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.*
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.smartgym.ui.dashboard.composables.LeadIntakeForm
//import com.example.smartgymkmp.ui.workoutroutine.MemberDashboard
//import com.example.smartgymkmp.ui.workoutroutine.getTodayName
import kotlinx.coroutines.delay
import kotlin.math.*
import draw.drawCustomText

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    routine: Map<String, String>,
//    paymentStatus: PaymentStatus,
//    razorpayLauncher: RazorpayLauncher, // 👈 Add this
//    paymentViewModel: PaymentViewModel, // 👈 Add this
    ) {
//    val showBottomSheet = remember { mutableStateOf(paymentStatus.isDue) }
    var showLeadForm by remember { mutableStateOf(false) }
    var isLoaded by remember { mutableStateOf(false) }
//    val context = LocalContext.current

    LaunchedEffect(Unit) {
        delay(100)
        isLoaded = true
    }

//    if (showBottomSheet.value) {
//        ModalBottomSheet(
//            onDismissRequest = { /* prevent dismiss */ },
//            sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true),
//            containerColor = MaterialTheme.colorScheme.surface,
//            shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
//        ) {
//            Column(modifier = Modifier.padding(24.dp)) {
//                Text("Payment Due!", style = MaterialTheme.typography.headlineSmall)
//                Spacer(Modifier.height(8.dp))
//                Text("Your ${paymentStatus.planName} plan is due.")
//                Text("Amount: ₹${paymentStatus.amount}")
//                Spacer(Modifier.height(24.dp))
//                Button(onClick = {
//                    showBottomSheet.value = false
//
//                    (context as? LifecycleOwner)?.lifecycleScope?.launch {
//                        razorpayLauncher.startPayment(
//                            gymOwnerId = "gym_001", // doc ID from Firestore
//                            amount = 500,
//                            planName = "Monthly Plan",
//                            onSuccess = {
//                                paymentViewModel.recordPayment(500, "Monthly", "Razorpay")
//                            },
//                            onFailure = {
//                                Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
//                            }
//                        )
//                    }
//
//                }) {
//                    Text("Pay Now")
//                }
//            }
//        }
//    }

    Box(modifier = modifier.fillMaxSize()) {
        EnhancedFuturisticGymBackground()

        AnimatedVisibility(
            visible = isLoaded,
            enter = fadeIn(animationSpec = tween(1000)) + slideInVertically(
                initialOffsetY = { it },
                animationSpec = tween(1000, easing = FastOutSlowInEasing)
            )
        ) {
            Column(
                modifier = Modifier
                    .verticalScroll(rememberScrollState())
                    .padding(16.dp)
            ) {
                AnimatedHeader()
                Spacer(modifier = Modifier.height(24.dp))
                AnimatedMetricsCarousel()
                Spacer(modifier = Modifier.height(24.dp))
                AnimatedLiveGymStatus()
                Spacer(modifier = Modifier.height(24.dp))
                AnimatedWorkoutTimeline(routine)
                Spacer(modifier = Modifier.height(24.dp))
                AnimatedMotivationalCard()
                Spacer(modifier = Modifier.height(100.dp)) // FAB space
            }
        }

        // Animated FABs
        AnimatedFABs(
            isVisible = isLoaded,
            onShowLeadForm = { showLeadForm = true },
            onShowMemberManagement = { navController.navigate("memberManagement") }
        )

        AnimatedDialog(
            visible = showLeadForm,
            onDismiss = { showLeadForm = false }
        ) {
            LeadIntakeForm()
        }
    }
}

// Replace your existing background calls in DashboardScreen with this single call:
// EnhancedFuturisticGymBackground()
// AdvancedHolographicOverlay()

@Composable
fun EnhancedFuturisticGymBackground() {

    Canvas(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF0A0A0A))
            .graphicsLayer {
                alpha = 0.1f // 👈 Background becomes subtle
            }
    ) {
        val width = size.width
        val height = size.height
        val centerX = width / 2
        val centerY = height / 2

        // Neural network grid with data flow
        drawNeuralNetworkGrid(width, height)

        // Holographic data streams
        drawHolographicDataStreams(width, height)

        // Floating workout equipment with trails
        drawFloatingWorkoutEquipment(centerX, centerY)

        // Biometric scanner overlay
        drawBiometricScanner(centerX, centerY)

        // Energy field emanations
        drawEnergyFieldEmanations(width, height)

        drawCentralGymIcon(centerX, centerY)

        drawOrbitingGymIcons(centerX, centerY)

        drawBackgroundGlowAura(centerX, centerY)

        drawHolographicTrainerAssistant(width, height)
    }
}

private fun DrawScope.drawHolographicTrainerAssistant(width: Float, height: Float) {
    val center = Offset(width * 0.85f, height * 0.15f)
    val pulse = 10f + sin(2 * PI.toFloat()) * 5f
    val alpha = 0.5f + 0.5f * sin(2 * PI.toFloat())

    // Glowing ring
    drawCircle(
        color = Color.Cyan.copy(alpha = alpha * 0.2f),
        radius = 40f + pulse,
        center = center,
        style = Stroke(width = 2.dp.toPx())
    )

    // Inner face (just circle for now)
    drawCircle(
        color = Color(0xFF00E5FF).copy(alpha = 0.8f),
        radius = 20f,
        center = center
    )

    // Scanning line
    val scanAngle = 2 * PI.toFloat()
    drawLine(
        color = Color.Cyan.copy(alpha = 0.6f),
        start = center,
        end = Offset(
            center.x + cos(scanAngle) * 30f,
            center.y + sin(scanAngle) * 30f
        ),
        strokeWidth = 2.dp.toPx()
    )
}


private fun DrawScope.drawBackgroundGlowAura(centerX: Float, centerY: Float) {
    val glowPulse = 0.3f + 0.2f * sin( 2 * PI.toFloat())
    val colors = listOf(
        Color(0xFF00E676),
        Color(0xFFFF4081),
        Color(0xFF00E5FF)
    )

    colors.forEachIndexed { i, color ->
        drawCircle(
            color = color.copy(alpha = glowPulse / (i + 1)),
            radius = 200f + i * 60f,
            center = Offset(centerX, centerY),
            style = Stroke(width = 4.dp.toPx())
        )
    }
}


private fun DrawScope.drawOrbitingGymIcons(centerX: Float, centerY: Float) {
    val radius = 160f
    val icons = listOf("🏋️", "🤸", "🏃", "💪", "🧘")
    val orbitSpeed = 0.5f

    icons.forEachIndexed { index, icon ->
        val angle = 2 * PI.toFloat() * orbitSpeed + index * (2 * PI.toFloat() / icons.size)
        val x = centerX + cos(angle) * radius
        val y = centerY + sin(angle) * radius

        drawCustomText(
            text = icon,
            x = x,
            y = y
        )
    }
}


private fun DrawScope.drawCentralGymIcon(centerX: Float, centerY: Float) {
    val iconSize = 80f // Fixed size
    val alpha = 0.7f   // Fixed glow

    // Glowing pulse ring (static)
    drawCircle(
        color = Color(0xFF00E5FF).copy(alpha = alpha * 0.4f),
        radius = iconSize * 2f,
        center = Offset(centerX, centerY),
        style = Stroke(width = 2.dp.toPx())
    )

    drawCustomText(
        text = "🏋️",
        x = centerX,
        y = centerY + iconSize / 2f // Baseline correction
    )
}

private fun DrawScope.drawNeuralNetworkGrid(
    width: Float,
    height: Float
) {
    // Dynamic neural network connections
    val nodeCount = 6
    val nodes = mutableListOf<Offset>()

    // Create nodes with subtle movement
    for (i in 0 until nodeCount) {
        for (j in 0 until nodeCount) {
            val baseX = (i + 1) * width / (nodeCount + 1)
            val baseY = (j + 1) * height / (nodeCount + 1)

            // Add organic movement

        }
    }

    // Draw connections with data packets
    nodes.forEachIndexed { index, node ->
        val nearbyNodes = nodes.filterIndexed { i, _ ->
            i != index && (i - index).absoluteValue <= 3
        }

        nearbyNodes.forEach { nearbyNode ->
            val distance = sqrt((node.x - nearbyNode.x).pow(2) + (node.y - nearbyNode.y).pow(2))
            if (distance < 150f) {
                val connectionAlpha = (1f - distance / 150f) * 0.3f

                // Connection line with energy flow
                drawLine(
                    color = Color(0xFF00E5FF).copy(alpha = connectionAlpha),
                    start = node,
                    end = nearbyNode,
                    strokeWidth = 2.dp.toPx()
                )

                // Data packet moving along connection
                val packetProgress = (index * 0.1f) % 1f
                val packetPos = Offset(
                    node.x + (nearbyNode.x - node.x) * packetProgress,
                    node.y + (nearbyNode.y - node.y) * packetProgress
                )

                drawCircle(
                    color = Color(0xFF00E5FF).copy(alpha = 0.8f),
                    radius = 3f,
                    center = packetPos
                )
            }
        }

        // Neural node with pulsing effect

        drawCircle(
            color = Color(0xFF00E676),
            center = node
        )

        // Node glow effect
        drawCircle(
            color = Color(0xFF00E676).copy(alpha = 0.3f),
            center = node
        )
    }
}

private fun DrawScope.drawHolographicDataStreams(
    width: Float,
    height: Float
) {
    val streamCount = 4
    val workoutData = listOf("BPM: 142", "SET: 3/4", "REPS: 12", "KG: 85")

    for (i in 0 until streamCount) {
        val streamY = height * (0.2f + i * 0.2f)
        val streamProgress = (i * 0.25f) % 1f

        // Data stream line
        drawLine(
            color = Color(0xFF00E5FF).copy(alpha = 0.6f),
            start = Offset(0f, streamY),
            end = Offset(width, streamY),
            strokeWidth = 1.dp.toPx()
        )

        // Moving data points
        val dataX = width * streamProgress
        val glitchOffset = sin(10f) * 5f

        // Data visualization bars
        for (j in 0 until 8) {
            val barX = dataX + j * 15f - 60f
            if (barX > 0 && barX < width) {
                val barHeight = (sin(5f + j * 0.5f) + 1f) * 10f + 5f
                drawLine(
                    color = Color(0xFF00E5FF).copy(alpha = 0.7f),
                    start = Offset(barX, streamY - barHeight),
                    end = Offset(barX, streamY + barHeight),
                    strokeWidth = 2.dp.toPx()
                )
            }
        }

        // Holographic text effect (simulated)
        drawCircle(
            color = Color(0xFF00E5FF).copy(alpha = 0.8f),
            radius = 4f,
            center = Offset(dataX + glitchOffset, streamY)
        )

        // Trailing particles
        for (k in 1..5) {
            val trailX = dataX - k * 20f
            val trailAlpha = 0.8f - k * 0.15f
            if (trailX > 0) {
                drawCircle(
                    color = Color(0xFF00E5FF).copy(alpha = trailAlpha),
                    radius = 2f,
                    center = Offset(trailX, streamY)
                )
            }
        }
    }
}

private fun DrawScope.drawFloatingWorkoutEquipment(
    centerX: Float,
    centerY: Float
) {
    // Floating dumbbells with motion trails
    val dumbbellPositions = listOf(
        Offset(centerX * 0.3f, centerY * 0.4f),
        Offset(centerX * 1.7f, centerY * 0.8f),
        Offset(centerX * 0.2f, centerY * 1.6f)
    )

    dumbbellPositions.forEachIndexed { index, basePos ->
        val floatY = sin(2 * PI + index * 1.2f) * 15f
        val floatX = cos(1.5f * PI + index * 0.8f) * 8f
        val currentPos = Offset(basePos.x + floatX.toFloat(), basePos.y + floatY.toFloat())

        // Motion trail effect
        for (i in 1..8) {
            val trailAlpha = 0.6f - i * 0.07f
            val trailPos = Offset(
                (currentPos.x.toFloat() - floatX * i * 0.1f).toFloat(),
                (currentPos.y - floatY * i * 0.1f).toFloat()
            )

            drawDumbbellGhost(trailPos, trailAlpha, 0.9f - i * 0.05f)
        }

        // Main dumbbell
        drawDumbbellGhost(currentPos, 0.8f, 1f)
    }
}

private fun DrawScope.drawDumbbellGhost(position: Offset, alpha: Float, scale: Float) {
    val size = 12f * scale

    // Dumbbell weights
    drawCircle(
        color = Color(0xFF00E5FF).copy(alpha = alpha),
        radius = size,
        center = Offset(position.x - size * 1.5f, position.y)
    )
    drawCircle(
        color = Color(0xFF00E5FF).copy(alpha = alpha),
        radius = size,
        center = Offset(position.x + size * 1.5f, position.y)
    )

    // Dumbbell bar
    drawLine(
        color = Color(0xFF00E5FF).copy(alpha = alpha),
        start = Offset(position.x - size * 1.5f, position.y),
        end = Offset(position.x + size * 1.5f, position.y),
        strokeWidth = (4.dp.toPx() * scale)
    )
}

private fun DrawScope.drawBiometricScanner(
    centerX: Float,
    centerY: Float
) {
    val scannerRadius = 80f
    val scannerPos = Offset(centerX * 1.6f, centerY * 0.3f)

    // Scanning rings
    for (i in 1..4) {
        val ringRadius = scannerRadius * i * 0.3f
        val ringAlpha = (sin(2f + i * 0.5f) + 1f) / 2f * 0.4f

        drawCircle(
            color = Color(0xFF00E676).copy(alpha = ringAlpha),
            radius = ringRadius,
            center = scannerPos,
            style = Stroke(width = 2.dp.toPx())
        )
    }

    // Central scanner core
    drawCircle(
        color = Color(0xFF00E676).copy(alpha = 0.9f),
        radius = 8f,
        center = scannerPos
    )
}

private fun DrawScope.drawDigitalRain(
    time: Float,
    width: Float,
    height: Float
) {
    val rainColumns = 12
    val columnWidth = width / rainColumns

    for (i in 0 until rainColumns) {
        val columnX = i * columnWidth + columnWidth / 2
        val rainSpeed = 0.5f + (i % 3) * 0.3f
        val rainPhase = (time * rainSpeed + i * 0.2f) % 1f

        // Digital rain drops
        for (j in 0 until 8) {
            val dropY = height * rainPhase + j * 40f - 160f
            if (dropY > 0 && dropY < height) {
                val dropAlpha = if (j == 0) 0.8f else 0.4f - j * 0.05f
                val dropSize = if (j == 0) 3f else 2f

                drawCircle(
                    color = Color(0xFF00E5FF).copy(alpha = dropAlpha),
                    radius = dropSize,
                    center = Offset(columnX + sin(time * 3f + i) * 5f, dropY)
                )

                // Trailing line
                if (j == 0) {
                    drawLine(
                        color = Color(0xFF00E5FF).copy(alpha = 0.3f),
                        start = Offset(columnX, dropY),
                        end = Offset(columnX, dropY + 20f),
                        strokeWidth = 1.dp.toPx()
                    )
                }
            }
        }
    }
}

private fun DrawScope.drawEnergyFieldEmanations(
    width: Float,
    height: Float
) {
    val fieldCenters = listOf(
        Offset(width * 0.1f, height * 0.9f),
        Offset(width * 0.9f, height * 0.1f),
        Offset(width * 0.8f, height * 0.7f)
    )

    fieldCenters.forEachIndexed { index, center ->
        val fieldPhase = index * 2f

        // Energy field ripples
        for (i in 1..6) {
            val rippleRadius = i * 25f + sin(fieldPhase + i * 0.3f) * 8f
            val rippleAlpha = (0.4f - i * 0.06f) * (sin(fieldPhase) + 1f) / 2f

            drawCircle(
                color = Color(0xFFFF4081).copy(alpha = rippleAlpha),
                radius = rippleRadius,
                center = center,
                style = Stroke(width = 2.dp.toPx())
            )
        }

        // Energy core
        val coreAlpha = (sin(fieldPhase * 2f) + 1f) / 2f * 0.8f
        drawCircle(
            color = Color(0xFFFF4081).copy(alpha = coreAlpha),
            radius = 6f,
            center = center
        )
    }
}

@Composable
private fun AnimatedHeader() {
    var isVisible by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        delay(300)
        isVisible = true
    }

    AnimatedVisibility(
        visible = isVisible,
        enter = fadeIn(animationSpec = tween(800)) +
                slideInVertically(initialOffsetY = { -it }, animationSpec = tween(800)) +
                scaleIn(initialScale = 0.8f, animationSpec = tween(800))
    ) {
        GlowingCard(
            modifier = Modifier
                .fillMaxWidth()
                .height(160.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp)
            ) {
                // Animated circuit pattern
                AnimatedCircuitPattern()

                Column {
                    TypewriterText(
                        text = "Welcome Back, Shivansh",
                        style = MaterialTheme.typography.headlineMedium.copy(
                            color = Color.White,
                            fontWeight = FontWeight.Bold
                        ),
                        animationDelay = 500
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    TypewriterText(
                        text = "Your Fitness Journey Continues",
                        style = MaterialTheme.typography.titleMedium.copy(
                            color = Color(0xFF00E5FF)
                        ),
                        animationDelay = 1000
                    )

                    // Animated progress bar
                    AnimatedProgressBar(
                        progress = 0.7f,
                        modifier = Modifier
                            .padding(top = 16.dp)
                            .fillMaxWidth()
                            .height(8.dp)
                    )

                    TypewriterText(
                        text = "70% of daily goal completed",
                        style = MaterialTheme.typography.bodySmall.copy(
                            color = Color.White.copy(alpha = 0.7f)
                        ),
                        animationDelay = 1500,
                        modifier = Modifier.padding(top = 8.dp)
                    )
                }
            }
        }
    }
}

@Composable
private fun AnimatedMetricsCarousel() {
    var isVisible by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        delay(600)
        isVisible = true
    }

    AnimatedVisibility(
        visible = isVisible,
        enter = fadeIn(animationSpec = tween(600)) +
                slideInHorizontally(initialOffsetX = { it }, animationSpec = tween(600))
    ) {
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            itemsIndexed(metrics) { index, metric ->
                AnimatedMetricCard(
                    metric = metric,
                    animationDelay = index * 200L
                )
            }
        }
    }
}

@Composable
private fun AnimatedMetricCard(metric: Metric, animationDelay: Long) {
    var isVisible by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        delay(animationDelay)
        isVisible = true
    }

    AnimatedVisibility(
        visible = isVisible,
        enter = fadeIn(animationSpec = tween(600)) +
                scaleIn(initialScale = 0.8f, animationSpec = tween(600)) +
                slideInVertically(initialOffsetY = { it }, animationSpec = tween(600))
    ) {
        GlowingCard(
            modifier = Modifier
                .width(160.dp)
                .height(180.dp),
            glowColor = metric.color
        ) {
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                // Animated circular progress
                AnimatedCircularProgress(
                    progress = metric.progress,
                    color = metric.color,
                    modifier = Modifier.size(80.dp)
                )

                Spacer(modifier = Modifier.height(16.dp))

                AnimatedCounterText(
                    targetValue = metric.value,
                    style = MaterialTheme.typography.headlineSmall.copy(
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                )

                Text(
                    metric.title,
                    style = MaterialTheme.typography.bodySmall.copy(
                        color = Color.White.copy(alpha = 0.7f)
                    )
                )
            }
        }
    }
}

@Composable
private fun AnimatedLiveGymStatus() {
    var isVisible by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        delay(900)
        isVisible = true
    }

    AnimatedVisibility(
        visible = isVisible,
        enter = fadeIn(animationSpec = tween(600)) +
                slideInHorizontally(initialOffsetX = { -it }, animationSpec = tween(600))
    ) {
        GlowingCard(
            modifier = Modifier
                .fillMaxWidth()
                .height(120.dp),
            glowColor = Color(0xFF00E676)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        "Live Gym Status",
                        style = MaterialTheme.typography.titleMedium.copy(
                            color = Color.White,
                            fontWeight = FontWeight.Bold
                        )
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        PulsatingDot()
                        Text(
                            "Currently Open",
                            color = Color(0xFF00E676),
                            style = MaterialTheme.typography.bodyLarge.copy(
                                fontWeight = FontWeight.Bold
                            )
                        )
                    }
                    Text(
                        "Closes at 11:00 PM",
                        color = Color.White.copy(alpha = 0.7f),
                        style = MaterialTheme.typography.bodyMedium
                    )
                }

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    AnimatedCounterText(
                        targetValue = "45%",
                        style = MaterialTheme.typography.headlineMedium.copy(
                            color = Color(0xFF00E5FF),
                            fontWeight = FontWeight.Bold
                        )
                    )
                    Text(
                        "Occupied",
                        color = Color.White.copy(alpha = 0.7f),
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }
        }
    }
}

@Composable
private fun AnimatedWorkoutTimeline(routine: Map<String, String>) {
    var isVisible by remember { mutableStateOf(false) }
    val today = /*getTodayName()*/ "Monday"
    val todayPart = routine[today] ?: "Rest"


    LaunchedEffect(Unit) {
        delay(1200)
        isVisible = true
    }

    AnimatedVisibility(
        visible = isVisible,
        enter = fadeIn(animationSpec = tween(600)) +
                slideInVertically(initialOffsetY = { it }, animationSpec = tween(600))
    ) {
        GlowingCard(
            modifier = Modifier
                .fillMaxWidth()
                .height(280.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp)
            ) {
                Text(
                    "Today's Focus: $todayPart",
                    style = MaterialTheme.typography.titleMedium.copy(
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                )
                Spacer(modifier = Modifier.height(16.dp))

//                MemberDashboard(routine)
            }
        }
    }
}

@Composable
fun AnimatedTimelineItem(item: TimelineItem, animationDelay: Long) {
    var isVisible by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        delay(animationDelay)
        isVisible = true
    }

    AnimatedVisibility(
        visible = isVisible,
        enter = fadeIn(animationSpec = tween(400)) +
                slideInHorizontally(initialOffsetX = { -it }, animationSpec = tween(400))
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Box(
                modifier = Modifier.width(24.dp),
                contentAlignment = Alignment.Center
            ) {
                Box(
                    modifier = Modifier
                        .width(2.dp)
                        .height(50.dp)
                        .background(Color.White.copy(alpha = 0.2f))
                )
                PulsatingTimelineDot(color = item.color)
            }

            Column {
                Text(
                    item.title,
                    color = Color.White,
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontWeight = FontWeight.Bold
                    )
                )
                Text(
                    item.description,
                    color = Color.White.copy(alpha = 0.7f),
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}

@Composable
private fun AnimatedMotivationalCard() {
    var isVisible by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        delay(1500)
        isVisible = true
    }

    AnimatedVisibility(
        visible = isVisible,
        enter = fadeIn(animationSpec = tween(800)) +
                scaleIn(initialScale = 0.9f, animationSpec = tween(800)) +
                slideInVertically(initialOffsetY = { it }, animationSpec = tween(800))
    ) {
        GlowingCard(
            modifier = Modifier
                .fillMaxWidth()
                .height(160.dp),
            glowColor = Color(0xFFFFD700)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp)
            ) {
                // Animated background pattern
                AnimatedBackgroundPattern()

                Column {
                    BouncingIcon(
                        icon = Icons.Default.Face,
                        tint = Color(0xFFFFD700),
                        size = 32.dp
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    TypewriterText(
                        text = "Discipline is the bridge between goals and accomplishment.",
                        style = MaterialTheme.typography.titleMedium.copy(
                            color = Color.White,
                            fontWeight = FontWeight.Bold
                        ),
                        animationDelay = 1800
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        "- Jim Rohn",
                        style = MaterialTheme.typography.bodyMedium.copy(
                            color = Color.White.copy(alpha = 0.7f)
                        )
                    )
                }
            }
        }
    }
}

@Composable
private fun AnimatedFABs(
    isVisible: Boolean,
    onShowLeadForm: () -> Unit,
    onShowMemberManagement: () -> Unit
) {
    AnimatedVisibility(
        visible = isVisible,
        enter = slideInHorizontally(initialOffsetX = { it }) +
                fadeIn(animationSpec = tween(600, delayMillis = 1800)),
        modifier = Modifier
            .fillMaxSize()
            .wrapContentSize(Alignment.BottomEnd)
            .padding(16.dp)
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            GlowingFAB(
                onClick = onShowMemberManagement,
                icon = Icons.Default.Person,
                contentDescription = "Manage Members",
                glowColor = Color(0xFF00E5FF)
            )

            GlowingFAB(
                onClick = onShowLeadForm,
                icon = Icons.Default.AddCircle,
                contentDescription = "Add Lead",
                glowColor = Color(0xFF00E676)
            )
        }
    }
}

// Utility Composables
@Composable
private fun GlowingCard(
    modifier: Modifier = Modifier,
    glowColor: Color = Color(0xFF00E5FF),
    content: @Composable () -> Unit
) {
    Card(
        modifier = modifier
            .shadow(
                elevation = 16.dp,
                spotColor = glowColor.copy(alpha = 0.6f),
                ambientColor = glowColor.copy(alpha = 0.3f)
            ),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(
            // 👇 Yaha transparency adjust karo
            containerColor = Color(0xFF1E1E1E).copy(alpha = 0.4f) // 🔥 was 0.9f before
        ),
        border = BorderStroke(1.dp, glowColor.copy(alpha = 0.3f))
    ) {
        content()
    }
}

@Composable
private fun GlowingFAB(
    onClick: () -> Unit,
    icon: ImageVector,
    contentDescription: String,
    glowColor: Color
) {
    val scale by animateFloatAsState(
        targetValue = 1f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        )
    )

    FloatingActionButton(
        onClick = onClick,
        modifier = Modifier
            .scale(scale)
            .shadow(
                elevation = 12.dp,
                spotColor = glowColor.copy(alpha = 0.8f),
                ambientColor = glowColor.copy(alpha = 0.4f)
            ),
        containerColor = glowColor,
        contentColor = Color.Black
    ) {
        BouncingIcon(
            icon = icon,
            tint = Color.Black,
            size = 24.dp
        )
    }
}

@Composable
private fun TypewriterText(
    text: String,
    style: TextStyle,
    animationDelay: Long = 0,
    modifier: Modifier = Modifier
) {
    var displayedText by remember { mutableStateOf("") }

    LaunchedEffect(text) {
        delay(animationDelay)
        text.forEachIndexed { index, _ ->
            displayedText = text.take(index + 1)
            delay(50)
        }
    }

    Text(
        text = displayedText,
        style = style,
        modifier = modifier
    )
}

@Composable
private fun AnimatedCounterText(
    targetValue: String,
    style: TextStyle,
    modifier: Modifier = Modifier
) {
    val animatedValue by animateFloatAsState(
        targetValue = 1f,
        animationSpec = tween(1000, easing = FastOutSlowInEasing)
    )

    Text(
        text = targetValue,
        style = style,
        modifier = modifier.alpha(animatedValue)
    )
}

@Composable
private fun AnimatedCircularProgress(
    progress: Float,
    color: Color,
    modifier: Modifier = Modifier
) {
    val animatedProgress by animateFloatAsState(
        targetValue = progress,
        animationSpec = tween(1500, easing = FastOutSlowInEasing)
    )

    CircularProgressIndicator(
        progress = animatedProgress,
        modifier = modifier,
        color = color,
        strokeWidth = 8.dp,
        trackColor = Color.White.copy(alpha = 0.1f)
    )
}

@Composable
private fun AnimatedProgressBar(
    progress: Float,
    modifier: Modifier = Modifier
) {
    val animatedProgress by animateFloatAsState(
        targetValue = progress,
        animationSpec = tween(1500, easing = FastOutSlowInEasing)
    )

    LinearProgressIndicator(
        progress = animatedProgress,
        modifier = modifier.clip(RoundedCornerShape(4.dp)),
        color = Color(0xFF00E5FF),
        trackColor = Color.White.copy(alpha = 0.1f)
    )
}

@Composable
private fun PulsatingDot() {
    val infiniteTransition = rememberInfiniteTransition()
    val scale by infiniteTransition.animateFloat(
        initialValue = 0.8f,
        targetValue = 1.2f,
        animationSpec = infiniteRepeatable(
            animation = tween(1000, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        )
    )

    Box(
        modifier = Modifier
            .size(12.dp)
            .scale(scale)
            .background(Color(0xFF00E676), CircleShape)
    )
}

@Composable
private fun PulsatingTimelineDot(color: Color) {
    val infiniteTransition = rememberInfiniteTransition()
    val scale by infiniteTransition.animateFloat(
        initialValue = 0.8f,
        targetValue = 1.2f,
        animationSpec = infiniteRepeatable(
            animation = tween(1500, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        )
    )

    Box(
        modifier = Modifier
            .size(8.dp)
            .scale(scale)
            .background(color, CircleShape)
    )
}

@Composable
private fun BouncingIcon(
    icon: ImageVector,
    tint: Color,
    size: Dp
) {
    val infiniteTransition = rememberInfiniteTransition()
    val bounce by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = -10f,
        animationSpec = infiniteRepeatable(
            animation = tween(1000, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        )
    )

    Icon(
        imageVector = icon,
        contentDescription = null,
        tint = tint,
        modifier = Modifier
            .size(size)
            .offset(y = bounce.dp)
    )
}

@Composable
private fun AnimatedCircuitPattern() {
    val infiniteTransition = rememberInfiniteTransition()
    val alpha by infiniteTransition.animateFloat(
        initialValue = 0.1f,
        targetValue = 0.3f,
        animationSpec = infiniteRepeatable(
            animation = tween(2000, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        )
    )

    Canvas(modifier = Modifier.fillMaxSize()) {
        val width = size.width
        val height = size.height

        // Draw circuit-like pattern
        drawLine(
            color = Color(0xFF00E5FF).copy(alpha = alpha.coerceIn(0.0f, 1.0f)),
            start = Offset(width * 0.8f, 0f),
            end = Offset(width * 0.8f, height * 0.3f),
            strokeWidth = 2.dp.toPx()
        )

        drawLine(
            color = Color(0xFF00E5FF).copy(alpha = alpha.coerceIn(0.0f, 1.0f)),
            start = Offset(width * 0.8f, height * 0.3f),
            end = Offset(width, height * 0.3f),
            strokeWidth = 2.dp.toPx()
        )

        drawCircle(
            color = Color(0xFF00E5FF).copy(alpha = alpha.coerceIn(0.0f, 1.0f)),
            radius = 4.dp.toPx(),
            center = Offset(width * 0.8f, height * 0.3f)
        )
    }
}

@Composable
private fun AnimatedBackgroundPattern() {
    val infiniteTransition = rememberInfiniteTransition()
    val rotation by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(15000, easing = LinearEasing)
        )
    )

    Canvas(modifier = Modifier.fillMaxSize()) {
        rotate(rotation) {
            drawCircle(
                color = Color.White.copy(alpha = 0.05f),
                radius = 80f,
                center = Offset(size.width - 40f, size.height - 40f)
            )
        }
    }
}

@Composable
private fun AnimatedDialog(
    visible: Boolean,
    onDismiss: () -> Unit,
    content: @Composable () -> Unit
) {
    if (visible) {
        AnimatedVisibility(
            visible = visible,
            enter = fadeIn(animationSpec = tween(300)) +
                    scaleIn(initialScale = 0.8f, animationSpec = tween(300)),
            exit = fadeOut(animationSpec = tween(300)) +
                    scaleOut(targetScale = 0.8f, animationSpec = tween(300))
        ) {
            Surface(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                shape = RoundedCornerShape(24.dp),
                color = Color.Transparent
            ) {
                content()
            }
        }
    }
}


data class Metric(
    val title: String,
    val value: String,
    val progress: Float,
    val color: Color
)

data class TimelineItem(
    val title: String,
    val description: String,
    val color: Color
)

// Sample data
private val metrics = listOf(
    Metric("Calories", "856", 0.7f, Color(0xFF00E5FF)),
    Metric("Active Time", "83", 0.6f, Color(0xFFFF4081)),
    Metric("Steps", "8,547", 0.8f, Color(0xFF00E676))
)

