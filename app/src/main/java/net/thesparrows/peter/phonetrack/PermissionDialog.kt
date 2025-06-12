package net.thesparrows.peter.phonetrack

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PermissionDialog(
    permissionTextProvider: PermissionTextProvider,
    isPermanentlyDeclined: Boolean,
    onDismiss: () -> Unit,
    onOkClick: () -> Unit,
    onGoToAppSettingsClick: () -> Unit,
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            Button(onClick =
                if(isPermanentlyDeclined){
                    onGoToAppSettingsClick
                } else {
                    onOkClick
                }
            ) {
                Text(
                    text = if(isPermanentlyDeclined) {
                        "Grant permission"
                    } else {
                        "Ok"
                    }
                )
            }},
        title = {
            Text("Permission Required")
        },
        text = {
            Text(
                text = permissionTextProvider.getDescription(isPermanentlyDeclined)
            )
        }
    )
}

interface PermissionTextProvider {
    fun getDescription(isPermanentlyDeclined: Boolean) : String
}

class LocationPermissionTextProvider: PermissionTextProvider {
    override fun getDescription(isPermanentlyDeclined: Boolean): String {
        return if( isPermanentlyDeclined ){
            "It seems you permanently declined location Permission. You can go to the app settings to grant it"
        } else {
            "This app needs access to your location to send that data to the tracking server."
        }
    }
}
class NotificationPermissionTextProvider: PermissionTextProvider {
    override fun getDescription(isPermanentlyDeclined: Boolean): String {
        return if( isPermanentlyDeclined ){
            "It seems you permanently declined notification access. You can go to the app settings to grant it"
        } else {
            "This app needs access to display a notification to run a the service even when the app is closed."
        }
    }
}