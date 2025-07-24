package com.smartgym.ui.dashboard.composables

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LeadIntakeForm() {
    var name by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var goal by remember { mutableStateOf("") }
    var time by remember { mutableStateOf("") }
    var notes by remember { mutableStateOf("") }
    var expanded by remember { mutableStateOf(false) }
    var isLoading by remember { mutableStateOf(false) }
    var isSubmitted by remember { mutableStateOf(false) }

    val goals = listOf("Weight Loss", "Muscle Gain", "General Fitness", "Athletic Performance", "Rehabilitation", "Endurance Training")

    // Animation states
    var showForm by remember { mutableStateOf(false) }

    // Trigger form animation on composition
    LaunchedEffect(Unit) {
        delay(300)
        showForm = true
    }

    Box(modifier = Modifier.fillMaxSize()) {
        // Animated gradient background with breathing effect
        AnimatedBackgroundForForm()

        // Floating particles
        FloatingParticles()

        // Main content
        AnimatedVisibility(
            visible = showForm,
            enter = slideInVertically(
                initialOffsetY = { it },
                animationSpec = tween(1000, easing = FastOutSlowInEasing)
            ) + fadeIn(animationSpec = tween(1000))
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(20.dp)
            ) {
                // Futuristic Header
                FuturisticHeader()

                Spacer(modifier = Modifier.height(32.dp))

                // Glassmorphic form container
                GlassmorphicContainer {
                    Column(
                        modifier = Modifier.padding(24.dp)
                    ) {
                        // Form fields with sequential animations
                        FormFieldWithDelay(
                            delay = 200,
                            content = {
                                FuturisticTextField(
                                    value = name,
                                    onValueChange = { name = it },
                                    label = "NEURAL ID (Full Name)",
                                    icon = Icons.Default.Person,
                                    emoji = "🧑"
                                )
                            }
                        )

                        Spacer(modifier = Modifier.height(20.dp))

                        FormFieldWithDelay(
                            delay = 400,
                            content = {
                                FuturisticTextField(
                                    value = phone,
                                    onValueChange = { phone = it },
                                    label = "COMM LINK (Phone)",
                                    icon = Icons.Default.Phone,
                                    emoji = "📞",
                                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone)
                                )
                            }
                        )

                        Spacer(modifier = Modifier.height(20.dp))

                        FormFieldWithDelay(
                            delay = 600,
                            content = {
                                // Fitness Goal Dropdown
                                ExposedDropdownMenuBox(
                                    expanded = expanded,
                                    onExpandedChange = { expanded = !expanded }
                                ) {
                                    FuturisticTextField(
                                        value = goal,
                                        onValueChange = {},
                                        label = "MISSION OBJECTIVE",
                                        icon = Icons.Default.Face,
                                        emoji = "🎯",
                                        readOnly = true,
                                        trailingIcon = {
                                            Icon(
                                                Icons.Default.ArrowDropDown,
                                                contentDescription = null,
                                                tint = Color(0xFF00E5FF),
                                                modifier = Modifier.rotate(if (expanded) 180f else 0f)
                                            )
                                        },
                                        modifier = Modifier.menuAnchor()
                                    )

                                    ExposedDropdownMenu(
                                        expanded = expanded,
                                        onDismissRequest = { expanded = false },
                                        modifier = Modifier
                                            .background(
                                                Color(0xFF1A1A1A).copy(alpha = 0.95f),
                                                RoundedCornerShape(12.dp)
                                            )
                                            .border(
                                                1.dp,
                                                Color(0xFF00E5FF).copy(alpha = 0.3f),
                                                RoundedCornerShape(12.dp)
                                            )
                                    ) {
                                        goals.forEach { option ->
                                            DropdownMenuItem(
                                                text = {
                                                    Text(
                                                        option,
                                                        color = Color.White,
                                                        fontSize = 14.sp,
                                                        fontWeight = FontWeight.Medium
                                                    )
                                                },
                                                onClick = {
                                                    goal = option
                                                    expanded = false
                                                },
                                                modifier = Modifier.background(
                                                    Color.Transparent
                                                )
                                            )
                                        }
                                    }
                                }
                            }
                        )

                        Spacer(modifier = Modifier.height(20.dp))

                        FormFieldWithDelay(
                            delay = 800,
                            content = {
                                FuturisticTextField(
                                    value = time,
                                    onValueChange = { time = it },
                                    label = "OPTIMAL TIME SYNC",
                                    icon = Icons.Default.DateRange,
                                    emoji = "⏰"
                                )
                            }
                        )

                        Spacer(modifier = Modifier.height(20.dp))

                        FormFieldWithDelay(
                            delay = 1000,
                            content = {
                                FuturisticTextField(
                                    value = notes,
                                    onValueChange = { notes = it },
                                    label = "ADDITIONAL DATA",
                                    icon = Icons.Default.AccountBox,
                                    emoji = "📝",
                                    singleLine = false,
                                    modifier = Modifier.height(120.dp)
                                )
                            }
                        )
                    }
                }

                Spacer(modifier = Modifier.height(32.dp))
            }
        }

        // Cyberpunk Submit Button
        AnimatedVisibility(
            visible = showForm,
            enter = slideInVertically(
                initialOffsetY = { it },
                animationSpec = tween(1200, easing = FastOutSlowInEasing)
            ) + fadeIn(animationSpec = tween(1200))
        ) {
            CyberpunkSubmitButton(
                isLoading = isLoading,
                isSubmitted = isSubmitted,
                onClick = {
                    isLoading = true
                    // Simulate API call
                    LaunchedEffect(Unit) {
                        delay(2000)
                        isLoading = false
                        isSubmitted = true
                        delay(1000)
                        isSubmitted = false
                    }
                },
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(24.dp)
            )
        }
    }
}

