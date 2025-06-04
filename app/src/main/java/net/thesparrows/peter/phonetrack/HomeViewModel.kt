package net.thesparrows.peter.phonetrack

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
            HomeEvent.SaveTrackProfile -> {
                if (_state.value.currentTrackProfile == null)
                viewModelScope.launch {
                    _state.value.currentTrackProfile?.let { dao.upsertProfile(it) }
                }
            }
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
                        dao.upsertProfile(updatedTrackProfile)
                    }.invokeOnCompletion {
                        _state.update {
                            it.copy(
                                currentTrackProfile = null
                            )
                        }
                    }
                } else {
                    _state.update {
                        it.copy(
                            currentTrackProfile = TrackProfile("New Profile", "UserName", 1, "Web Address")
                        )
                    }
                }
            }
            is HomeEvent.EditProfile -> {
                _state.update {
                    it.copy(
                        currentTrackProfile = event.trackProfile
                    )
                }
            }
            is HomeEvent.SetDisplayName -> {
                var currentTrackProfile = _state.value.currentTrackProfile
                _state.update {
                    it.copy(
                        currentTrackProfile = currentTrackProfile?.copy(displayName = event.displayName)
                    )
                }
            }
            is HomeEvent.SetMapID -> {
                var currentTrackProfile = _state.value.currentTrackProfile
                _state.update {
                    it.copy(
                        currentTrackProfile = currentTrackProfile?.copy(mapID = event.mapID)
                    )
                }
            }
            is HomeEvent.SetUserName -> {
                var currentTrackProfile = _state.value.currentTrackProfile
                _state.update {
                    it.copy(
                        currentTrackProfile = currentTrackProfile?.copy(userName = event.userName)
                    )
                }
            }
            is HomeEvent.SetWebAddress -> {
                var currentTrackProfile = _state.value.currentTrackProfile
                _state.update {
                    it.copy(
                        currentTrackProfile = currentTrackProfile?.copy(webAddress = event.webAddress)
                    )
                }
            }
        }
    }
}