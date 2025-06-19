package net.thesparrows.peter.phonetrack

import android.Manifest
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.compose.foundation.text.input.TextFieldState
import androidx.core.content.ContextCompat.checkSelfPermission
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import net.thesparrows.peter.phonetrack.HomeEvent.*
import net.thesparrows.peter.phonetrack.ui.theme.Theme

class HomeViewModel(
    private val dao : TrackProfileDao,
    private val dataStore: DataStore<Preferences>,
    private val applicationContext: Context,
) : ViewModel() {

    //settings
    private val _theme: Flow<String> = dataStore.data.map { preferences ->
        preferences[SettingsKeys().theme] ?: "DEFAULT"
    }

    private val _trackProfiles = dao.getAll()
    private val _state = MutableStateFlow(HomeState(
        locationPermission = checkSelfPermission(applicationContext,Manifest.permission.ACCESS_FINE_LOCATION) == 0,
        notificationPermission = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU){
            checkSelfPermission(applicationContext,Manifest.permission.POST_NOTIFICATIONS) == 0
        } else {
            true
        }
    ))
    val state = combine(_state, _trackProfiles, _theme) {state, trackProfiles, theme ->
        state.copy(
            profileList = trackProfiles,
            theme = Theme.valueOf(theme)
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), HomeState())

    fun onEvent(event: HomeEvent) {
        when(event) {

            is DeleteTrackProfile -> {
                viewModelScope.launch {
                    dao.delete(event.trackProfile)
                }
            }

            is ToggleTrackProfile -> {
                viewModelScope.launch {
                    var updatedTrackProfile: TrackProfile = event.trackProfile.copy(running = !event.trackProfile.running)
                    dao.upsertProfile(updatedTrackProfile)

                    val serviceIntent: Intent = Intent(applicationContext, TrackService::class.java).apply {
                        action = if (updatedTrackProfile.running) {
                            TrackService.Actions.START.toString()
                        } else {
                            TrackService.Actions.STOP.toString()
                        }
                    }
                    applicationContext.startService(serviceIntent)
                }
            }

            is UpdateTrackProfile -> {
                var currentTrackProfile = _state.value.currentTrackProfile
                if (currentTrackProfile != null){
                    viewModelScope.launch {
                        var updatedTrackProfile: TrackProfile = currentTrackProfile.copy()
                        updatedTrackProfile.displayName = state.value.displayName.text.toString()
                        updatedTrackProfile.webAddress = state.value.webAddress.text.toString()
                        updatedTrackProfile.username = state.value.username.text.toString()
                        updatedTrackProfile.mapID = state.value.mapID.text.toString().toIntOrNull()?: 0
                        dao.upsertProfile(updatedTrackProfile)
                    }.invokeOnCompletion {
                        _state.update {
                            it.copy(
                                currentTrackProfile = null,
                                displayName = TextFieldState(initialText = ""),
                                webAddress = TextFieldState(initialText = ""),
                                username = TextFieldState(initialText = ""),
                                mapID = TextFieldState(initialText = ""),
                            )
                        }
                    }
                } else {
                    onEvent(EditProfile(TrackProfile("New Profile", "Username", 1, "Web.Address")))
                }
            }

            is EditProfile -> {
                _state.update {
                    it.copy(
                        currentTrackProfile = event.trackProfile,
                        displayName = TextFieldState(initialText = event.trackProfile?.displayName ?: ""),
                        webAddress = TextFieldState(initialText = event.trackProfile?.webAddress ?: ""),
                        username = TextFieldState(initialText = event.trackProfile?.username ?: ""),
                        mapID = TextFieldState(initialText = (event.trackProfile?.mapID ?: "").toString()),
                    )
                }
            }

            ToggleSettings -> {
                _state.update {
                    it.copy(
                        inSettings = !_state.value.inSettings
                    )
                }
            }

            ToggleThemeDropdown -> {
                _state.update {
                    it.copy(
                        themeDropDown = !_state.value.themeDropDown
                    )
                }
            }

            is SetTheme -> {
                viewModelScope.launch {
                    dataStore.edit { settings ->
                        settings[SettingsKeys().theme] = event.theme.toString()
                    }
                }.invokeOnCompletion {
                    _state.update {
                        it.copy(
                            themeDropDown = false
                        )
                    }
                }
            }

            DismissDialog -> {
                _state.update {
                    it.copy(
                        permissionDialogQueue = _state.value.permissionDialogQueue.take(_state.value.permissionDialogQueue.lastIndex)
                    )
                }
            }

            is PermissionResult -> {
                if (!event.isGranted) {
                    _state.update {
                        it.copy(
                            permissionDialogQueue = listOf(event.permission) + _state.value.permissionDialogQueue,
                        )
                    }
                }
                when (event.permission) {
                    Manifest.permission.ACCESS_FINE_LOCATION -> {
                        _state.update {
                            it.copy(
                                locationPermission = event.isGranted
                            )
                        }
                    }
                    Manifest.permission.POST_NOTIFICATIONS -> {
                        _state.update {
                            it.copy(
                                notificationPermission = event.isGranted
                            )
                        }
                    }
                }
            }
        }
    }
}