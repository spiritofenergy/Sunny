package com.kodex.sunny.location.data

import org.osmdroid.util.GeoPoint

data class LocationData(
    val speed: Float = 0.0f,
    val distance: Float = 0.0f,
    val startServiceTime: Long = 0L,
    val geoPoints: List<GeoPoint>,
)
