package net.thesparrows.peter.phonetrack

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import net.thesparrows.peter.phonetrack.ui.theme.Theme

@Composable
fun MainSettings(
    state: HomeState,
    onEvent: (HomeEvent) -> Unit
) {
    Box(
        Modifier.padding(12.dp)
            .fillMaxSize()
            .verticalScroll(rememberScrollState()))
    {
        Column {
            Text(
                text = "Theme:",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(8.dp),
            )
            Surface (
                shadowElevation = 3.dp,
                shape = MaterialTheme.shapes.small
            ){
                Row (
                    modifier = Modifier
                        .clickable { onEvent(HomeEvent.ToggleThemeDropdown) }
                        .background(MaterialTheme.colorScheme.surfaceContainer)
                ) {
                    Text(
                        text = when (state.theme) {
                            Theme.DEFAULT -> "Select Theme"
                            Theme.DARK_THEME -> "Dark Theme"
                            Theme.LIGHT_THEME -> "Light Theme (Default)"
                            Theme.FOLLOW_DEVICE_THEME -> "Follow Device Theme"
                            Theme.DYNAMIC_LIGHT_THEME -> "Light Theme (Dynamic Colours)"
                            Theme.DYNAMIC_DARK_THEME -> "Dark Theme (Dynamic Colours)"
                            Theme.DYNAMIC_THEME -> "Follow Device Theme (Dynamic Colours)"
                        },
                        color = MaterialTheme.colorScheme.onSurface,
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.padding(8.dp),
                    )
                    Icon(
                        Icons.Default.ArrowDropDown,
                        contentDescription = "Theme Dropdown",
                        modifier = Modifier.padding(top = 8.dp),
                    )
                }
            }
            Spacer(Modifier.height(8.dp))
            DropdownMenu(
                expanded = state.themeDropDown,
                onDismissRequest = { onEvent(HomeEvent.ToggleThemeDropdown) },
            ) {
                DropdownMenuItem(
                    text = { Text("Light Theme (Default)") },
                    onClick = { onEvent(HomeEvent.SetTheme(Theme.LIGHT_THEME)) }
                )
                DropdownMenuItem(
                    text = { Text("Dark Theme") },
                    onClick = { onEvent(HomeEvent.SetTheme(Theme.DARK_THEME)) }
                )

                DropdownMenuItem(
                    text = { Text("Follow Device Theme") },
                    onClick = { onEvent(HomeEvent.SetTheme(Theme.FOLLOW_DEVICE_THEME)) }
                )
                DropdownMenuItem(
                    text = { Text("Light Theme (Dynamic Colours)") },
                    onClick = { onEvent(HomeEvent.SetTheme(Theme.DYNAMIC_LIGHT_THEME)) }
                )
                DropdownMenuItem(
                    text = { Text("Dark Theme (Dynamic Colours)") },
                    onClick = { onEvent(HomeEvent.SetTheme(Theme.DYNAMIC_DARK_THEME)) }
                )
                DropdownMenuItem(
                    text = { Text("Follow Device Theme (Dynamic Colours)") },
                    onClick = { onEvent(HomeEvent.SetTheme(Theme.DYNAMIC_THEME)) }
                )
            }
        }
    }
}

