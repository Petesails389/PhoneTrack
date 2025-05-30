package net.thesparrows.peter.phonetrack

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.absoluteOffset
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.LinkAnnotation
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextLinkStyles
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withLink
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import net.thesparrows.peter.phonetrack.ui.theme.PhoneTrackTheme

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PhoneTrackTheme {
                Scaffold(
                    topBar = {
                        TopBar()
                    },
                ) {
                    Column(Modifier.padding(it)) {
                        ScrollContent()
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar () {

    TopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            titleContentColor = MaterialTheme.colorScheme.primary,
        ),
        title = {
            Text(
                text = stringResource(R.string.title_text, stringResource(R.string.app_name)),
                color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.titleLarge
            )
        },
        actions = {
            IconButton(onClick = { /* do something */ }) {
                Icon(
                    imageVector = Icons.Filled.Menu,
                    contentDescription = "Localized description"
                )
            }
        }
    )
}


@Composable
fun ScrollContent() {
    Column {
        Modifier.fillMaxHeight()
            .background(color = MaterialTheme.colorScheme.primary)
            .verticalScroll(rememberScrollState())
        Text(
            text = buildAnnotatedString {
                append(stringResource(R.string.welcome_text_p1))
                withLink(
                    LinkAnnotation.Url(
                        "https://peter.thesparrows.net/maps/",
                        TextLinkStyles(style = SpanStyle(
                            color = MaterialTheme.colorScheme.tertiary,
                            textDecoration = TextDecoration.Underline))
                    )
                ) {
                    append(stringResource(R.string.welcome_text_link))
                }
                append(stringResource(R.string.welcome_text_p2))
            },
            Modifier.padding(8.dp),
            color = MaterialTheme.colorScheme.primary
        )
        TrackProfile ("Track Profile 1")
    }
}

@Composable
fun TrackProfile(heading: String) {
    Card (Modifier.padding(8.dp)) {
        Card(
            shape = MaterialTheme.shapes.medium,
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = heading,
                    color = MaterialTheme.colorScheme.primary,
                    style = MaterialTheme.typography.titleMedium

                )
                Text(
                    text = "Some information",
                    color = MaterialTheme.colorScheme.primary,
                    style = MaterialTheme.typography.bodyMedium

                )
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun Preview() {
    PhoneTrackTheme {
        Scaffold(
            topBar = {
                TopBar()
            },
        ) {
            Column(Modifier.padding(it)) {
                ScrollContent()
            }
        }
    }
}