@Composable
fun AnimatedBackgroundForForm() {
    val infiniteTransition = rememberInfiniteTransition(label = "background")
    val breathingScale by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 1.1f,
        animationSpec = infiniteRepeatable(
            animation = tween(4000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "breathing"
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .scale(breathingScale)
            .background(
                Brush.radialGradient(
                    colors = listOf(
                        Color(0xFF0D1117),
                        Color(0xFF1A1A2E),
                        Color(0xFF16213E),
                        Color(0xFF0F0F0F)
                    ),
                    radius = 1200f
                )
            )
    )
}

@Composable
fun FloatingParticles() {
    val particles = remember { (1..8).map { it } }

    particles.forEach { index ->
        val infiniteTransition = rememberInfiniteTransition(label = "particle$index")
        val offsetY by infiniteTransition.animateFloat(
            initialValue = (index * 100).toFloat(),
            targetValue = (index * 100 + 50).toFloat(),
            animationSpec = infiniteRepeatable(
                animation = tween(
                    durationMillis = 3000 + (index * 200),
                    easing = LinearEasing
                ),
                repeatMode = RepeatMode.Reverse
            ),
            label = "particleY$index"
        )

        val offsetX by infiniteTransition.animateFloat(
            initialValue = (index * 50).toFloat(),
            targetValue = (index * 50 + 30).toFloat(),
            animationSpec = infiniteRepeatable(
                animation = tween(
                    durationMillis = 2000 + (index * 300),
                    easing = LinearEasing
                ),
                repeatMode = RepeatMode.Reverse
            ),
            label = "particleX$index"
        )

        Box(
            modifier = Modifier
                .offset(x = offsetX.dp, y = offsetY.dp)
                .size(4.dp)
                .background(
                    Color(0xFF00E5FF).copy(alpha = 0.6f),
                    CircleShape
                )
        )
    }
}

@Composable
fun FuturisticHeader() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = "◈ AI GYM INTERFACE ◈",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF00E5FF),
            textAlign = TextAlign.Center,
            letterSpacing = 2.sp
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "NEURAL LEAD ACQUISITION PROTOCOL",
            fontSize = 14.sp,
            color = Color.White.copy(alpha = 0.7f),
            textAlign = TextAlign.Center,
            letterSpacing = 1.sp
        )
    }
}

@Composable
fun GlassmorphicContainer(
    content: @Composable () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                Color(0xFF1A1A1A).copy(alpha = 0.3f),
                RoundedCornerShape(20.dp)
            )
            .border(
                1.dp,
                Brush.linearGradient(
                    colors = listOf(
                        Color(0xFF00E5FF).copy(alpha = 0.5f),
                        Color(0xFF8B5CF6).copy(alpha = 0.5f),
                        Color(0xFF00E5FF).copy(alpha = 0.5f)
                    )
                ),
                RoundedCornerShape(20.dp)
            )
            .blur(0.5.dp)
    ) {
        content()
    }
}

