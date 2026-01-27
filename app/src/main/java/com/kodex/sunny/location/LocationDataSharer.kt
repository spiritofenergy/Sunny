package com.kodex.sunny.location

import com.kodex.sunny.location.data.LocationData
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import javax.inject.Singleton

@Singleton
class LocationDataSharer() {
    private val _locationDataFlow = MutableSharedFlow<LocationData>()
    val locationDataFlow = _locationDataFlow.asSharedFlow()


    suspend fun updateLocation(locationData: LocationData) {
        _locationDataFlow.emit(locationData)
    }


}