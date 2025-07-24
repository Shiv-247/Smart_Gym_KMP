//package com.smartgym.ui.dashboard.composables
//
//import android.os.Build
//import androidx.annotation.RequiresApi
//import androidx.compose.animation.core.*
//import androidx.compose.foundation.Canvas
//import androidx.compose.foundation.background
//import androidx.compose.foundation.border
//import androidx.compose.foundation.layout.*
//import androidx.compose.foundation.rememberScrollState
//import androidx.compose.foundation.shape.CircleShape
//import androidx.compose.foundation.shape.RoundedCornerShape
//import androidx.compose.foundation.verticalScroll
//import androidx.compose.material.icons.Icons
//import androidx.compose.material.icons.filled.*
//import androidx.compose.material3.*
//import androidx.compose.runtime.*
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.draw.rotate
//import androidx.compose.ui.draw.scale
//import androidx.compose.ui.draw.shadow
//import androidx.compose.ui.graphics.*
//import androidx.compose.ui.graphics.drawscope.DrawScope
//import androidx.compose.ui.graphics.vector.ImageVector
//import androidx.compose.ui.text.TextStyle
//import androidx.compose.ui.text.font.FontWeight
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.unit.sp
//import androidx.lifecycle.viewmodel.compose.viewModel
//import androidx.navigation.NavHostController
//import com.smartgym.data.model.MemberEntity
//import com.smartgym.viewmodel.MemberViewModel
//import kotlinx.coroutines.delay
//import java.time.LocalDate
//import java.time.format.DateTimeFormatter
//import java.time.temporal.ChronoUnit
//import kotlin.math.cos
//import kotlin.math.sin
//
//@RequiresApi(Build.VERSION_CODES.O)
//@Composable
//fun MemberProfileScreen(
//    navController: NavHostController,
//    modifier: Modifier = Modifier,
//    onEditMember: (String) -> Unit = {},
//    onDeleteMember: (String) -> Unit = {},
//    onCallMember: (String) -> Unit = {},
//    memberId: String,
//) {
//    // Animation states
//    var isVisible by remember { mutableStateOf(false) }
//    val viewModel: MemberViewModel = viewModel()
//    val member by viewModel.getMemberById(memberId).collectAsState(initial = null)
//    val scroll = rememberScrollState()
//    var isEditMode by remember { mutableStateOf(false) }
//    var name by remember { mutableStateOf("") }
//    var phone by remember { mutableStateOf("") }
//
//    LaunchedEffect(member) {
//        member?.let {
//            name = it.name
//            phone = it.phoneNumber
//        }
//    }
//
//    LaunchedEffect(Unit) {
//        isVisible = true
//    }
//
//    Box(
//        modifier = modifier
//            .fillMaxSize()
//            .verticalScroll(scroll)
//            .background(Color.Black)
//    ) {
//        // Animated background with particles
//        AnimatedBackground()
//
//        // Main content
//        Column(
//            modifier = Modifier
//                .fillMaxSize()
//                .padding(16.dp)
//        ) {
//            // Futuristic header
//            AnimatedHeader(isVisible = isVisible)
//
//            Spacer(modifier = Modifier.height(24.dp))
//
//            // Member profile card
//            member?.let { safeMember ->
//                FuturisticMemberCard(
//                    member = safeMember,
//                    isVisible = isVisible,
//                    onEdit = { isEditMode = true },
//                    onDelete = { onDeleteMember(safeMember.id) },
//                    onCall = { onCallMember(safeMember.phoneNumber) },
//                    isEditMode = isEditMode,
//                    name = name ?: "",
//                    phone = phone ?: "",
//                    onNameChange = { name = it },
//                    onPhoneChange = { phone = it },
//                    onUpdate = {
//                        viewModel.updateMember(
//                            MemberEntity(
//                                id = safeMember.id,
//                                name = name,
//                                phoneNumber = phone,
//                                fitnessGoal = safeMember.fitnessGoal,
//                                startDate = safeMember.startDate,
//                                avatarColor = safeMember.avatarColor.value.toLong(),
//                                notes = safeMember.notes,
//                                status = safeMember.status.name
//                            )
//                        )
//
//                        isEditMode = false
//                    }
//                )
//            }
//        }
//    }
//}
//
//@Composable
//private fun AnimatedBackground() {
//    val infiniteTransition = rememberInfiniteTransition(label = "background")
//
//    val gradientOffset by infiniteTransition.animateFloat(
//        initialValue = 0f,
//        targetValue = 1f,
//        animationSpec = infiniteRepeatable(
//            animation = tween(8000, easing = LinearEasing),
//            repeatMode = RepeatMode.Reverse
//        ),
//        label = "gradient"
//    )
//
//    val particleRotation by infiniteTransition.animateFloat(
//        initialValue = 0f,
//        targetValue = 360f,
//        animationSpec = infiniteRepeatable(
//            animation = tween(20000, easing = LinearEasing)
//        ),
//        label = "particles"
//    )
//
//    Box(
//        modifier = Modifier
//            .fillMaxSize()
//            .background(
//                brush = Brush.radialGradient(
//                    colors = listOf(
//                        Color(0xFF0A0B1E).copy(alpha = 0.9f),
//                        Color(0xFF1A1B3A).copy(alpha = 0.8f),
//                        Color(0xFF2D1B69).copy(alpha = 0.7f),
//                        Color.Black
//                    ),
//                    center = androidx.compose.ui.geometry.Offset(
//                        x = 0.3f + gradientOffset * 0.4f,
//                        y = 0.2f + gradientOffset * 0.6f
//                    )
//                )
//            )
//    ) {
//        // Floating particles
//        Canvas(
//            modifier = Modifier.fillMaxSize()
//        ) {
//            drawFloatingParticles(particleRotation)
//        }
//    }
//}
//
//private fun DrawScope.drawFloatingParticles(rotation: Float) {
//    val particles = 15
//    val centerX = size.width / 2
//    val centerY = size.height / 2
//
//    for (i in 0 until particles) {
//        val angle = (i * 360f / particles + rotation) * (kotlin.math.PI / 180f)
//        val radius = 200f + (i % 3) * 100f
//        val x = centerX + cos(angle) * radius
//        val y = centerY + sin(angle) * radius
//
//        drawCircle(
//            color = Color(0xFF00FFFF).copy(alpha = 0.1f + (i % 3) * 0.1f),
//            radius = 2f + (i % 3) * 1f,
//            center = androidx.compose.ui.geometry.Offset(x.toFloat(), y.toFloat())
//        )
//    }
//}
//
//@Composable
//private fun AnimatedHeader(isVisible: Boolean) {
//    val slideIn by animateFloatAsState(
//        targetValue = if (isVisible) 0f else -100f,
//        animationSpec = spring(
//            dampingRatio = Spring.DampingRatioMediumBouncy,
//            stiffness = Spring.StiffnessLow
//        ),
//        label = "header_slide"
//    )
//
//    val glowPulse by rememberInfiniteTransition(label = "glow").animateFloat(
//        initialValue = 0.3f,
//        targetValue = 1f,
//        animationSpec = infiniteRepeatable(
//            animation = tween(2000, easing = FastOutSlowInEasing),
//            repeatMode = RepeatMode.Reverse
//        ),
//        label = "glow_pulse"
//    )
//
//    Card(
//        modifier = Modifier
//            .fillMaxWidth()
//            .height(120.dp)
//            .offset(y = slideIn.dp)
//            .shadow(
//                elevation = 20.dp,
//                shape = RoundedCornerShape(24.dp),
//                ambientColor = Color(0xFF00FFFF),
//                spotColor = Color(0xFF00FFFF)
//            )
//            .border(
//                width = 1.dp,
//                brush = Brush.horizontalGradient(
//                    colors = listOf(
//                        Color(0xFF00FFFF).copy(alpha = glowPulse),
//                        Color(0xFFFF00FF).copy(alpha = glowPulse),
//                        Color(0xFF00FFFF).copy(alpha = glowPulse)
//                    )
//                ),
//                shape = RoundedCornerShape(24.dp)
//            ),
//        shape = RoundedCornerShape(24.dp),
//        colors = CardDefaults.cardColors(
//            containerColor = Color(0xFF0A0B1E).copy(alpha = 0.9f)
//        )
//    ) {
//        Box(
//            modifier = Modifier
//                .fillMaxSize()
//                .background(
//                    brush = Brush.verticalGradient(
//                        colors = listOf(
//                            Color(0xFF1A1B3A).copy(alpha = 0.8f),
//                            Color(0xFF0A0B1E).copy(alpha = 0.9f)
//                        )
//                    )
//                )
//                .padding(24.dp)
//        ) {
//            Row(
//                modifier = Modifier.fillMaxWidth(),
//                horizontalArrangement = Arrangement.SpaceBetween,
//                verticalAlignment = Alignment.CenterVertically
//            ) {
//                Column {
//                    TypewriterText(
//                        text = "MEMBER PROFILE",
//                        style = MaterialTheme.typography.headlineSmall.copy(
//                            color = Color(0xFF00FFFF),
//                            fontWeight = FontWeight.Bold,
//                            letterSpacing = 2.sp
//                        ),
//                        isVisible = isVisible
//                    )
//                    Spacer(modifier = Modifier.height(4.dp))
//                    TypewriterText(
//                        text = "NEURAL INTERFACE v2.1",
//                        style = MaterialTheme.typography.bodyMedium.copy(
//                            color = Color(0xFF888888),
//                            letterSpacing = 1.sp
//                        ),
//                        isVisible = isVisible,
//                        delay = 1000
//                    )
//                }
//
//                PulsatingBadge(count = "24")
//            }
//        }
//    }
//}
//
//@Composable
//private fun TypewriterText(
//    text: String,
//    style: androidx.compose.ui.text.TextStyle,
//    isVisible: Boolean,
//    delay: Long = 500
//) {
//    var visibleText by remember { mutableStateOf("") }
//
//    LaunchedEffect(isVisible) {
//        if (isVisible) {
//            delay(delay)
//            text.forEachIndexed { index, _ ->
//                visibleText = text.take(index + 1)
//                delay(50)
//            }
//        }
//    }
//
//    Text(
//        text = visibleText,
//        style = style
//    )
//}
//
//@Composable
//private fun PulsatingBadge(count: String) {
//    val scale by rememberInfiniteTransition(label = "badge").animateFloat(
//        initialValue = 0.9f,
//        targetValue = 1.1f,
//        animationSpec = infiniteRepeatable(
//            animation = tween(1500, easing = FastOutSlowInEasing),
//            repeatMode = RepeatMode.Reverse
//        ),
//        label = "badge_scale"
//    )
//
//    Box(
//        modifier = Modifier
//            .scale(scale)
//            .size(60.dp)
//            .background(
//                brush = Brush.radialGradient(
//                    colors = listOf(
//                        Color(0xFF00FFFF),
//                        Color(0xFF0080FF)
//                    )
//                ),
//                shape = CircleShape
//            )
//            .shadow(
//                elevation = 12.dp,
//                shape = CircleShape,
//                ambientColor = Color(0xFF00FFFF),
//                spotColor = Color(0xFF00FFFF)
//            ),
//        contentAlignment = Alignment.Center
//    ) {
//        Text(
//            text = count,
//            style = MaterialTheme.typography.titleMedium.copy(
//                color = Color.White,
//                fontWeight = FontWeight.Bold
//            )
//        )
//    }
//}
//
//@RequiresApi(Build.VERSION_CODES.O)
//@Composable
//private fun FuturisticMemberCard(
//    member: Member,
//    isVisible: Boolean,
//    onEdit: () -> Unit,
//    onDelete: () -> Unit,
//    onCall: () -> Unit,
//    isEditMode: Boolean,
//    name: String,
//    phone: String,
//    onNameChange: (String) -> Unit,
//    onPhoneChange: (String) -> Unit,
//    onUpdate: () -> Unit
//) {
//    val cardSlide by animateFloatAsState(
//        targetValue = if (isVisible) 0f else 100f,
//        animationSpec = spring(
//            dampingRatio = Spring.DampingRatioMediumBouncy,
//            stiffness = Spring.StiffnessLow
//        ),
//        label = "card_slide"
//    )
//
//    Card(
//        modifier = Modifier
//            .fillMaxWidth()
//            .offset(y = cardSlide.dp)
//            .shadow(
//                elevation = 25.dp,
//                shape = RoundedCornerShape(28.dp),
//                ambientColor = Color(0xFF00FFFF),
//                spotColor = Color(0xFF00FFFF)
//            )
//            .border(
//                width = 1.dp,
//                brush = Brush.linearGradient(
//                    colors = listOf(
//                        Color(0xFF00FFFF).copy(alpha = 0.5f),
//                        Color(0xFFFF00FF).copy(alpha = 0.5f),
//                        Color(0xFF00FF00).copy(alpha = 0.5f)
//                    )
//                ),
//                shape = RoundedCornerShape(28.dp)
//            ),
//        shape = RoundedCornerShape(28.dp),
//        colors = CardDefaults.cardColors(
//            containerColor = Color(0xFF0A0B1E).copy(alpha = 0.95f)
//        )
//    ) {
//        Column(
//            modifier = Modifier
//                .fillMaxWidth()
//                .background(
//                    brush = Brush.verticalGradient(
//                        colors = listOf(
//                            Color(0xFF1A1B3A).copy(alpha = 0.8f),
//                            Color(0xFF0A0B1E).copy(alpha = 0.95f)
//                        )
//                    )
//                )
//                .padding(24.dp)
//        ) {
//            // Animated avatar and name section
//            AnimatedProfileHeader(
//                avatarColor = member.avatarColor,
//                name = name,
//                fitnessGoal = member.fitnessGoal,
//                isVisible = isVisible
//            )
//
//
//            Spacer(modifier = Modifier.height(24.dp))
//
//            // Status chip
//            AnimatedStatusChip(status = member.status, isVisible = isVisible)
//
//            Spacer(modifier = Modifier.height(24.dp))
//
//            // Info rows with staggered animation
//            AnimatedInfoRows(
//                member = member,
//                isVisible = isVisible,
//                isEditMode = isEditMode,
//                name = name,
//                onNameChange = { onNameChange(it) },
//                phone = phone,
//                onPhoneChange = { onPhoneChange(it) }
//            )
//
//
//            Spacer(modifier = Modifier.height(24.dp))
//
//            // Progress bars
//            AnimatedProgressBars(isVisible = isVisible)
//
//            Spacer(modifier = Modifier.height(24.dp))
//
//            // Action buttons
//            AnimatedActionButtons(
//                onEdit = onEdit,
//                onDelete = onDelete,
//                onCall = onCall,
//                isVisible = isVisible,
//                isEditMode = isEditMode,
//                onSave = onUpdate
//
//            )
//        }
//    }
//}
//
//@Composable
//private fun AnimatedProfileHeader(
//    avatarColor: Color,
//    name: String,
//    fitnessGoal: String,
//    isVisible: Boolean
//) {
//    val avatarRotation by rememberInfiniteTransition(label = "avatar").animateFloat(
//        initialValue = -5f,
//        targetValue = 5f,
//        animationSpec = infiniteRepeatable(
//            animation = tween(3000, easing = FastOutSlowInEasing),
//            repeatMode = RepeatMode.Reverse
//        ),
//        label = "avatar_rotation"
//    )
//
//    val avatarScale by animateFloatAsState(
//        targetValue = if (isVisible) 1f else 0f,
//        animationSpec = spring(
//            dampingRatio = Spring.DampingRatioMediumBouncy,
//            stiffness = Spring.StiffnessLow
//        ),
//        label = "avatar_scale"
//    )
//
//    Row(
//        modifier = Modifier.fillMaxWidth(),
//        horizontalArrangement = Arrangement.spacedBy(16.dp),
//        verticalAlignment = Alignment.CenterVertically
//    ) {
//        // Rotating avatar with glow
//        Box(
//            modifier = Modifier
//                .size(80.dp)
//                .scale(avatarScale)
//                .shadow(
//                    elevation = 20.dp,
//                    shape = CircleShape,
//                    ambientColor = avatarColor,
//                    spotColor = avatarColor
//                )
//                .background(
//                    brush = Brush.radialGradient(
//                        colors = listOf(
//                            avatarColor,
//                            avatarColor.copy(alpha = 0.7f),
//                            Color.Transparent
//                        ),
//                        radius = 100f
//                    ),
//                    shape = CircleShape
//                )
//                .rotate(avatarRotation),
//            contentAlignment = Alignment.Center
//        ) {
//            Text(
//                text = name.firstOrNull()?.toString() ?: "",
//                style = MaterialTheme.typography.headlineLarge.copy(
//                    color = Color.White,
//                    fontWeight = FontWeight.Bold,
//                    fontSize = 32.sp
//                )
//            )
//        }
//
//        Column(
//            modifier = Modifier.weight(1f)
//        ) {
//            // Kinetic name animation
//            KineticText(
//                text = name.uppercase(),
//                style = MaterialTheme.typography.headlineSmall.copy(
//                    color = Color(0xFF00FFFF),
//                    fontWeight = FontWeight.Bold,
//                    letterSpacing = 1.sp
//                ),
//                isVisible = isVisible
//            )
//
//            Spacer(modifier = Modifier.height(8.dp))
//
//            KineticText(
//                text = fitnessGoal.uppercase(),
//                style = MaterialTheme.typography.bodyMedium.copy(
//                    color = Color(0xFFFF00FF),
//                    letterSpacing = 0.5.sp
//                ),
//                isVisible = isVisible,
//                delay = 800
//            )
//        }
//    }
//}
//
//@Composable
//private fun KineticText(
//    text: String,
//    style: TextStyle,
//    isVisible: Boolean,
//    delay: Long = 500
//) {
//    var animatedText by remember(text) { mutableStateOf("") }
//
//    LaunchedEffect(text, isVisible) {
//        if (isVisible) {
//            delay(delay)
//            animatedText = ""
//            text.forEachIndexed { index, _ ->
//                animatedText = text.take(index + 1)
//                delay(30)
//            }
//        }
//    }
//
//    Text(
//        text = animatedText,
//        style = style
//    )
//}
//
//
//@Composable
//private fun AnimatedStatusChip(status: MemberStatus, isVisible: Boolean) {
//    val glowIntensity by rememberInfiniteTransition(label = "status_glow").animateFloat(
//        initialValue = 0.5f,
//        targetValue = 1f,
//        animationSpec = infiniteRepeatable(
//            animation = tween(2000, easing = FastOutSlowInEasing),
//            repeatMode = RepeatMode.Reverse
//        ),
//        label = "glow_intensity"
//    )
//
//    val slideIn by animateFloatAsState(
//        targetValue = if (isVisible) 0f else -50f,
//        animationSpec = spring(
//            dampingRatio = Spring.DampingRatioMediumBouncy,
//            stiffness = Spring.StiffnessLow
//        ),
//        label = "status_slide"
//    )
//
//    val (backgroundColor, textColor, glowColor) = when (status) {
//        MemberStatus.ACTIVE -> Triple(
//            Color(0xFF00FF00).copy(alpha = 0.2f),
//            Color(0xFF00FF00),
//            Color(0xFF00FF00)
//        )
//        MemberStatus.PENDING -> Triple(
//            Color(0xFFFFFF00).copy(alpha = 0.2f),
//            Color(0xFFFFFF00),
//            Color(0xFFFFFF00)
//        )
//        MemberStatus.CONVERTED -> Triple(
//            Color(0xFF00FFFF).copy(alpha = 0.2f),
//            Color(0xFF00FFFF),
//            Color(0xFF00FFFF)
//        )
//    }
//
//    Box(
//        modifier = Modifier
//            .offset(x = slideIn.dp)
//            .shadow(
//                elevation = 15.dp,
//                shape = RoundedCornerShape(20.dp),
//                ambientColor = glowColor.copy(alpha = glowIntensity),
//                spotColor = glowColor.copy(alpha = glowIntensity)
//            )
//            .background(
//                color = backgroundColor,
//                shape = RoundedCornerShape(20.dp)
//            )
//            .border(
//                width = 1.dp,
//                color = textColor.copy(alpha = glowIntensity),
//                shape = RoundedCornerShape(20.dp)
//            )
//            .padding(horizontal = 16.dp, vertical = 8.dp)
//    ) {
//        Row(
//            horizontalArrangement = Arrangement.spacedBy(8.dp),
//            verticalAlignment = Alignment.CenterVertically
//        ) {
//            // Pulsating dot
//            Box(
//                modifier = Modifier
//                    .size(10.dp)
//                    .scale(glowIntensity)
//                    .background(textColor, CircleShape)
//                    .shadow(
//                        elevation = 8.dp,
//                        shape = CircleShape,
//                        ambientColor = glowColor,
//                        spotColor = glowColor
//                    )
//            )
//
//            Text(
//                text = status.name,
//                style = MaterialTheme.typography.labelMedium.copy(
//                    color = textColor,
//                    fontWeight = FontWeight.Bold,
//                    letterSpacing = 1.sp
//                )
//            )
//        }
//    }
//}
//
//@RequiresApi(Build.VERSION_CODES.O)
//@Composable
//private fun AnimatedInfoRows(
//    member: Member,
//    isVisible: Boolean,
//    isEditMode: Boolean,
//    name: String,
//    onNameChange: (String) -> Unit,
//    phone: String,
//    onPhoneChange: (String) -> Unit
//) {
//    // Show editable fields
//    val infoItems = listOf(
//        Triple(Icons.Default.Person, "Member Name", name),
//        Triple(Icons.Default.DateRange, "REGISTRATION", member.startDate.format(DateTimeFormatter.ofPattern("MMM dd, yyyy")).uppercase()),
//        Triple(Icons.Default.Phone, "Phone No", phone),
//    )
//
//    infoItems.forEachIndexed { index, (icon, label, value) ->
//        AnimatedInfoRow(
//            icon = icon,
//            label = label,
//            value = value,
//            isVisible = isVisible,
//            delay = 1000 + index * 200L,
//            slideFromRight = index % 2 == 0,
//            isEditMode = isEditMode && label != "REGISTRATION",
//            onValueChange = {
//                when (label) {
//                    "Member Name" -> onNameChange(it)
//                    "Phone No" -> onPhoneChange(it)
//                    // REGISTRATION is not editable
//                }
//            }
//        )
//
//        if (index < infoItems.size - 1) {
//            Spacer(modifier = Modifier.height(16.dp))
//        }
//    }
//}
//
//
//@Composable
//private fun AnimatedInfoRow(
//    icon: ImageVector,
//    label: String,
//    value: String,
//    isVisible: Boolean,
//    delay: Long,
//    slideFromRight: Boolean,
//    isEditMode: Boolean,
//    onValueChange: (String) -> Unit
//) {
//    val startOffset = if (slideFromRight) 100f else -100f
//    val offsetX = remember { Animatable(startOffset) }
//
//    LaunchedEffect(isVisible) {
//        if (isVisible) {
//            delay(delay)
//            offsetX.animateTo(
//                targetValue = 0f,
//                animationSpec = spring(
//                    dampingRatio = Spring.DampingRatioMediumBouncy,
//                    stiffness = Spring.StiffnessLow
//                )
//            )
//        } else {
//            offsetX.snapTo(startOffset)
//        }
//    }
//
//    Row(
//        modifier = Modifier
//            .fillMaxWidth()
//            .offset(x = offsetX.value.dp)
//            .background(
//                color = Color(0xFF1A1B3A).copy(alpha = 0.3f),
//                shape = RoundedCornerShape(12.dp)
//            )
//            .border(
//                width = 1.dp,
//                color = Color(0xFF00FFFF).copy(alpha = 0.3f),
//                shape = RoundedCornerShape(12.dp)
//            )
//            .padding(16.dp),
//        horizontalArrangement = Arrangement.spacedBy(12.dp),
//        verticalAlignment = Alignment.CenterVertically
//    ) {
//        Icon(
//            imageVector = icon,
//            contentDescription = null,
//            tint = Color(0xFF00FFFF),
//            modifier = Modifier.size(24.dp)
//        )
//
//        Column(
//            modifier = Modifier.weight(1f)
//        ) {
//            Text(
//                text = label,
//                style = MaterialTheme.typography.bodySmall.copy(
//                    color = Color(0xFF888888),
//                    fontWeight = FontWeight.Medium,
//                    letterSpacing = 1.sp
//                )
//            )
//
//            if (isEditMode) {
//                OutlinedTextField(
//                    value = value,
//                    onValueChange = onValueChange,
//                    textStyle = MaterialTheme.typography.bodyMedium.copy(
//                        color = Color.White,
//                        fontWeight = FontWeight.Bold
//                    ),
//                    singleLine = true,
//                    colors = OutlinedTextFieldDefaults.colors(
//                        focusedBorderColor = Color(0xFF00FFFF),
//                        unfocusedBorderColor = Color(0xFF444444),
//                        cursorColor = Color.Cyan
//                    )
//                )
//            } else {
//                Text(
//                    text = value,
//                    style = MaterialTheme.typography.bodyMedium.copy(
//                        color = Color.White,
//                        fontWeight = FontWeight.Bold
//                    )
//                )
//            }
//        }
//    }
//}
//
//
//
//@Composable
//private fun AnimatedProgressBars(isVisible: Boolean) {
//    val progressData = listOf(
//        Triple("CONSISTENCY", 0.85f, Color(0xFF00FF00)),
//        Triple("ATTENDANCE", 0.92f, Color(0xFF00FFFF)),
//        Triple("PERFORMANCE", 0.78f, Color(0xFFFF00FF))
//    )
//
//    Column(
//        verticalArrangement = Arrangement.spacedBy(12.dp)
//    ) {
//        Text(
//            text = "BIOMETRIC DATA",
//            style = MaterialTheme.typography.titleMedium.copy(
//                color = Color(0xFF00FFFF),
//                fontWeight = FontWeight.Bold,
//                letterSpacing = 2.sp
//            )
//        )
//
//        progressData.forEachIndexed { index, (label, progress, color) ->
//            AnimatedProgressBar(
//                label = label,
//                progress = progress,
//                color = color,
//                isVisible = isVisible,
//                delay = 1500 + index * 200L
//            )
//        }
//    }
//}
//
//@Composable
//private fun AnimatedProgressBar(
//    label: String,
//    progress: Float,
//    color: Color,
//    isVisible: Boolean,
//    delay: Long
//) {
//    var animatedProgress by remember { mutableStateOf(0f) }
//
//    LaunchedEffect(isVisible) {
//        if (isVisible) {
//            delay(delay)
//            animatedProgress = progress
//        }
//    }
//
//    val animatedProgressValue by animateFloatAsState(
//        targetValue = animatedProgress,
//        animationSpec = tween(1500, easing = FastOutSlowInEasing),
//        label = "progress_value"
//    )
//
//    Column(
//        verticalArrangement = Arrangement.spacedBy(4.dp)
//    ) {
//        Row(
//            modifier = Modifier.fillMaxWidth(),
//            horizontalArrangement = Arrangement.SpaceBetween
//        ) {
//            Text(
//                text = label,
//                style = MaterialTheme.typography.bodySmall.copy(
//                    color = Color(0xFF888888),
//                    letterSpacing = 1.sp
//                )
//            )
//            Text(
//                text = "${(animatedProgressValue * 100).toInt()}%",
//                style = MaterialTheme.typography.bodySmall.copy(
//                    color = color,
//                    fontWeight = FontWeight.Bold
//                )
//            )
//        }
//
//        Box(
//            modifier = Modifier
//                .fillMaxWidth()
//                .height(8.dp)
//                .background(
//                    color = Color(0xFF1A1B3A),
//                    shape = RoundedCornerShape(4.dp)
//                )
//        ) {
//            Box(
//                modifier = Modifier
//                    .fillMaxHeight()
//                    .fillMaxWidth(animatedProgressValue)
//                    .background(
//                        brush = Brush.horizontalGradient(
//                            colors = listOf(
//                                color.copy(alpha = 0.7f),
//                                color
//                            )
//                        ),
//                        shape = RoundedCornerShape(4.dp)
//                    )
//                    .shadow(
//                        elevation = 8.dp,
//                        shape = RoundedCornerShape(4.dp),
//                        ambientColor = color,
//                        spotColor = color
//                    )
//            )
//        }
//    }
//}
//
//@Composable
//private fun AnimatedActionButtons(
//    onEdit: () -> Unit,
//    onDelete: () -> Unit,
//    onCall: () -> Unit,
//    onSave: () -> Unit,
//    isVisible: Boolean,
//    isEditMode: Boolean
//) {
//    val buttonSlide by animateFloatAsState(
//        targetValue = if (isVisible) 0f else 100f,
//        animationSpec = spring(
//            dampingRatio = Spring.DampingRatioMediumBouncy,
//            stiffness = Spring.StiffnessLow
//        ),
//        label = "button_slide"
//    )
//
//    Row(
//        modifier = Modifier
//            .fillMaxWidth()
//            .offset(y = buttonSlide.dp),
//        horizontalArrangement = Arrangement.SpaceEvenly
//    ) {
//        if (isEditMode) {
//            CyberButton(
//                onClick = onSave,
//                icon = Icons.Default.Check,
//                label = "SAVE",
//                color = Color(0xFF00FFAA)
//            )
//        } else {
//            CyberButton(
//                onClick = onCall,
//                icon = Icons.Default.Phone,
//                label = "CALL",
//                color = Color(0xFF00FF00)
//            )
//
//            CyberButton(
//                onClick = onEdit,
//                icon = Icons.Default.Edit,
//                label = "EDIT",
//                color = Color(0xFF00FFFF)
//            )
//
//            CyberButton(
//                onClick = onDelete,
//                icon = Icons.Default.Delete,
//                label = "DELETE",
//                color = Color(0xFFFF0040)
//            )
//        }
//    }
//}
//
//
//@Composable
//private fun CyberButton(
//    onClick: () -> Unit,
//    icon: ImageVector,
//    label: String,
//    color: Color
//) {
//    var isPressed by remember { mutableStateOf(false) }
//
//    val scale by animateFloatAsState(
//        targetValue = if (isPressed) 0.95f else 1f,
//        animationSpec = spring(
//            dampingRatio = Spring.DampingRatioMediumBouncy,
//            stiffness = Spring.StiffnessHigh
//        ),
//        label = "button_scale"
//    )
//
//    val glowIntensity by rememberInfiniteTransition(label = "button_glow").animateFloat(
//        initialValue = 0.5f,
//        targetValue = 1f,
//        animationSpec = infiniteRepeatable(
//            animation = tween(2000, easing = FastOutSlowInEasing),
//            repeatMode = RepeatMode.Reverse
//        ),
//        label = "glow_intensity"
//    )
//
//    Button(
//        onClick = {
//            isPressed = true
//            onClick()
//            isPressed = false
//        },
//        modifier = Modifier
//            .scale(scale)
//            .shadow(
//                elevation = 15.dp,
//                shape = RoundedCornerShape(16.dp),
//                ambientColor = color.copy(alpha = glowIntensity),
//                spotColor = color.copy(alpha = glowIntensity)
//            )
//            .border(
//                width = 1.dp,
//                color = color.copy(alpha = glowIntensity),
//                shape = RoundedCornerShape(16.dp)
//            ),
//        shape = RoundedCornerShape(16.dp),
//        colors = ButtonDefaults.buttonColors(
//            containerColor = color.copy(alpha = 0.1f),
//            contentColor = color
//        ),
//        contentPadding = PaddingValues(horizontal = 20.dp, vertical = 12.dp)
//    ) {
//        Icon(
//            imageVector = icon,
//            contentDescription = label,
//            modifier = Modifier
//                .size(20.dp)
//                .padding(end = 8.dp)
//        )
//        Text(text = label, style = MaterialTheme.typography.bodyMedium)
//    }
//}
//
//
//// Data classes
//data class Member @RequiresApi(Build.VERSION_CODES.O) constructor(
//    val id: String,
//    val name: String,
//    val phoneNumber: String,
//    val fitnessGoal: String = "General Fitness", // 🟢 default
//    val startDate: LocalDate,
//    val avatarColor: Color = Color(0xFF90CAF9),  // 🟢 light blue default
//    val notes: String = "",
//    val status: MemberStatus = MemberStatus.ACTIVE,
//    val age: Int = 0,
//    val activePlan: String = "",
//)
//
//
//
//enum class MemberStatus {
//    ACTIVE, PENDING, CONVERTED
//}
//
//
//@RequiresApi(Build.VERSION_CODES.O)
//fun MemberEntity.toMember(): Member {
//    return Member(
//        id = id,
//        name = name,
//        phoneNumber = phoneNumber,
//        fitnessGoal = fitnessGoal,
//        startDate = startDate,
//        avatarColor = Color(avatarColor), // converting Long → Color
//        notes = notes,
//        status = MemberStatus.valueOf(status) // converting String → Enum
//    )
//}
