package net.thesparrows.peter.phonetrack

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.LinkAnnotation
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextLinkStyles
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withLink
import androidx.compose.ui.unit.dp

@Composable
fun TrackProfileList(
    state: HomeState,
    onEvent: (HomeEvent) -> Unit
) {
    Box(
    Modifier.padding(12.dp)
    .fillMaxSize()
    .verticalScroll(rememberScrollState()))
    {
        Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
            Text(
                text = buildAnnotatedString {
                    append(stringResource(R.string.welcome_text_p1))
                    withLink(
                        LinkAnnotation.Url(
                            "https://peter.thesparrows.net/maps/",
                            TextLinkStyles(
                                style = SpanStyle(
                                    color = MaterialTheme.colorScheme.primary,
                                    textDecoration = TextDecoration.Underline
                                )
                            )
                        )
                    ) {
                        append(stringResource(R.string.welcome_text_link))
                    }
                },
                modifier = Modifier.padding(8.dp)
            )
            state.profileList.forEach { trackProfile ->
                TrackProfile(trackProfile, onEvent)
            }
            Spacer(Modifier.height(64.dp))
        }
    }
}

@Composable
fun TrackProfile(trackProfile: TrackProfile, onEvent: (HomeEvent) -> Unit) {
    Card(
        shape = MaterialTheme.shapes.large,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant,
            contentColor = MaterialTheme.colorScheme.onSurfaceVariant
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row (modifier = Modifier.padding(16.dp)
            .height(intrinsicSize = IntrinsicSize.Max)) {
            Column (modifier = Modifier.weight(2f)) {
                Text(
                    text = trackProfile.displayName + ":",
                    style = MaterialTheme.typography.titleMedium
                )
                Row{
                    Column {
                        Text(
                            text = "Username:  ",
                            style = MaterialTheme.typography.bodyMedium
                        )
                        Text(
                            text = "Address:  ",
                            style = MaterialTheme.typography.bodyMedium
                        )
                        Text(
                            text = "Map ID:  ",
                            style = MaterialTheme.typography.bodyMedium
                        )
                        Text(
                            text = "Duration:  ",
                            style = MaterialTheme.typography.titleSmall
                        )
                        Text(
                            text = "Location:  ",
                            style = MaterialTheme.typography.bodyMedium
                        )
                        Text(
                            text = "Upload:  ",
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                    Column{
                        Text(
                            text = trackProfile.username,
                            style = MaterialTheme.typography.bodyMedium,
                            overflow = TextOverflow.Ellipsis,
                            maxLines = 1,
                        )
                        Text(
                            text = trackProfile.webAddress,
                            style = MaterialTheme.typography.bodyMedium,
                            overflow = TextOverflow.Ellipsis,
                            maxLines = 1,
                        )
                        Text(
                            text = trackProfile.mapID.toString(),
                            style = MaterialTheme.typography.bodyMedium,
                            overflow = TextOverflow.Ellipsis,
                            maxLines = 1,
                        )
                        Spacer (modifier = Modifier.weight(1f))
                        Text(
                            text = if (trackProfile.locationDuration == 0) "Constant Tracking" else trackProfile.locationDuration.toString() + "s",
                            style = MaterialTheme.typography.bodyMedium,
                            overflow = TextOverflow.Ellipsis,
                            maxLines = 1,
                        )
                        Text(
                            text = if (trackProfile.uploadDuration == 0) "Live Upload" else trackProfile.uploadDuration.toString() + "s",
                            style = MaterialTheme.typography.bodyMedium,
                            overflow = TextOverflow.Ellipsis,
                            maxLines = 1,
                        )
                    }
                }
            }
            Column(
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                Switch(
                    checked = trackProfile.running,
                    colors = switchColors(),
                    onCheckedChange = {
                        onEvent(HomeEvent.ToggleTrackProfile(trackProfile))
                    },
                    modifier = Modifier.padding(0.dp),
                )
                FilledIconButton(
                    onClick = {
                        onEvent(HomeEvent.EditProfile(trackProfile))
                    },
                    colors = IconButtonColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        contentColor = MaterialTheme.colorScheme.onPrimary,
                        disabledContainerColor = MaterialTheme.colorScheme.primary,
                        disabledContentColor = MaterialTheme.colorScheme.onPrimary
                    ),
                ) {
                    Icon(
                        imageVector = Icons.Default.Settings,
                        contentDescription = "Edit Track Profile",
                    )
                }
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

@Composable
private fun switchColors() = SwitchDefaults.colors(
    checkedThumbColor = MaterialTheme.colorScheme.primary,
    checkedTrackColor = MaterialTheme.colorScheme.surfaceVariant,
    checkedBorderColor = MaterialTheme.colorScheme.primary,
    checkedIconColor = MaterialTheme.colorScheme.surfaceVariant,
    uncheckedThumbColor = MaterialTheme.colorScheme.outline,
    uncheckedTrackColor = MaterialTheme.colorScheme.surfaceVariant,
    uncheckedBorderColor = MaterialTheme.colorScheme.outline,
    uncheckedIconColor = MaterialTheme.colorScheme.surfaceVariant
)