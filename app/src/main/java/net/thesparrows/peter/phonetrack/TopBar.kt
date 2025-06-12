package net.thesparrows.peter.phonetrack

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(
    state: HomeState,
    onEvent: (HomeEvent) -> Unit,
) {

    TopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
        ),
        navigationIcon = {
            if (state.inSettings) {
                IconButton(
                    onClick = {
                        onEvent(HomeEvent.ToggleSettings)
                    },
                    colors = IconButtonDefaults.iconButtonColors(contentColor = MaterialTheme.colorScheme.onPrimaryContainer)
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Default.ArrowBack,
                        contentDescription = "Localized description"
                    )
                }
            } else if (state.currentTrackProfile != null) {
                IconButton(
                    onClick = {
                        onEvent(HomeEvent.EditProfile(null))
                    },
                    colors = IconButtonDefaults.iconButtonColors(contentColor = MaterialTheme.colorScheme.onPrimaryContainer)
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Default.ArrowBack,
                        contentDescription = "Localized description"
                    )
                }
            }
        },
        title = {
            if (state.inSettings) {
                Text(
                    text = "Settings",
                    style = MaterialTheme.typography.titleLarge
                )
            } else if (state.currentTrackProfile != null) {
                Text(
                    text = "Editing " + state.currentTrackProfile.displayName,
                    style = MaterialTheme.typography.titleLarge
                )
            } else {
                Text(
                    text = stringResource(R.string.title_text, stringResource(R.string.app_name)),
                    style = MaterialTheme.typography.titleLarge
                )
            }
        },
        actions = {
            IconButton(
                onClick = {
                    onEvent(HomeEvent.ToggleSettings)
                },
                colors = IconButtonDefaults.iconButtonColors(contentColor = MaterialTheme.colorScheme.onPrimaryContainer)
            ) {
                Icon(
                    imageVector = Icons.Filled.Menu,
                    contentDescription = "Localized description"
                )
            }
        }
    )
}