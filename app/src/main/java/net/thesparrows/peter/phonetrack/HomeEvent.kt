package net.thesparrows.peter.phonetrack

import net.thesparrows.peter.phonetrack.ui.theme.Theme

sealed interface HomeEvent {
    data class DeleteTrackProfile(val trackProfile: TrackProfile): HomeEvent
    data class ToggleTrackProfile(val trackProfile: TrackProfile): HomeEvent
    object UpdateTrackProfile: HomeEvent
    object ToggleSettings: HomeEvent
    object ToggleThemeDropdown: HomeEvent
    data class SetTheme(val theme: Theme): HomeEvent
    data class EditProfile(val trackProfile: TrackProfile?): HomeEvent
    
}