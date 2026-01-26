package com.kodex.sunny.custom.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.kodex.sunny.main_screen.tracker.data.TrackData
import kotlinx.coroutines.flow.Flow

@Dao
interface TrackDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTrack(trackData: TrackData)

    @Delete
    suspend fun deleteTrack(trackData: TrackData)

    @Query("SELECT * FROM tracks")
    fun getAllTracks(): Flow<List<TrackData>>


}