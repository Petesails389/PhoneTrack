package net.thesparrows.peter.phonetrack

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [TrackProfile::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract val dao : TrackProfileDao
}