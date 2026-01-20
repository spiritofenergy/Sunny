package com.kodex.sunny.main_screen.home.map_utils

import android.annotation.SuppressLint
import android.app.ActivityManager
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.core.graphics.drawable.toBitmap
import com.google.android.gms.location.Priority
import com.kodex.sunny.R
import com.kodex.sunny.location.LocationService
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Polyline
import org.osmdroid.views.overlay.mylocation.GpsMyLocationProvider
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay

const val START_TIME = "start_time"
const val UPDATE_TIME = "update_time"
const val PRIORITY = "priority"


fun initMyLocationOverlay(mapView: MapView): MyLocationNewOverlay {
    val gpsProvider = GpsMyLocationProvider(mapView.context)
    val myLocationOverlay = MyLocationNewOverlay(gpsProvider, mapView)
    val personBitmap = mapView.context.getDrawable(R.drawable.my_location_over)
        ?.toBitmap()
    val directionBitmap = mapView.context.getDrawable(R.drawable.follow_location)
        ?.toBitmap()
    myLocationOverlay.setPersonIcon(personBitmap)
    myLocationOverlay.setDirectionIcon(directionBitmap)
    myLocationOverlay.enableMyLocation()
    myLocationOverlay.enableFollowLocation()
    return myLocationOverlay
}

fun startLocationService(
    context: Context, startTime: Long,
    updateInterval: Long,
    priority: String
) {
    val intent = Intent(context, LocationService::class.java).apply {
        putExtra(START_TIME, startTime)
        putExtra(UPDATE_TIME, updateInterval)
        putExtra(PRIORITY, getPriority(priority))
    }
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        context.startForegroundService(intent)
    } else {
        context.startService(intent)
    }
}

fun stopLocationService(context: Context) {
    val intent = Intent(context, LocationService::class.java)
    context.stopService(intent)
}

fun isServiceRunning(context: Context): Boolean {
    val acManager = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
    for (service in acManager.getRunningServices(Int.Companion.MAX_VALUE)) {
        return LocationService::class.java.name == service.service.className
    }
    return false
}

@SuppressLint("DefaultLocale")
fun getAverageSpeed(distance: Float, startTime: Long): String {
    return if (distance == 0.0f) {
        "0.0"
    } else {
        val averageSpeed = 3.6f * (distance / ((System.currentTimeMillis() - startTime) / 1000f))
        String.format("%.1f  ", averageSpeed)
    }
}

private fun getPriority(priority: String): Int {
    return when (priority) {
        "PRIORITY_HIGH_ACCURACY" -> Priority.PRIORITY_HIGH_ACCURACY
        "PRIORITY_LOW_POWER" -> Priority.PRIORITY_LOW_POWER
        "PRIORITY_PASSIVE" -> Priority.PRIORITY_PASSIVE
        "PRIORITY_BALANCED_POWER_ACCURACY" -> Priority.PRIORITY_BALANCED_POWER_ACCURACY
        else -> Priority.PRIORITY_HIGH_ACCURACY
    }
}

fun geoPointsToString(geoPoints: List<GeoPoint>?): String {
    val stringBuilder = StringBuilder()
    if (geoPoints == null) {
        return stringBuilder.toString()
    }
    for (geoPoint in geoPoints) {
        if (stringBuilder.isEmpty()) {
            stringBuilder.append("${geoPoint.latitude},${geoPoint.longitude}")
        } else {
            stringBuilder.append("/${geoPoint.latitude},${geoPoint.longitude}")
        }
    }
    return stringBuilder.toString()
}

fun getPolyLineFromString(geoPointsString: String, color: ULong, trackWidth: Float): Polyline {
    val myPolyLine = Polyline().apply {
        outlinePaint.color = Color(color).toArgb()
        outlinePaint.strokeWidth = trackWidth
    }
    geoPointsString.split("/").forEach { geoData ->
        val geoPoints = geoData.split(",")
        val geoPoint = GeoPoint(
            geoPoints.first().toDouble(),
            geoPoints.last().toDouble()
        )
        myPolyLine.addPoint(geoPoint)

    }
    return myPolyLine
}
