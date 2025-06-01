package net.thesparrows.peter.phonetrack

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.LinkAnnotation
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextLinkStyles
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
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
                            TrackProfile(trackProfile, onEvent)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun TrackProfile(trackProfile: TrackProfile, onEvent: (HomeEvent) -> Unit) {
    Card(
        shape = MaterialTheme.shapes.large,
        modifier = Modifier.fillMaxWidth()
    ) {
        Row (modifier = Modifier.padding(16.dp)
            .height(intrinsicSize = IntrinsicSize.Max)) {
            Column (modifier = Modifier.weight(2f)) {
                Text(
                    text = trackProfile.displayName + ":",
                    color = MaterialTheme.colorScheme.primary,
                    style = MaterialTheme.typography.titleMedium
                )
                Row{
                    Column {
                        Text(
                            text = "Username:  ",
                            color = MaterialTheme.colorScheme.primary,
                            style = MaterialTheme.typography.bodyMedium
                        )
                        Text(
                            text = "Address:  ",
                            color = MaterialTheme.colorScheme.primary,
                            style = MaterialTheme.typography.bodyMedium
                        )
                        Text(
                            text = "Map ID:  ",
                            color = MaterialTheme.colorScheme.primary,
                            style = MaterialTheme.typography.bodyMedium
                        )
                        Text(
                            text = "Duration:  ",
                            color = MaterialTheme.colorScheme.primary,
                            style = MaterialTheme.typography.titleSmall
                        )
                        Text(
                            text = "Location:  ",
                            color = MaterialTheme.colorScheme.primary,
                            style = MaterialTheme.typography.bodyMedium
                        )
                        Text(
                            text = "Upload:  ",
                            color = MaterialTheme.colorScheme.primary,
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                    Column{
                        Text(
                            text = trackProfile.userName,
                            color = MaterialTheme.colorScheme.primary,
                            style = MaterialTheme.typography.bodyMedium,
                            overflow = TextOverflow.Ellipsis,
                            maxLines = 1,
                        )
                        Text(
                            text = trackProfile.webAddress,
                            color = MaterialTheme.colorScheme.primary,
                            style = MaterialTheme.typography.bodyMedium,
                            overflow = TextOverflow.Ellipsis,
                            maxLines = 1,
                        )
                        Text(
                            text = trackProfile.mapID.toString(),
                            color = MaterialTheme.colorScheme.primary,
                            style = MaterialTheme.typography.bodyMedium,
                            overflow = TextOverflow.Ellipsis,
                            maxLines = 1,
                        )
                        Spacer (modifier = Modifier.weight(1f))
                        Text(
                            text = if (trackProfile.locationDuration == 0) "Constant Tracking" else trackProfile.locationDuration.toString() + "s",
                            color = MaterialTheme.colorScheme.primary,
                            style = MaterialTheme.typography.bodyMedium,
                            overflow = TextOverflow.Ellipsis,
                            maxLines = 1,
                        )
                        Text(
                            text = if (trackProfile.uploadDuration == 0) "Live Upload" else trackProfile.uploadDuration.toString() + "s",
                            color = MaterialTheme.colorScheme.primary,
                            style = MaterialTheme.typography.bodyMedium,
                            overflow = TextOverflow.Ellipsis,
                            maxLines = 1,
                        )
                    }
                }
            }
            Column {
                FilledIconButton(
                    onClick = {
                        //onEvent(HomeEvent.DeleteTrackProfile(trackProfile))
                    },
                    colors = IconButtonColors(
                        containerColor = MaterialTheme.colorScheme.secondary,
                        contentColor = MaterialTheme.colorScheme.onSecondary,
                        disabledContainerColor = MaterialTheme.colorScheme.secondary,
                        disabledContentColor = MaterialTheme.colorScheme.onSecondary
                    ),
                    modifier = Modifier.padding(0.dp),
                ) {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = "Edit Track Profile",
                    )
                }
                Spacer(modifier = Modifier.weight(0.1f))
                FilledIconButton(
                    onClick = {
                        onEvent(HomeEvent.DeleteTrackProfile(trackProfile))
                    },
                    colors = IconButtonColors(
                        containerColor = MaterialTheme.colorScheme.tertiary,
                        contentColor = MaterialTheme.colorScheme.onTertiary,
                        disabledContainerColor = MaterialTheme.colorScheme.tertiary,
                        disabledContentColor = MaterialTheme.colorScheme.onTertiary
                    ),
                    modifier = Modifier.padding(0.dp),
                ) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Delete Track Profile",
                    )
                }
            }
        }
    }
}