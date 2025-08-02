package com.example.smartgymkmp.ui.components.animations

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import com.example.smartgymkmp.ui.theme.CyberColors
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun AnimatedGridBackground(offset: Float) {
    Canvas(
        modifier = Modifier
            .fillMaxSize()
            .graphicsLayer {
                translationX = sin(offset * 0.01f) * 10f
                translationY = cos(offset * 0.01f) * 10f
            }
    ) {
        val gridSize = 50.dp.toPx()
        val strokeWidth = 1.dp.toPx()

        // Draw animated grid lines
        for (i in 0 until (size.width / gridSize).toInt()) {
            drawLine(
                color = CyberColors.NeonBlue.copy(alpha = 0.1f),
                start = Offset(i * gridSize, 0f),
                end = Offset(i * gridSize, size.height),
                strokeWidth = strokeWidth
            )
        }

        for (i in 0 until (size.height / gridSize).toInt()) {
            drawLine(
                color = CyberColors.NeonBlue.copy(alpha = 0.1f),
                start = Offset(0f, i * gridSize),
                end = Offset(size.width, i * gridSize),
                strokeWidth = strokeWidth
            )
        }
    }
}