package net.thesparrows.peter.phonetrack

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class TrackProfile(
    @ColumnInfo(name = "display_name") var displayName: String,
    @ColumnInfo(name = "user_name") var username: String,
    @ColumnInfo(name = "map_id") var mapID: Int,
    @ColumnInfo(name = "web_address") var webAddress: String,
    @ColumnInfo(name = "location_duration") var locationDuration: Int = 0,
    @ColumnInfo(name = "upload_duration") var uploadDuration: Int = 300,
    @ColumnInfo(name = "running") var running: Boolean = true,
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0
)