@Composable
fun FormFieldWithDelay(
    delay: Long,
    content: @Composable () -> Unit
) {
    var visible by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        delay(delay)
        visible = true
    }

    AnimatedVisibility(
        visible = visible,
        enter = slideInHorizontally(
            initialOffsetX = { -it },
            animationSpec = tween(600, easing = FastOutSlowInEasing)
        ) + fadeIn(animationSpec = tween(600))
    ) {
        content()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FuturisticTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    icon: ImageVector,
    emoji: String,
    modifier: Modifier = Modifier,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    readOnly: Boolean = false,
    trailingIcon: @Composable (() -> Unit)? = null,
    singleLine: Boolean = true
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isFocused by interactionSource.collectIsFocusedAsState()

    // Neon glow animation
    val infiniteTransition = rememberInfiniteTransition(label = "glow")
    val glowAlpha by infiniteTransition.animateFloat(
        initialValue = 0.5f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(2000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "glow"
    )

    val focusScale by animateFloatAsState(
        targetValue = if (isFocused) 1.02f else 1f,
        animationSpec = tween(200),
        label = "focus"
    )

    Column {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(bottom = 8.dp)
        ) {
            Text(
                text = emoji,
                fontSize = 18.sp,
                modifier = Modifier.padding(end = 8.dp)
            )
            Text(
                text = label,
                fontSize = 12.sp,
                color = Color(0xFF00E5FF),
                fontWeight = FontWeight.Bold,
                letterSpacing = 0.5.sp
            )
        }

        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            leadingIcon = {
                Icon(
                    icon,
                    contentDescription = null,
                    tint = Color(0xFF00E5FF).copy(alpha = glowAlpha)
                )
            },
            trailingIcon = trailingIcon,
            modifier = modifier
                .fillMaxWidth()
                .scale(focusScale)
                .border(
                    width = if (isFocused) 2.dp else 1.dp,
                    brush = Brush.linearGradient(
                        colors = listOf(
                            Color(0xFF00E5FF).copy(alpha = if (isFocused) glowAlpha else 0.3f),
                            Color(0xFF8B5CF6).copy(alpha = if (isFocused) glowAlpha else 0.3f),
                            Color(0xFF00E5FF).copy(alpha = if (isFocused) glowAlpha else 0.3f)
                        )
                    ),
                    shape = RoundedCornerShape(12.dp)
                ),
            colors = OutlinedTextFieldDefaults.colors(
                cursorColor = Color(0xFF00E5FF),
                focusedBorderColor = Color.Transparent,
                unfocusedBorderColor = Color.Transparent,
                focusedContainerColor = Color(0xFF1A1A1A).copy(alpha = 0.8f),
                unfocusedContainerColor = Color(0xFF1A1A1A).copy(alpha = 0.8f),
                focusedTextColor = Color.White,
                unfocusedTextColor = Color.White.copy(alpha = 0.9f)
            ),
            keyboardOptions = keyboardOptions,
            readOnly = readOnly,
            singleLine = singleLine,
            interactionSource = interactionSource,
            shape = RoundedCornerShape(12.dp)
        )
    }
}

@Composable
fun CyberpunkSubmitButton(
    isLoading: Boolean,
    isSubmitted: Boolean,
    onClick: @Composable () -> Unit,
    modifier: Modifier = Modifier
) {
    val infiniteTransition = rememberInfiniteTransition(label = "button")
    val rippleAlpha by infiniteTransition.animateFloat(
        initialValue = 0.3f,
        targetValue = 0.8f,
        animationSpec = infiniteRepeatable(
            animation = tween(1500, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "ripple"
    )

    val rotationAngle by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(2000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "rotation"
    )

    FloatingActionButton(
        onClick = {
            if (!isLoading && !isSubmitted) onClick else {
                {}
            }
        },
        modifier = modifier
            .size(70.dp)
            .border(
                2.dp,
                Color(0xFF00E5FF).copy(alpha = rippleAlpha),
                CircleShape
            ),
        containerColor = if (isSubmitted) Color(0xFF00FF88) else Color(0xFF00E5FF),
        contentColor = Color.Black,
        elevation = FloatingActionButtonDefaults.elevation(
            defaultElevation = 8.dp,
            pressedElevation = 12.dp
        )
    ) {
        when {
            isLoading -> {
                CircularProgressIndicator(
                    modifier = Modifier
                        .size(24.dp)
                        .rotate(rotationAngle),
                    color = Color.Black,
                    strokeWidth = 3.dp
                )
            }
            isSubmitted -> {
                Icon(
                    Icons.Default.Check,
                    contentDescription = "Success",
                    modifier = Modifier.size(32.dp)
                )
            }
            else -> {
                Icon(
                    Icons.Default.Send,
                    contentDescription = "Submit Lead",
                    modifier = Modifier.size(28.dp)
                )
            }
        }
    }
}