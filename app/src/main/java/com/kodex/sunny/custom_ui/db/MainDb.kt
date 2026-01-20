package com.kodex.sunny.custom_ui.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.kodex.sunny.main_screen.tracker.data.TrackData

@Database(entities = [TrackData::class], version = 1)
abstract class MainDb: RoomDatabase()  {
    abstract val trackDao: TrackDao
}


