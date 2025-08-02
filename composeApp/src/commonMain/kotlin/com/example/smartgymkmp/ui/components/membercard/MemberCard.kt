package com.example.smartgymkmp.ui.components.membercard

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.smartgymkmp.model.MemberEntity
import com.example.smartgymkmp.model.MemberStatus
import com.example.smartgymkmp.ui.components.TypewriterText
import com.example.smartgymkmp.ui.components.buttons.GlowingIconButton
import com.example.smartgymkmp.ui.theme.CyberColors
import com.example.smartgymkmp.viewmodels.MemberViewModel

@Composable
fun AnimatedMemberCard(
    member: MemberEntity,
    index: Int,
    parallaxOffset: Int,
    onCall: () -> Unit,
    onEdit: () -> Unit,
    navController: NavHostController,
    onClick: () -> Unit,
    viewModel: MemberViewModel,
) {
    var isVisible by remember { mutableStateOf(false) }
    var isPressed by remember { mutableStateOf(false) }

    val scale by animateFloatAsState(
        targetValue = if (isPressed) 0.95f else 1f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessHigh
        ),
        label = "cardScale"
    )

    val borderAnimation = rememberInfiniteTransition(label = "borderGlow")
    val glowIntensity by borderAnimation.animateFloat(
        initialValue = 0.3f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(2000, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "glowIntensity"
    )
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .scale(scale)
            .graphicsLayer {
                translationX = (parallaxOffset * 0.1f) * (index % 2 - 0.5f)
            }
            .pointerInput(Unit) {
                detectTapGestures(
                    onPress = {
                        isPressed = true
                        tryAwaitRelease()
                        isPressed = false
                    },
                    onTap = {
                        navController.navigate("memberProfile/${member.id}")
                    }
                )
            }
            .clickable { onClick() }
            .border(
                width = 2.dp,
                brush = Brush.linearGradient(
                    colors = listOf(
                        CyberColors.NeonBlue.copy(alpha = glowIntensity),
                        CyberColors.NeonPurple.copy(alpha = glowIntensity * 0.8f),
                        CyberColors.NeonGreen.copy(alpha = glowIntensity * 0.6f)
                    )
                ),
                shape = RoundedCornerShape(16.dp)
            ),
        colors = CardDefaults.cardColors(
            containerColor = CyberColors.CardBg.copy(alpha = 0.9f)
        ),
        shape = RoundedCornerShape(16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Animated avatar
                AnimatedAvatar(member.name, MemberStatus.valueOf(member.status))

                Column {
                    // Typewriter name
                    TypewriterText(
                        text = member.name,
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold,
                            color = CyberColors.NeonBlue
                        )
                    )

                    // Animated goal
                    Text(
                        "TARGET: ${member.fitnessGoal}",
                        style = MaterialTheme.typography.bodyMedium.copy(
                            color = CyberColors.NeonGreen.copy(alpha = 0.8f)
                        )
                    )

                    Text(
                        "JOINED: ${member.startDate}",
                        style = MaterialTheme.typography.bodySmall.copy(
                            color = Color.White.copy(alpha = 0.6f)
                        )
                    )

                    Text(
                        "Phone no: ${member.phoneNumber}",
                        style = MaterialTheme.typography.bodySmall.copy(
                            color = Color.White.copy(alpha = 0.6f)
                        )
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    // Animated status badge
                    AnimatedStatusBadge(MemberStatus.valueOf(member.status))

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 20.dp)
                    ) {
                        GlowingIconButton(
                            onClick = onCall,
                            icon = Icons.Default.Call,
                            color = CyberColors.NeonGreen
                        )
                        Spacer(modifier = Modifier.width(20.dp))
                        GlowingIconButton(
                            onClick = { viewModel.deleteMember(member) },
                            icon = Icons.Default.Delete,
                            color = CyberColors.NeonPink
                        )
                    }
                }
            }
        }
    }
}