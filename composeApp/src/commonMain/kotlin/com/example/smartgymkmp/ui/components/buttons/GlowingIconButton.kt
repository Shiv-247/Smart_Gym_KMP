package com.example.smartgymkmp.ui.components.buttons

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.example.smartgymkmp.ui.theme.CyberColors

@Composable
fun GlowingIconButton(
    onClick: () -> Unit,
    icon: ImageVector,
    color: Color = CyberColors.NeonBlue
) {
    val glowAnimation = rememberInfiniteTransition(label = "iconGlow")
    val glow by glowAnimation.animateFloat(
        initialValue = 0.5f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(1000, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "iconGlow"
    )

    IconButton(
        onClick = onClick,
        modifier = Modifier
            .size(40.dp)
            .background(
                brush = Brush.radialGradient(
                    colors = listOf(
                        color.copy(alpha = 0.3f * glow),
                        Color.Transparent
                    )
                ),
                shape = CircleShape
            )
            .border(
                width = 1.dp,
                color = color.copy(alpha = glow),
                shape = CircleShape
            )
    ) {
        Icon(
            icon,
            contentDescription = null,
            tint = color,
            modifier = Modifier.size(20.dp)
        )
    }
}