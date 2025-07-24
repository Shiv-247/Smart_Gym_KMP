//package com.smartgym.ui.dashboard.composables
//
//import androidx.annotation.RequiresApi
//import androidx.compose.animation.*
//import androidx.compose.animation.core.*
//import androidx.compose.foundation.Canvas
//import androidx.compose.foundation.background
//import androidx.compose.foundation.border
//import androidx.compose.foundation.clickable
//import androidx.compose.foundation.gestures.detectTapGestures
//import androidx.compose.foundation.layout.*
//import androidx.compose.foundation.lazy.LazyColumn
//import androidx.compose.foundation.lazy.itemsIndexed
//import androidx.compose.foundation.lazy.rememberLazyListState
//import androidx.compose.foundation.shape.CircleShape
//import androidx.compose.foundation.shape.RoundedCornerShape
//import androidx.compose.material.icons.Icons
//import androidx.compose.material.icons.filled.*
//import androidx.compose.material3.*
//import androidx.compose.runtime.*
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.draw.clip
//import androidx.compose.ui.draw.rotate
//import androidx.compose.ui.draw.scale
//import androidx.compose.ui.geometry.Offset
//import androidx.compose.ui.graphics.Brush
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.graphics.graphicsLayer
//import androidx.compose.ui.input.pointer.pointerInput
//import androidx.compose.ui.platform.LocalDensity
//import androidx.compose.ui.text.font.FontWeight
//import androidx.compose.ui.text.style.TextAlign
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.unit.sp
//import androidx.lifecycle.viewmodel.compose.viewModel
//import androidx.navigation.NavHostController
//import com.smartgym.data.model.MemberEntity
//import com.smartgym.viewmodel.MemberViewModel
//import kotlinx.coroutines.delay
//import java.time.LocalDate
//import java.time.format.DateTimeFormatter
//import java.util.UUID
//import kotlin.math.cos
//import kotlin.math.sin
//
//// Cyberpunk color palette
//object CyberColors {
//    val NeonBlue = Color(0xFF00E5FF)
//    val NeonPurple = Color(0xFF9C27B0)
//    val NeonGreen = Color(0xFF00FF7F)
//    val NeonPink = Color(0xFFFF1493)
//    val DeepPurple = Color(0xFF1A0F2E)
//    val DarkBg = Color(0xFF0D0D0D)
//    val CardBg = Color(0xFF1A1A2E)
//    val GlowBlue = Color(0xFF0066CC)
//}
//
////@RequiresApi(Build.VERSION_CODES.O)
//@Composable
//fun MemberManagementScreen(
//    navController: NavHostController,
//    modifier: Modifier = Modifier,
//    onCallMember: (String) -> Unit = {},
//    onEditMember: (String) -> Unit = {},
//) {
////    val viewModel: MemberViewModel = viewModel()
////    val members by viewModel.allMembers.collectAsState()
//
//    var selectedFilter by remember { mutableStateOf(MemberFilter.ALL) }
//    val listState = rememberLazyListState()
//    val density = LocalDensity.current
//
//    // Animated background
//    val infiniteTransition = rememberInfiniteTransition(label = "background")
//    val backgroundOffset by infiniteTransition.animateFloat(
//        initialValue = 0f,
//        targetValue = 360f,
//        animationSpec = infiniteRepeatable(
//            animation = tween(20000, easing = LinearEasing),
//            repeatMode = RepeatMode.Restart
//        ),
//        label = "backgroundOffset"
//    )
//
//    // Scroll-based parallax effect
//    val scrollOffset = remember { derivedStateOf { listState.firstVisibleItemScrollOffset } }
//
//    val filteredMembers = members.filter {
//        when (selectedFilter) {
//            MemberFilter.TODAY -> it.startDate == LocalDate.now()
//            MemberFilter.THIS_WEEK -> it.startDate >= LocalDate.now().minusDays(7)
//            MemberFilter.ALL -> true
//        }
//    }
//
//    LaunchedEffect(Unit) {
//        viewModel.loadMembers("gym_001") // For testing/demo only
//    }
//
//
//
//    Box(
//        modifier = modifier
//            .fillMaxSize()
//            .background(
//                brush = Brush.radialGradient(
//                    colors = listOf(
//                        CyberColors.DeepPurple.copy(alpha = 0.8f),
//                        CyberColors.DarkBg,
//                        Color.Black
//                    ),
//                    radius = 1000f + backgroundOffset * 2
//                )
//            )
//    ) {
//        // Animated grid pattern background
//        AnimatedGridBackground(backgroundOffset)
//
//        Column(
//            modifier = Modifier
//                .fillMaxSize()
//                .padding(16.dp)
//        ) {
//            // Animated header
//            AnimatedHeader()
//
//            Spacer(modifier = Modifier.height(24.dp))
//
//            // Futuristic filter chips
//            FuturisticFilterChips(
//                selectedFilter = selectedFilter,
//                onFilterSelected = { selectedFilter = it }
//            )
//
//            Spacer(modifier = Modifier.height(24.dp))
//
//            // Members list with animations
//
//            LazyColumn(
//                modifier = Modifier.fillMaxSize(),
//                contentPadding = PaddingValues(16.dp),
//                verticalArrangement = Arrangement.spacedBy(12.dp)
//            ) {
//                itemsIndexed(filteredMembers) { index, member ->
//                    AnimatedMemberCard(
//                        member = member,
//                        index = index,
//                        parallaxOffset = scrollOffset.value,
//                        onCall = { onCallMember(member.phoneNumber) },
//                        onEdit = { onEditMember(member.id) },
//                        navController = navController,
//                        onClick = {
//                            navController.navigate("memberProfile/${member.id}")
//                        },
//                        viewModel = viewModel
//                    )
//                }
//            }
//
//        }
//
//        // Floating Action Button with pulsating glow
//        FloatingAddButton(
//            modifier = Modifier
//                .align(Alignment.BottomEnd)
//                .padding(16.dp),
//            scrollOffset = scrollOffset.value,
//            viewModel = viewModel
//        )
//    }
//}
//
//@Composable
//private fun AnimatedGridBackground(offset: Float) {
//    Canvas(
//        modifier = Modifier
//            .fillMaxSize()
//            .graphicsLayer {
//                translationX = sin(offset * 0.01f) * 10f
//                translationY = cos(offset * 0.01f) * 10f
//            }
//    ) {
//        val gridSize = 50.dp.toPx()
//        val strokeWidth = 1.dp.toPx()
//
//        // Draw animated grid lines
//        for (i in 0 until (size.width / gridSize).toInt()) {
//            drawLine(
//                color = CyberColors.NeonBlue.copy(alpha = 0.1f),
//                start = Offset(i * gridSize, 0f),
//                end = Offset(i * gridSize, size.height),
//                strokeWidth = strokeWidth
//            )
//        }
//
//        for (i in 0 until (size.height / gridSize).toInt()) {
//            drawLine(
//                color = CyberColors.NeonBlue.copy(alpha = 0.1f),
//                start = Offset(0f, i * gridSize),
//                end = Offset(size.width, i * gridSize),
//                strokeWidth = strokeWidth
//            )
//        }
//    }
//}
//
//@Composable
//private fun AnimatedHeader() {
//    Column {
//        Row(
//            modifier = Modifier.fillMaxWidth(),
//            horizontalArrangement = Arrangement.SpaceBetween,
//            verticalAlignment = Alignment.CenterVertically
//        ) {
//            TypewriterText(
//                text = "MEMBER CONTROL PANEL",
//                style = MaterialTheme.typography.headlineMedium.copy(
//                    fontSize = 24.sp,
//                    fontWeight = FontWeight.Bold,
//                    color = CyberColors.NeonBlue
//                )
//            )
//
//            // Animated theme toggle
//            GlowingIconButton(
//                onClick = { },
//                icon = Icons.Default.Settings
//            )
//        }
//
//        // Subtitle with glow effect
//        Text(
//            "Advanced Member Management System v2.0",
//            style = MaterialTheme.typography.bodyMedium.copy(
//                color = CyberColors.NeonGreen.copy(alpha = 0.8f)
//            ),
//            modifier = Modifier.padding(top = 4.dp)
//        )
//    }
//}
//
//@Composable
//private fun TypewriterText(
//    text: String,
//    style: androidx.compose.ui.text.TextStyle,
//    modifier: Modifier = Modifier
//) {
//    var displayedText by remember { mutableStateOf("") }
//
//    LaunchedEffect(text) {
//        displayedText = ""
//        text.forEachIndexed { index, _ ->
//            delay(100)
//            displayedText = text.substring(0, index + 1)
//        }
//    }
//
//    Text(
//        text = displayedText,
//        style = style,
//        modifier = modifier
//    )
//}
//
//@Composable
//private fun FuturisticFilterChips(
//    selectedFilter: MemberFilter,
//    onFilterSelected: (MemberFilter) -> Unit
//) {
//    Row(
//        horizontalArrangement = Arrangement.spacedBy(12.dp)
//    ) {
//        MemberFilter.values().forEachIndexed { index, filter ->
//            val isSelected = selectedFilter == filter
//            val animatedScale by animateFloatAsState(
//                targetValue = if (isSelected) 1.1f else 1f,
//                animationSpec = spring(
//                    dampingRatio = Spring.DampingRatioMediumBouncy,
//                    stiffness = Spring.StiffnessLow
//                ),
//                label = "chipScale"
//            )
//
//            LaunchedEffect(Unit) {
//                delay(index * 150L)
//            }
//
//            Box(
//                modifier = Modifier
//                    .scale(animatedScale)
//                    .clip(RoundedCornerShape(16.dp))
//                    .background(
//                        brush = if (isSelected) {
//                            Brush.linearGradient(
//                                colors = listOf(
//                                    CyberColors.NeonBlue,
//                                    CyberColors.NeonPurple
//                                )
//                            )
//                        } else {
//                            Brush.linearGradient(
//                                colors = listOf(
//                                    CyberColors.CardBg,
//                                    CyberColors.CardBg.copy(alpha = 0.8f)
//                                )
//                            )
//                        }
//                    )
//                    .border(
//                        width = 1.dp,
//                        color = if (isSelected) CyberColors.NeonBlue else CyberColors.GlowBlue.copy(
//                            alpha = 0.5f
//                        ),
//                        shape = RoundedCornerShape(16.dp)
//                    )
//                    .clickable { onFilterSelected(filter) }
//                    .padding(horizontal = 16.dp, vertical = 8.dp)
//            ) {
//                Text(
//                    filter.title,
//                    color = if (isSelected) Color.Black else CyberColors.NeonBlue,
//                    fontWeight = FontWeight.Bold,
//                    fontSize = 14.sp
//                )
//            }
//        }
//    }
//}
//
//@Composable
//private fun AnimatedMemberCard(
//    member: MemberEntity,
//    index: Int,
//    parallaxOffset: Int,
//    onCall: () -> Unit,
//    onEdit: () -> Unit,
//    navController: NavHostController,
//    onClick: () -> Unit,
//    viewModel: MemberViewModel,
//) {
//    var isVisible by remember { mutableStateOf(false) }
//    var isPressed by remember { mutableStateOf(false) }
//
//    val scale by animateFloatAsState(
//        targetValue = if (isPressed) 0.95f else 1f,
//        animationSpec = spring(
//            dampingRatio = Spring.DampingRatioMediumBouncy,
//            stiffness = Spring.StiffnessHigh
//        ),
//        label = "cardScale"
//    )
//
//    val borderAnimation = rememberInfiniteTransition(label = "borderGlow")
//    val glowIntensity by borderAnimation.animateFloat(
//        initialValue = 0.3f,
//        targetValue = 1f,
//        animationSpec = infiniteRepeatable(
//            animation = tween(2000, easing = FastOutSlowInEasing),
//            repeatMode = RepeatMode.Reverse
//        ),
//        label = "glowIntensity"
//    )
//    Card(
//        modifier = Modifier
//            .fillMaxWidth()
//            .scale(scale)
//            .graphicsLayer {
//                translationX = (parallaxOffset * 0.1f) * (index % 2 - 0.5f)
//            }
//            .pointerInput(Unit) {
//                detectTapGestures(
//                    onPress = {
//                        isPressed = true
//                        tryAwaitRelease()
//                        isPressed = false
//                    },
//                    onTap = {
//                        navController.navigate("memberProfile/${member.id}")
//                    }
//                )
//            }
//            .clickable { onClick() }
//            .border(
//                width = 2.dp,
//                brush = Brush.linearGradient(
//                    colors = listOf(
//                        CyberColors.NeonBlue.copy(alpha = glowIntensity),
//                        CyberColors.NeonPurple.copy(alpha = glowIntensity * 0.8f),
//                        CyberColors.NeonGreen.copy(alpha = glowIntensity * 0.6f)
//                    )
//                ),
//                shape = RoundedCornerShape(16.dp)
//            ),
//        colors = CardDefaults.cardColors(
//            containerColor = CyberColors.CardBg.copy(alpha = 0.9f)
//        ),
//        shape = RoundedCornerShape(16.dp)
//    ) {
//        Row(
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(20.dp),
//            horizontalArrangement = Arrangement.SpaceBetween,
//            verticalAlignment = Alignment.CenterVertically
//        ) {
//            Row(
//                verticalAlignment = Alignment.CenterVertically,
//                horizontalArrangement = Arrangement.spacedBy(16.dp)
//            ) {
//                // Animated avatar
//                AnimatedAvatar(member.name, MemberStatus.valueOf(member.status))
//
//                Column {
//                    // Typewriter name
//                    TypewriterText(
//                        text = member.name,
//                        style = MaterialTheme.typography.titleMedium.copy(
//                            fontWeight = FontWeight.Bold,
//                            color = CyberColors.NeonBlue
//                        )
//                    )
//
//                    // Animated goal
//                    Text(
//                        "TARGET: ${member.fitnessGoal}",
//                        style = MaterialTheme.typography.bodyMedium.copy(
//                            color = CyberColors.NeonGreen.copy(alpha = 0.8f)
//                        )
//                    )
//
//                    Text(
//                        "JOINED: ${member.startDate}",
//                        style = MaterialTheme.typography.bodySmall.copy(
//                            color = Color.White.copy(alpha = 0.6f)
//                        )
//                    )
//
//                    Text(
//                        "Phone no: ${member.phoneNumber}",
//                        style = MaterialTheme.typography.bodySmall.copy(
//                            color = Color.White.copy(alpha = 0.6f)
//                        )
//                    )
//
//                    Spacer(modifier = Modifier.height(10.dp))
//
//                    // Animated status badge
//                    AnimatedStatusBadge(MemberStatus.valueOf(member.status))
//
//                    Row(
//                        modifier = Modifier
//                            .fillMaxWidth()
//                            .padding(top = 20.dp)
//                    ) {
//                        GlowingIconButton(
//                            onClick = onCall,
//                            icon = Icons.Default.Call,
//                            color = CyberColors.NeonGreen
//                        )
//                        Spacer(modifier = Modifier.width(20.dp))
//                        GlowingIconButton(
//                            onClick = { viewModel.deleteMember(member) },
//                            icon = Icons.Default.Delete,
//                            color = CyberColors.NeonPink
//                        )
//                    }
//                }
//            }
//        }
//    }
//}
//
//@Composable
//private fun AnimatedAvatar(name: String, status: MemberStatus) {
//    val rotation by rememberInfiniteTransition(label = "avatar").animateFloat(
//        initialValue = 0f,
//        targetValue = 360f,
//        animationSpec = infiniteRepeatable(
//            animation = tween(10000, easing = LinearEasing),
//            repeatMode = RepeatMode.Restart
//        ),
//        label = "avatarRotation"
//    )
//
//    val statusColor = when (status) {
//        MemberStatus.ACTIVE -> CyberColors.NeonGreen
//        MemberStatus.CONVERTED -> CyberColors.NeonBlue
//        MemberStatus.PENDING -> CyberColors.NeonPink
//    }
//
//    Box(
//        modifier = Modifier
//            .size(50.dp)
//            .clip(CircleShape)
//            .background(
//                brush = Brush.radialGradient(
//                    colors = listOf(
//                        statusColor.copy(alpha = 0.3f),
//                        statusColor.copy(alpha = 0.1f)
//                    )
//                )
//            )
//            .border(
//                width = 2.dp,
//                color = statusColor,
//                shape = CircleShape
//            )
//            .rotate(rotation * 0.1f),
//        contentAlignment = Alignment.Center
//    ) {
//        Text(
//            text = name.split(" ").joinToString("") { it.first().toString() },
//            style = MaterialTheme.typography.titleMedium.copy(
//                fontWeight = FontWeight.Bold,
//                color = statusColor
//            )
//        )
//    }
//}
//
//@Composable
//private fun AnimatedStatusBadge(status: MemberStatus) {
//    val pulseAnimation = rememberInfiniteTransition(label = "pulse")
//    val pulse by pulseAnimation.animateFloat(
//        initialValue = 0.8f,
//        targetValue = 1.2f,
//        animationSpec = infiniteRepeatable(
//            animation = tween(1500, easing = FastOutSlowInEasing),
//            repeatMode = RepeatMode.Reverse
//        ),
//        label = "statusPulse"
//    )
//
//    val statusColor = when (status) {
//        MemberStatus.ACTIVE -> CyberColors.NeonGreen
//        MemberStatus.CONVERTED -> CyberColors.NeonBlue
//        MemberStatus.PENDING -> CyberColors.NeonPink
//    }
//
//    Box(
//        modifier = Modifier
//            .scale(pulse)
//            .clip(RoundedCornerShape(12.dp))
//            .background(statusColor.copy(alpha = 0.2f))
//            .border(
//                width = 1.dp,
//                color = statusColor,
//                shape = RoundedCornerShape(12.dp)
//            )
//            .padding(horizontal = 8.dp, vertical = 4.dp)
//    ) {
//        Text(
//            text = status.name,
//            style = MaterialTheme.typography.labelSmall.copy(
//                color = statusColor,
//                fontWeight = FontWeight.Bold
//            )
//        )
//    }
//}
//
//@Composable
//private fun GlowingIconButton(
//    onClick: () -> Unit,
//    icon: androidx.compose.ui.graphics.vector.ImageVector,
//    color: Color = CyberColors.NeonBlue
//) {
//    val glowAnimation = rememberInfiniteTransition(label = "iconGlow")
//    val glow by glowAnimation.animateFloat(
//        initialValue = 0.5f,
//        targetValue = 1f,
//        animationSpec = infiniteRepeatable(
//            animation = tween(1000, easing = FastOutSlowInEasing),
//            repeatMode = RepeatMode.Reverse
//        ),
//        label = "iconGlow"
//    )
//
//    IconButton(
//        onClick = onClick,
//        modifier = Modifier
//            .size(40.dp)
//            .background(
//                brush = Brush.radialGradient(
//                    colors = listOf(
//                        color.copy(alpha = 0.3f * glow),
//                        Color.Transparent
//                    )
//                ),
//                shape = CircleShape
//            )
//            .border(
//                width = 1.dp,
//                color = color.copy(alpha = glow),
//                shape = CircleShape
//            )
//    ) {
//        Icon(
//            icon,
//            contentDescription = null,
//            tint = color,
//            modifier = Modifier.size(20.dp)
//        )
//    }
//}
//
//@RequiresApi(Build.VERSION_CODES.O)
//@Composable
//private fun FloatingAddButton(
//    modifier: Modifier = Modifier,
//    scrollOffset: Int,
//    viewModel: MemberViewModel
//) {
//    val pulseAnimation = rememberInfiniteTransition(label = "fabPulse")
//    val pulse by pulseAnimation.animateFloat(
//        initialValue = 1f,
//        targetValue = 1.15f,
//        animationSpec = infiniteRepeatable(
//            animation = tween(1500, easing = FastOutSlowInEasing),
//            repeatMode = RepeatMode.Reverse
//        ),
//        label = "fabPulse"
//    )
//
//    val elevation by animateFloatAsState(
//        targetValue = if (scrollOffset > 100) 12f else 6f,
//        animationSpec = spring(
//            dampingRatio = Spring.DampingRatioMediumBouncy,
//            stiffness = Spring.StiffnessLow
//        ),
//        label = "fabElevation"
//    )
//
//    FloatingActionButton(
//        onClick = {
//            viewModel.addMember(
//                MemberEntity(
//                    id = UUID.randomUUID().toString(),
//                    name = "New Member",
//                    phoneNumber = "1234567890",
//                    fitnessGoal = "Weight Loss",
//                    startDate = LocalDate.now(),
//                    avatarColor = Color.Magenta.value.toLong(),
//                    notes = "No preference",
//                    status = "ACTIVE"
//                )
//            )
//        },
//        modifier = modifier
//            .scale(pulse)
//            .graphicsLayer {
//                shadowElevation = elevation
//            },
//        containerColor = CyberColors.NeonBlue,
//        contentColor = Color.Black,
//        shape = CircleShape
//    ) {
//        Icon(
//            Icons.Default.Add,
//            contentDescription = "Add Member",
//            modifier = Modifier.size(24.dp)
//        )
//    }
//}
//
//
//enum class MemberFilter(val title: String) {
//    TODAY("Today"),
//    THIS_WEEK("This Week"),
//    ALL("All Members")
//}