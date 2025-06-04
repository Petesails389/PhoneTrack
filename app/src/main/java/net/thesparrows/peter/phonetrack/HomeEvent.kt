package net.thesparrows.peter.phonetrack

sealed interface HomeEvent {
    object SaveTrackProfile: HomeEvent
    data class DeleteTrackProfile(val trackProfile: TrackProfile): HomeEvent
    data class ToggleTrackProfile(val trackProfile: TrackProfile): HomeEvent
    object UpdateTrackProfile: HomeEvent
    data class EditProfile(val trackProfile: TrackProfile?): HomeEvent
    data class SetDisplayName(val displayName: String): HomeEvent
    data class SetUserName(val userName: String): HomeEvent
    data class SetMapID(val mapID: Int): HomeEvent
    data class SetWebAddress(val webAddress: String): HomeEvent
    
}