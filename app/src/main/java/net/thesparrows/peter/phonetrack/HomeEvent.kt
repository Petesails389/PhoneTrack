package net.thesparrows.peter.phonetrack

sealed interface HomeEvent {
    object SaveTrackProfile: HomeEvent
    data class DeleteTrackProfile(val trackProfile: TrackProfile): HomeEvent
    data class setDisplayName(val displayName: String): HomeEvent
    data class setUserName(val userName: String): HomeEvent
    data class setMapID(val mapID: Int): HomeEvent
    
}