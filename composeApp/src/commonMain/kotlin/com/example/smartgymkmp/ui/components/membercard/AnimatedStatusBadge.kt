package com.example.smartgymkmp.ui.components.membercard

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.smartgymkmp.model.MemberStatus
import com.example.smartgymkmp.ui.theme.CyberColors

@Composable
fun AnimatedStatusBadge(status: MemberStatus) {
    val pulseAnimation = rememberInfiniteTransition(label = "pulse")
    val pulse by pulseAnimation.animateFloat(
        initialValue = 0.8f,
        targetValue = 1.2f,
        animationSpec = infiniteRepeatable(
            animation = tween(1500, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "statusPulse"
    )

    val statusColor = when (status) {
        MemberStatus.ACTIVE -> CyberColors.NeonGreen
        MemberStatus.CONVERTED -> CyberColors.NeonBlue
        MemberStatus.PENDING -> CyberColors.NeonPink
    }

    Box(
        modifier = Modifier
            .scale(pulse)
            .clip(RoundedCornerShape(12.dp))
            .background(statusColor.copy(alpha = 0.2f))
            .border(
                width = 1.dp,
                color = statusColor,
                shape = RoundedCornerShape(12.dp)
            )
            .padding(horizontal = 8.dp, vertical = 4.dp)
    ) {
        Text(
            text = status.name,
            style = MaterialTheme.typography.labelSmall.copy(
                color = statusColor,
                fontWeight = FontWeight.Bold
            )
        )
    }
}