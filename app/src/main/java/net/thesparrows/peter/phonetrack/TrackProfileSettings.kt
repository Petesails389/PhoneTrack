package net.thesparrows.peter.phonetrack

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp

@Composable
fun TrackProfileSettings (
    state: HomeState,
    onEvent: (HomeEvent) -> Unit,
){
    val defaultTrackProfile = TrackProfile("New Profile", "UserName", 1, "Web Address")

    Box(
        Modifier
            .padding(12.dp)
            .fillMaxSize()
            .verticalScroll(rememberScrollState()))
    {
        Column {
            OutlinedTextField(
                value = state.currentTrackProfile?.displayName ?: defaultTrackProfile.displayName,
                onValueChange = { onEvent(HomeEvent.SetDisplayName(it)) },
                label = {
                    Text(
                        text = "Display Name:",
                        style = MaterialTheme.typography.titleSmall
                        )
                        },
                textStyle = MaterialTheme.typography.bodyLarge,
                maxLines = 1,
                modifier = Modifier.fillMaxWidth()
                    .padding(8.dp)
            )
            OutlinedTextField(
                value = state.currentTrackProfile?.webAddress ?: defaultTrackProfile.webAddress,
                onValueChange = { onEvent(HomeEvent.SetWebAddress(it)) },
                label = {
                    Text(
                        text = "Web Address:",
                        style = MaterialTheme.typography.titleSmall
                    )
                },
                textStyle = MaterialTheme.typography.bodyLarge,
                maxLines = 1,
                modifier = Modifier.fillMaxWidth()
                    .padding(8.dp)
            )
            OutlinedTextField(
                value = state.currentTrackProfile?.userName ?: defaultTrackProfile.userName,
                onValueChange = { onEvent(HomeEvent.SetUserName(it)) },
                label = {
                    Text(
                        text = "User Name:",
                        style = MaterialTheme.typography.titleSmall
                    )
                },
                textStyle = MaterialTheme.typography.bodyLarge,
                maxLines = 1,
                modifier = Modifier.fillMaxWidth()
                    .padding(8.dp)
            )
            var mapID by remember { mutableStateOf<Int?>(1) }
            OutlinedTextField(
                value = (mapID ?: "").toString(),
                onValueChange = {
                    mapID = it.toIntOrNull()
                    onEvent(HomeEvent.SetMapID(mapID ?: state.currentTrackProfile?.mapID ?: 0))
                },
                label = {
                    Text(
                        text = "User Name:",
                        style = MaterialTheme.typography.titleSmall
                    )
                },
                textStyle = MaterialTheme.typography.bodyLarge,
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                modifier = Modifier.padding(8.dp)
            )
        }
    }
}