package com.example.smartgymkmp.ui.components.filterchips

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.smartgymkmp.model.MemberFilter
import com.example.smartgymkmp.ui.theme.CyberColors
import kotlinx.coroutines.delay

@Composable
fun FuturisticFilterChips(
    selectedFilter: MemberFilter,
    onFilterSelected: (MemberFilter) -> Unit
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        MemberFilter.values().forEachIndexed { index, filter ->
            val isSelected = selectedFilter == filter
            val animatedScale by animateFloatAsState(
                targetValue = if (isSelected) 1.1f else 1f,
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioMediumBouncy,
                    stiffness = Spring.StiffnessLow
                ),
                label = "chipScale"
            )

            LaunchedEffect(Unit) {
                delay(index * 150L)
            }

            Box(
                modifier = Modifier
                    .scale(animatedScale)
                    .clip(RoundedCornerShape(16.dp))
                    .background(
                        brush = if (isSelected) {
                            Brush.linearGradient(
                                colors = listOf(
                                    CyberColors.NeonBlue,
                                    CyberColors.NeonPurple
                                )
                            )
                        } else {
                            Brush.linearGradient(
                                colors = listOf(
                                    CyberColors.CardBg,
                                    CyberColors.CardBg.copy(alpha = 0.8f)
                                )
                            )
                        }
                    )
                    .border(
                        width = 1.dp,
                        color = if (isSelected) CyberColors.NeonBlue else CyberColors.GlowBlue.copy(
                            alpha = 0.5f
                        ),
                        shape = RoundedCornerShape(16.dp)
                    )
                    .clickable { onFilterSelected(filter) }
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            ) {
                Text(
                    filter.title,
                    color = if (isSelected) Color.Black else CyberColors.NeonBlue,
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp
                )
            }
        }
    }
}