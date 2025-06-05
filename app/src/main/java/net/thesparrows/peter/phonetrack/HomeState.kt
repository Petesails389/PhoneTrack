package net.thesparrows.peter.phonetrack

import androidx.compose.foundation.text.input.TextFieldState

data class HomeState(
    val profileList: List<TrackProfile> = emptyList(),
    val currentTrackProfile: TrackProfile? = null,
    val displayName: TextFieldState = TextFieldState(),
    val webAddress: TextFieldState = TextFieldState(),
    val username: TextFieldState = TextFieldState(),
    val mapID: TextFieldState = TextFieldState(),
)
