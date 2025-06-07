package net.thesparrows.peter.phonetrack

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.InputTransformation
import androidx.compose.foundation.text.input.maxLength
import androidx.compose.foundation.text.input.then
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Link
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Tag
import androidx.compose.material.icons.filled.Title
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TextFieldLabelPosition
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import net.thesparrows.peter.phonetrack.ui.theme.textFieldColors

@Composable
fun TrackProfileSettings (
    state: HomeState,
    onEvent: (HomeEvent) -> Unit,
){

    Box(
        Modifier
            .padding(12.dp)
            .fillMaxSize()
            .verticalScroll(rememberScrollState()))
    {
        Column {
            TextField(
                state = state.displayName,
                labelPosition = TextFieldLabelPosition.Above(),
                label = {
                    Row (verticalAlignment = Alignment.CenterVertically){
                        Icon(Icons.Default.Title, contentDescription = "Title Icon")
                        Spacer(Modifier.width(4.dp))
                        Text(
                            text = "Display Name:",
                            style = MaterialTheme.typography.titleMedium
                        )
                    }
                },
                textStyle = MaterialTheme.typography.bodyLarge,
                contentPadding = TextFieldDefaults.contentPaddingWithoutLabel(top = 4.dp, bottom = 4.dp, start = 8.dp, end = 8.dp),
                colors = textFieldColors(),
                modifier = Modifier.fillMaxWidth()
                    .padding(8.dp)
            )
            TextField(
                state = state.webAddress,
                labelPosition = TextFieldLabelPosition.Above(),
                label = {
                    Row (verticalAlignment = Alignment.CenterVertically){
                        Icon(Icons.Filled.Link, contentDescription = "Title Icon")
                        Spacer(Modifier.width(4.dp))
                        Text(
                            text = "Web Address:",
                            style = MaterialTheme.typography.titleMedium
                        )
                    }
                },
                textStyle = MaterialTheme.typography.bodyLarge,
                contentPadding = TextFieldDefaults.contentPaddingWithoutLabel(top = 4.dp, bottom = 4.dp, start = 8.dp, end = 8.dp),
                colors = textFieldColors(),
                modifier = Modifier.fillMaxWidth()
                    .padding(8.dp)
            )
            TextField(
                state = state.username,
                labelPosition = TextFieldLabelPosition.Above(),
                label = {
                    Row (verticalAlignment = Alignment.CenterVertically){
                        Icon(Icons.Default.Person, contentDescription = "Title Icon")
                        Spacer(Modifier.width(4.dp))
                        Text(
                            text = "Username:",
                            style = MaterialTheme.typography.titleMedium
                        )
                    }
                },
                textStyle = MaterialTheme.typography.bodyLarge,
                contentPadding = TextFieldDefaults.contentPaddingWithoutLabel(top = 4.dp, bottom = 4.dp, start = 8.dp, end = 8.dp),
                colors = textFieldColors(),
                modifier = Modifier.fillMaxWidth()
                    .padding(8.dp)
            )
            TextField(
                state = state.mapID,
                labelPosition = TextFieldLabelPosition.Above(),
                label = {
                    Row (verticalAlignment = Alignment.CenterVertically){
                        Icon(Icons.Default.Tag, contentDescription = "Title Icon")
                        Spacer(Modifier.width(4.dp))
                        Text(
                            text = "Map ID:",
                            style = MaterialTheme.typography.titleMedium
                        )
                    }
                },
                inputTransformation = DigitOnlyInputTransformation().then(InputTransformation.maxLength(6)),
                textStyle = MaterialTheme.typography.bodyLarge,
                contentPadding = TextFieldDefaults.contentPaddingWithoutLabel(top = 4.dp, bottom = 4.dp, start = 8.dp, end = 8.dp),
                colors = textFieldColors(),
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                modifier = Modifier.padding(8.dp)
            )
        }
    }
}