package com.example.smartgymkmp.ui.components.buttons

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import com.benasher44.uuid.uuid4
import com.example.smartgymkmp.model.MemberEntity
import com.example.smartgymkmp.ui.theme.CyberColors
import com.example.smartgymkmp.viewmodels.MemberViewModel
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.todayIn

@Composable
fun FloatingAddButton(
    modifier: Modifier = Modifier,
    scrollOffset: Int,
    viewModel: MemberViewModel
) {
    val pulseAnimation = rememberInfiniteTransition(label = "fabPulse")
    val pulse by pulseAnimation.animateFloat(
        initialValue = 1f,
        targetValue = 1.15f,
        animationSpec = infiniteRepeatable(
            animation = tween(1500, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "fabPulse"
    )

    val elevation by animateFloatAsState(
        targetValue = if (scrollOffset > 100) 12f else 6f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        ),
        label = "fabElevation"
    )

    FloatingActionButton(
        onClick = {
            viewModel.addMember(
                MemberEntity(
                    id = uuid4().toString(),
                    name = "New Member",
                    phoneNumber = "1234567890",
                    fitnessGoal = "Weight Loss",
                    startDate = Clock.System.todayIn(TimeZone.currentSystemDefault()).toString(),
                    avatarColor = Color.Magenta.value.toLong(),
                    notes = "No preference",
                    status = "ACTIVE"
                )
            )
        },
        modifier = modifier
            .scale(pulse)
            .graphicsLayer {
                shadowElevation = elevation
            },
        containerColor = CyberColors.NeonBlue,
        contentColor = Color.Black,
        shape = CircleShape
    ) {
        Icon(
            Icons.Default.Add,
            contentDescription = "Add Member",
            modifier = Modifier.size(24.dp)
        )
    }
}