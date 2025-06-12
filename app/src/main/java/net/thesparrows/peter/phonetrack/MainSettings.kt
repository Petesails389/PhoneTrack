package net.thesparrows.peter.phonetrack

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.provider.Settings
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat.shouldShowRequestPermissionRationale
import net.thesparrows.peter.phonetrack.ui.theme.Theme

@Composable
fun MainSettings(
    state: HomeState,
    onEvent: (HomeEvent) -> Unit,
    activity: Activity,
) {
    val multiplePermissionsResultLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions(),
        onResult = {permissions ->
            permissions.keys.forEach { permission ->
                onEvent(HomeEvent.PermissionResult(
                    permission = permission,
                    isGranted = permissions[permission] == true
                ))
            }
        }
    )

    Box(
        Modifier
            .padding(12.dp)
            .fillMaxSize()
            .verticalScroll(rememberScrollState()))
    {
        Column {
            Text(
                text = "Theme:",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(8.dp),
            )
            Column {
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
            Text(
                text = "Permissions:",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(8.dp),
            )
            PermissionSetting(
                Manifest.permission.ACCESS_FINE_LOCATION,
                state.locationPermission,
                multiplePermissionsResultLauncher
            )
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                PermissionSetting(
                    Manifest.permission.POST_NOTIFICATIONS,
                    state.notificationPermission,
                    multiplePermissionsResultLauncher
                )
            }
            state.permissionDialogQueue.reversed().forEach { permission ->
                PermissionDialog(
                    permissionTextProvider = when(permission) {
                        Manifest.permission.ACCESS_FINE_LOCATION -> LocationPermissionTextProvider()
                        Manifest.permission.POST_NOTIFICATIONS -> NotificationPermissionTextProvider()
                        else -> return@forEach
                    },
                    isPermanentlyDeclined = !shouldShowRequestPermissionRationale(
                        activity,
                        permission
                    ),
                    onDismiss = {
                        onEvent(HomeEvent.DismissDialog)
                    },
                    onOkClick = {
                        onEvent(HomeEvent.DismissDialog)
                        multiplePermissionsResultLauncher.launch(
                            arrayOf(permission)
                        )
                    },
                    onGoToAppSettingsClick = activity::openAppSettings
                )
            }
        }
    }
}

@Composable
fun PermissionSetting(
    permission: String,
    granted: Boolean,
    multiplePermissionsResultLauncher: ManagedActivityResultLauncher<Array<String>, Map<String, @JvmSuppressWildcards Boolean>>
) {
    if (granted) {
        Text(
            when (permission) {
                Manifest.permission.ACCESS_FINE_LOCATION -> "Location permission granted."
                Manifest.permission.POST_NOTIFICATIONS -> "Notification permission granted."
                else -> return
            }
        )
    } else {
        Button(onClick = {
            multiplePermissionsResultLauncher.launch(
                arrayOf(permission)
            )
        } ) {
            Text(when (permission) {
                Manifest.permission.ACCESS_FINE_LOCATION -> "Grant location permission"
                Manifest.permission.POST_NOTIFICATIONS -> "Grant notification permission"
                else -> return@Button
            })
        }
    }
}

fun Activity.openAppSettings() {
    Intent(
        Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
        Uri.fromParts("package", packageName, null)
    ).also(::startActivity)
}

