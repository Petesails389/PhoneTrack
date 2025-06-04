package net.thesparrows.peter.phonetrack

data class HomeState(
    val profileList: List<TrackProfile> = emptyList(),
    val currentTrackProfile: TrackProfile? = null
)
