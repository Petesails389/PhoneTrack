package net.thesparrows.peter.phonetrack

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Update
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface TrackProfileDao {
    @Query("SELECT * FROM TrackProfile ORDER BY id ASC ")
    fun getAll(): Flow<List<TrackProfile>>

    @Query("SELECT * FROM TrackProfile WHERE id IN (:profileIds)")
    fun loadAllByIds(profileIds: IntArray): Flow<List<TrackProfile>>

    @Upsert
    suspend fun upsertProfile(vararg users: TrackProfile)

    @Delete
    suspend fun delete(profile: TrackProfile)
}