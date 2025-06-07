package net.thesparrows.peter.phonetrack

import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.stringPreferencesKey

data class SettingsKeys(
    val theme: Preferences.Key<String> = stringPreferencesKey("theme"),
)