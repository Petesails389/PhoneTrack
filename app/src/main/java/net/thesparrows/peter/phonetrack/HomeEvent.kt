package net.thesparrows.peter.phonetrack

sealed interface HomeEvent {
    data class DeleteTrackProfile(val trackProfile: TrackProfile): HomeEvent
    data class ToggleTrackProfile(val trackProfile: TrackProfile): HomeEvent
    object UpdateTrackProfile: HomeEvent
    data class EditProfile(val trackProfile: TrackProfile?): HomeEvent
    
}