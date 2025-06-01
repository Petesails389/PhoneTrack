package net.thesparrows.peter.phonetrack

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
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
                viewModelScope.launch {
                    dao.upsertProfile(TrackProfile("Test Profile", "Test User", 1))
                }
            }
            is HomeEvent.setDisplayName -> TODO()
            is HomeEvent.setMapID -> TODO()
            is HomeEvent.setUserName -> TODO()
        }
    }
}