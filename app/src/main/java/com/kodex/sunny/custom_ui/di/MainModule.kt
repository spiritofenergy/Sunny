package com.kodex.sunny.custom_ui.di

import com.kodex.sunny.utils.TimerManager


import android.app.Application
import androidx.room.Room
import com.kodex.sunny.custom_ui.db.MainDb
import com.kodex.sunny.location.LocationDataSharer
import com.kodex.sunny.utils.SettingsPreferenceManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class MainModule {

    @Provides
    @Singleton
    fun provideMainDb(app : Application): MainDb {
        return Room.databaseBuilder(
        app ,
        MainDb::class.java,
        "gps_tracker"
    ).build()
    }

    @Provides
    @Singleton
    fun provideTimeManager(): TimerManager {
        return TimerManager()
    }

    @Provides
    @Singleton
    fun provideLocationFlow(): LocationDataSharer {
        return LocationDataSharer()

    }
    @Provides
    @Singleton
    fun provideSettingPreferencesManager(application: Application): SettingsPreferenceManager {
        return SettingsPreferenceManager(application)

    }
}