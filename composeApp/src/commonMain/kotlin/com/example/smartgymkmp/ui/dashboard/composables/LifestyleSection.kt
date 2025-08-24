package com.example.smartgymkmp.ui.dashboard.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bedtime
import androidx.compose.material.icons.filled.Monitor
import androidx.compose.material.icons.filled.Restaurant
import androidx.compose.material.icons.filled.WaterDrop
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun LifestyleSection() {
    Column {
        Text(
            "Lifestyle & Metrics",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            MetricCard(
                modifier = Modifier.weight(1f),
                title = "Body Stats",
                value = "78.5 kg",
                icon = Icons.Default.Monitor,
                trend = "+0.5 kg this week"
            )
            MetricCard(
                modifier = Modifier.weight(1f),
                title = "Nutrition",
                value = "1850 kcal",
                icon = Icons.Default.Restaurant,
                trend = "75% of daily goal"
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            MetricCard(
                modifier = Modifier.weight(1f),
                title = "Hydration",
                value = "1.5L",
                icon = Icons.Default.WaterDrop,
                trend = "4 more cups needed"
            )
            MetricCard(
                modifier = Modifier.weight(1f),
                title = "Sleep",
                value = "6h 45m",
                icon = Icons.Default.Bedtime,
                trend = "83% sleep quality"
            )
        }
    }
}