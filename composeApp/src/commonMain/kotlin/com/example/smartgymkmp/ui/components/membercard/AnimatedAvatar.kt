package com.example.smartgymkmp.ui.components.membercard

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.smartgymkmp.model.MemberStatus
import com.example.smartgymkmp.ui.theme.CyberColors

@Composable
fun AnimatedAvatar(name: String, status: MemberStatus) {
    val rotation by rememberInfiniteTransition(label = "avatar").animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(10000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "avatarRotation"
    )

    val statusColor = when (status) {
        MemberStatus.ACTIVE -> CyberColors.NeonGreen
        MemberStatus.CONVERTED -> CyberColors.NeonBlue
        MemberStatus.PENDING -> CyberColors.NeonPink
    }

    Box(
        modifier = Modifier
            .size(50.dp)
            .clip(CircleShape)
            .background(
                brush = Brush.radialGradient(
                    colors = listOf(
                        statusColor.copy(alpha = 0.3f),
                        statusColor.copy(alpha = 0.1f)
                    )
                )
            )
            .border(
                width = 2.dp,
                color = statusColor,
                shape = CircleShape
            )
            .rotate(rotation * 0.1f),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = name.split(" ").joinToString("") { it.first().toString() },
            style = MaterialTheme.typography.titleMedium.copy(
                fontWeight = FontWeight.Bold,
                color = statusColor
            )
        )
    }
}