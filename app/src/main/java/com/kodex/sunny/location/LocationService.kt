package com.kodex.sunny.location

import android.annotation.SuppressLint
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.ContentProviderClient
import android.content.Intent
import android.location.Location
import android.os.Build
import android.os.IBinder
import android.os.Looper
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.navigationevent.NavigationEventDispatcher
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.kodex.sunny.R
import com.kodex.sunny.location.data.LocationData
import com.kodex.sunny.main_screen.home.map_utils.PRIORITY
import com.kodex.sunny.main_screen.home.map_utils.START_TIME
import com.kodex.sunny.main_screen.home.map_utils.UPDATE_TIME
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.osmdroid.util.GeoPoint
import javax.inject.Inject
import kotlin.time.ExperimentalTime

@AndroidEntryPoint
class LocationService: Service() {
    @Inject
    lateinit var locationDataSharer: LocationDataSharer

    private lateinit var locationProviderClient: FusedLocationProviderClient
    private var distance = 0F
    private var lastLocation: Location? = null
    private var startTime: Long = 0L
    private val geoPoints = mutableListOf<GeoPoint>()


      private val locationCallback = object : LocationCallback() {
          override fun onLocationResult(locationResult: LocationResult) {
              super.onLocationResult(locationResult)
              val location = locationResult.lastLocation
              if (location != null) {
                  val geoPoint = GeoPoint(
                      location.latitude,
                      location.longitude,
                      location.altitude
                  )
                  geoPoints.add(geoPoint)
                  distance += lastLocation?.distanceTo(location) ?: 0F
                 Log.d("LocTag_1", "Location: ${location.latitude} ${location.longitude}")
                  val locationData = LocationData(
                      speed = location.speed,
                      distance = distance,
                      startServiceTime = startTime,
                      geoPoints = geoPoints,
                  )
                  CoroutineScope(Dispatchers.IO).launch {
                      locationDataSharer.updateLocation(
                          locationData
                      )
                  }
                  lastLocation = location
              }
          }
      }

    override fun onBind(intent: Intent?): IBinder? {

        return null
    }

    override fun onCreate() {
        initLocationProviderClient()
         super.onCreate()

    }

    override fun onDestroy() {
        locationProviderClient.removeLocationUpdates(locationCallback)
        super.onDestroy()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
       startTime = intent?.getLongExtra(START_TIME, -1L) ?: -1L
       val updateTime = intent?.getLongExtra(UPDATE_TIME, 3000L) ?: 3000L
       val priority = intent?.getIntExtra(PRIORITY, Priority.PRIORITY_HIGH_ACCURACY) ?: Priority.PRIORITY_HIGH_ACCURACY
        showNotification()
        startLocationUpdates(priority, updateTime)
        return START_STICKY
    }

    private fun initLocationProviderClient() {
        locationProviderClient = LocationServices
            .getFusedLocationProviderClient(this)
    }

    @SuppressLint("MissingPermission")
    private fun  startLocationUpdates(locationPriority: Int, updateLocationTime: Long) {
        val locationRequest = LocationRequest.Builder(
            locationPriority, updateLocationTime
        ).build()
        locationProviderClient.requestLocationUpdates(
            locationRequest,
            locationCallback,
            Looper.myLooper(),

        )
    }

    private fun showNotification() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                "channel_1",
                "Location Service",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            val manager = getSystemService(NotificationManager::class.java)
            manager.createNotificationChannel(channel)
        }
        val intent = Intent(this, LocationService::class.java)
        val contentIntent = PendingIntent.getActivity(
            this,
            0,
            intent,
            PendingIntent.FLAG_IMMUTABLE
        )
        val notification = NotificationCompat.Builder(
            this,
            "channel_1")
            .setContentTitle("GPS Tracker - Location Service")
            .setContentText("GPS Tracker Running")
            .setSmallIcon(R.drawable.my_location)
            .setContentIntent(contentIntent)
            .build()
        startForeground(10,notification)

    }
}