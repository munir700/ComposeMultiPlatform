package app.sunreef.yachts.mobile.presentation.dashboard.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import app.sunreef.yachts.mobile.presentation.dashboard.DashboardContract

@Composable
fun DashboardContent(
    state: DashboardContract.State,
    onEvent: (DashboardContract.Event) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        // Header
        DashboardHeader(
            systemStatus = state.systemStatus,
            onSettingsClick = { onEvent(DashboardContract.Event.SettingsClicked) }
        )

        // Loading State
        if (state.isLoading) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.background),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
            return@Column
        }

        // Error State
        state.error?.let { error ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.background),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(
                        text = "Error",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.error
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = error,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(onClick = { onEvent(DashboardContract.Event.RefreshSystems) }) {
                        Text("Retry")
                    }
                }
            }
            return@Column
        }

        // Systems List
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
        ) {
            state.systems.forEach { system ->
                SystemCard(
                    system = system,
                    onClick = { onEvent(DashboardContract.Event.SystemClicked(system.id)) }
                )
                Spacer(modifier = Modifier.height(12.dp))
            }
        }

        // Refresh Button
        Button(
            onClick = { onEvent(DashboardContract.Event.RefreshSystems) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .height(48.dp)
        ) {
            Text("Refresh Systems")
        }
    }
}