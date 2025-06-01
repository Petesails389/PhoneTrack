package net.thesparrows.peter.phonetrack

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class TrackProfile(
    @ColumnInfo(name = "display_name") val displayName: String,
    @ColumnInfo(name = "user_name") val userName: String,
    @ColumnInfo(name = "map_id") val mapID: Int,
    @ColumnInfo(name = "web_address") val webAddress: String,
    @ColumnInfo(name = "location_duration") val locationDuration: Int = 0,
    @ColumnInfo(name = "upload_duration") val uploadDuration: Int = 300,
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0
)