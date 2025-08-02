package com.example.smartgymkmp.ui.components.animations

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.smartgymkmp.ui.components.TypewriterText
import com.example.smartgymkmp.ui.components.buttons.GlowingIconButton
import com.example.smartgymkmp.ui.theme.CyberColors

@Composable
fun AnimatedHeader() {
    Column {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            TypewriterText(
                text = "MEMBER CONTROL PANEL",
                style = MaterialTheme.typography.headlineMedium.copy(
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = CyberColors.NeonBlue
                )
            )

            // Animated theme toggle
            GlowingIconButton(
                onClick = { },
                icon = Icons.Default.Settings
            )
        }

        // Subtitle with glow effect
        Text(
            "Advanced Member Management System v2.0",
            style = MaterialTheme.typography.bodyMedium.copy(
                color = CyberColors.NeonGreen.copy(alpha = 0.8f)
            ),
            modifier = Modifier.padding(top = 4.dp)
        )
    }
}