package net.thesparrows.peter.phonetrack

import androidx.compose.foundation.text.input.TextFieldState
import net.thesparrows.peter.phonetrack.ui.theme.Theme

data class HomeState(
    //HomeScreen
    val profileList: List<TrackProfile> = emptyList(),

    //TrackProfileSettings
    val currentTrackProfile: TrackProfile? = null,
    val displayName: TextFieldState = TextFieldState(),
    val webAddress: TextFieldState = TextFieldState(),
    val username: TextFieldState = TextFieldState(),
    val mapID: TextFieldState = TextFieldState(),

    //MainSettings
    val inSettings: Boolean = false,
    val themeDropDown: Boolean = false,
    val theme: Theme = Theme.DEFAULT,
)
