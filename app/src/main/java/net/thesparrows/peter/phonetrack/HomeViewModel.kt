package net.thesparrows.peter.phonetrack

import androidx.compose.foundation.text.input.TextFieldState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeViewModel(
    private val dao : TrackProfileDao
) : ViewModel() {

    private val _trackProfiles = dao.getAll()
    private val _state = MutableStateFlow(HomeState())
    val state = combine(_state, _trackProfiles) {state, trackProfiles ->
        state.copy(
            profileList = trackProfiles
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), HomeState())

    fun onEvent(event: HomeEvent) {
        when(event) {
            is HomeEvent.DeleteTrackProfile -> {
                viewModelScope.launch {
                    dao.delete(event.trackProfile)
                }
            }
            is HomeEvent.ToggleTrackProfile -> {
                viewModelScope.launch {
                    var updatedTrackProfile: TrackProfile = event.trackProfile.copy(running = !event.trackProfile.running)
                    dao.upsertProfile(updatedTrackProfile)
                }
            }
            is HomeEvent.UpdateTrackProfile -> {
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
                    onEvent(HomeEvent.EditProfile(TrackProfile("New Profile", "Username", 1, "Web.Address")))
                }
            }
            is HomeEvent.EditProfile -> {
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
        }
    }
}