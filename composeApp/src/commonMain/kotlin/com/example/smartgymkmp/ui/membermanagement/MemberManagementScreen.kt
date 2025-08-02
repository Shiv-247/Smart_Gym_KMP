package com.example.smartgymkmp.ui.membermanagement

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.smartgymkmp.model.MemberFilter
import com.example.smartgymkmp.ui.components.animations.AnimatedGridBackground
import com.example.smartgymkmp.ui.components.animations.AnimatedHeader
import com.example.smartgymkmp.ui.components.buttons.FloatingAddButton
import com.example.smartgymkmp.ui.components.filterchips.FuturisticFilterChips
import com.example.smartgymkmp.ui.components.membercard.AnimatedMemberCard
import com.example.smartgymkmp.ui.theme.CyberColors
import com.example.smartgymkmp.viewmodels.MemberViewModel
import kotlinx.datetime.Clock
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.TimeZone
import kotlinx.datetime.minus
import kotlinx.datetime.todayIn

@Composable
fun MemberManagementScreen(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    onCallMember: (String) -> Unit = {},
    onEditMember: (String) -> Unit = {},
) {
    val viewModel: MemberViewModel = viewModel()
    val members by viewModel.allMembers.collectAsState()

    var selectedFilter by remember { mutableStateOf(MemberFilter.ALL) }
    val listState = rememberLazyListState()
    val density = LocalDensity.current

    // Animated background
    val infiniteTransition = rememberInfiniteTransition(label = "background")
    val backgroundOffset by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(20000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "backgroundOffset"
    )

    // Scroll-based parallax effect
    val scrollOffset = remember { derivedStateOf { listState.firstVisibleItemScrollOffset } }

    val filteredMembers = members.filter {
        when (selectedFilter) {
            MemberFilter.TODAY -> it.startDate == Clock.System.todayIn(TimeZone.currentSystemDefault()).toString()
            MemberFilter.THIS_WEEK -> it.startDate >= Clock.System.todayIn(TimeZone.currentSystemDefault()).minus(7, DateTimeUnit.DAY).toString()
            MemberFilter.ALL -> true
        }
    }

    LaunchedEffect(Unit) {
        viewModel.loadMembers("gym_001") // For testing/demo only
    }



    Box(
        modifier = modifier
            .fillMaxSize()
            .background(
                brush = Brush.radialGradient(
                    colors = listOf(
                        CyberColors.DeepPurple.copy(alpha = 0.8f),
                        CyberColors.DarkBg,
                        Color.Black
                    ),
                    radius = 1000f + backgroundOffset * 2
                )
            )
    ) {
        // Animated grid pattern background
        AnimatedGridBackground(backgroundOffset)

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            // Animated header
            AnimatedHeader()

            Spacer(modifier = Modifier.height(24.dp))

            // Futuristic filter chips
            FuturisticFilterChips(
                selectedFilter = selectedFilter,
                onFilterSelected = { selectedFilter = it }
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Members list with animations

            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                itemsIndexed(filteredMembers) { index, member ->
                    AnimatedMemberCard(
                        member = member,
                        index = index,
                        parallaxOffset = scrollOffset.value,
                        onCall = { onCallMember(member.phoneNumber) },
                        onEdit = { onEditMember(member.id) },
                        navController = navController,
                        onClick = {
                            navController.navigate("memberProfile/${member.id}")
                        },
                        viewModel = viewModel
                    )
                }
            }

        }

        // Floating Action Button with pulsating glow
        FloatingAddButton(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp),
            scrollOffset = scrollOffset.value,
            viewModel = viewModel
        )
    }
}