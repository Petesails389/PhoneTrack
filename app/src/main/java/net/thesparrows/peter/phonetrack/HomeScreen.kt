package net.thesparrows.peter.phonetrack

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.LinkAnnotation
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextLinkStyles
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withLink
import androidx.compose.ui.unit.dp
import net.thesparrows.peter.phonetrack.ui.theme.PhoneTrackTheme

@Composable
fun HomeScreen(
    state: HomeState,
    onEvent: (HomeEvent) -> Unit
) {
    PhoneTrackTheme {
        Scaffold(
            topBar = {
                TopBar()
            },
            floatingActionButton = {
                FloatingActionButton(onClick = {
                    onEvent(HomeEvent.SaveTrackProfile)
                }) {
                    Icon(imageVector = Icons.Default.Add, contentDescription = "Add Track Profile")
                }
            }
        ) {
            Box (Modifier.padding(it)) {
                Box(Modifier.padding(12.dp)) {
                    Column (verticalArrangement = Arrangement.spacedBy(12.dp)){
                        Modifier.fillMaxSize()
                            .background(color = MaterialTheme.colorScheme.primary)
                            .verticalScroll(rememberScrollState())
                        Text(
                            text = buildAnnotatedString {
                                append(stringResource(R.string.welcome_text_p1))
                                withLink(
                                    LinkAnnotation.Url(
                                        "https://peter.thesparrows.net/maps/",
                                        TextLinkStyles(
                                            style = SpanStyle(
                                                color = MaterialTheme.colorScheme.tertiary,
                                                textDecoration = TextDecoration.Underline
                                            )
                                        )
                                    )
                                ) {
                                    append(stringResource(R.string.welcome_text_link))
                                }
                            },
                            color = MaterialTheme.colorScheme.primary
                        )
                        state.profileList.forEach { trackProfile ->
                            TrackProfile(trackProfile)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun TrackProfile(trackProfile: TrackProfile) {
    Card(
        shape = MaterialTheme.shapes.large,
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = trackProfile.displayName,
                color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.titleMedium

            )
            Text(
                text = trackProfile.userName,
                color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.bodyMedium

            )
        }
    }
}