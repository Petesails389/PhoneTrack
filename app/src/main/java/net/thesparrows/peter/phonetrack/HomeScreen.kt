package net.thesparrows.peter.phonetrack

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import net.thesparrows.peter.phonetrack.ui.theme.PhoneTrackTheme

@Composable
fun HomeScreen(
    state: HomeState,
    onEvent: (HomeEvent) -> Unit
) {
    PhoneTrackTheme {
        Scaffold(
            topBar = {
                TopBar(state, onEvent)
            },
            floatingActionButton = {
                FloatingActionButton(onClick = {
                    onEvent(HomeEvent.UpdateTrackProfile)
                }) {
                    if (state.currentTrackProfile != null) {
                        Icon(imageVector = Icons.Default.Done, contentDescription = "Save Track Profile")
                    } else {
                        Icon(imageVector = Icons.Default.Add, contentDescription = "Add Track Profile")
                    }
                }
            },
            contentColor = MaterialTheme.colorScheme.onBackground,
            containerColor = MaterialTheme.colorScheme.background,
        ) {
            Box (Modifier.padding(it)) {
                if(state.currentTrackProfile != null) {
                    TrackProfileSettings(state, onEvent)
                } else {
                    TrackProfileList(state, onEvent)
                }
            }
        }
    }
}