package com.kodex.sunny.main_screen.map.ui

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.kodex.sunny.custom_ui.Map
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
 import com.kodex.sunny.R
import com.kodex.sunny.location.data.MapData
import com.kodex.sunny.main_screen.home.map_utils.getAverageSpeed
import com.kodex.sunny.main_screen.home.map_utils.isServiceRunning
import com.kodex.sunny.main_screen.home.map_utils.startLocationService
import com.kodex.sunny.main_screen.home.map_utils.stopLocationService
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Polyline
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay

@SuppressLint("DefaultLocale")
@Composable
fun MapScreen(
    viewModel: MapViewModel = hiltViewModel()
) {
    val context = LocalContext.current

    val mapView = remember {
        MapView(context).apply {
            setTileSource(TileSourceFactory.MAPNIK)
            setMultiTouchControls(true)
            controller.setZoom(15.0)

        }
    }
    var showSaveTrackDialog by remember {
        mutableStateOf(false)
    }
    var mapDataState by remember {
        mutableStateOf<MapData?>(null)
    }
    var myLocationOverlay by remember {
        mutableStateOf<MyLocationNewOverlay?>(null)
    }
    var isLocationServiceRunning by remember {
        mutableStateOf(false)
    }
    var isResumPolyline by remember {
        mutableStateOf(false)
    }
    var isStartTime by remember {
        mutableStateOf(false)
    }
    var distance by remember {
        mutableStateOf("0.0")
    }

    var speed by remember {
        mutableStateOf("0.0")
    }
    var averageSpeed by remember {
        mutableStateOf("0.0")
    }

    var myPolyLine by remember {
        mutableStateOf<Polyline?>(null)
    }

    LaunchedEffect(Unit) {
       // myPolyLine = Polyline().apply {
          //  outlinePaint.color = Color(viewModel.getTraCKColor().toULong()).toArgb()
          //  outlinePaint.strokeWidth = viewModel.getTrackLineWidth().toFloat()
       // }
        /*myLocationOverlay = initMyLocationOverlay(mapView)
        mapView.overlays.add(myPolyLine)
        mapView.overlays.add(myLocationOverlay)*/

        isLocationServiceRunning = isServiceRunning(context)
        viewModel.locationDataFlow.collect { locationData ->

            Log.d("LocData", "Update call")

            val averageSpeed = getAverageSpeed(locationData.distance, locationData.startServiceTime)
            val speed = String.format("%.1f", (3.6f * locationData.speed))
            val distance = String.format("%.1f", locationData.distance / 1000f)

            mapDataState = MapData(averageSpeed, distance, speed)
            Log.d("LocData", distance + " " + speed + " " + averageSpeed)
            // myPolyLine?.addPoint(locationData.geoPoints.last())


            if (isLocationServiceRunning) {
                if (!isResumPolyline) {
                    isResumPolyline = true
                    locationData.geoPoints.forEach { geoPoint ->
                        myPolyLine?.addPoint(geoPoint)
                    }
                } else {
                    myPolyLine?.addPoint(locationData.geoPoints.last())
                }
            }
            if (isLocationServiceRunning && !isStartTime) {
                isStartTime = true
                viewModel.startTimer(locationData.startServiceTime)
            }
        }
    }
    Box(modifier = Modifier.fillMaxSize()) {
        AndroidView(
            {
                mapView
            },
            Modifier.fillMaxSize()
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.SpaceBetween
        )
        {
          /*  Column() {
                RoundedCornerText(text = stringResource( R.string.time) + ": ${viewModel.timerState.value}")
                Spacer(Modifier.padding(3.dp))
                RoundedCornerText(text = stringResource( R.string.average_speed) + ": $averageSpeed km/h)")
                Spacer(Modifier.padding(3.dp))
                RoundedCornerText(text = stringResource(R.string.speed) + ": $speed  km/h")
                Spacer(Modifier.padding(3.dp))
                RoundedCornerText(text = stringResource(R.string.distance) + ": $distance km",
                    fontSize = 22,
                    fontWeight = FontWeight.Bold
                )
            }*/
            Column(
                modifier = Modifier
                    .fillMaxWidth(), horizontalAlignment = Alignment.End
            ) {
                Spacer(Modifier.height(5.dp))
                FloatingActionButton(
                    onClick = {
                        myLocationOverlay?.enableFollowLocation()
                    }
                ) {
                    Icon(
                        painter = painterResource(R.drawable.my_location_over),
                        contentDescription = "1"
                    )
                }

                Spacer(Modifier.height(5.dp))
                FloatingActionButton(
                    onClick = {
                        mapView.controller.animateTo(myLocationOverlay?.myLocation)
                    }
                ) {
                    Icon(
                        painter = painterResource(R.drawable.my_location),
                        contentDescription = "2"
                    )
                }
                Spacer(Modifier.height(5.dp))
                FloatingActionButton(
                    onClick = {
                        if (isLocationServiceRunning) {
                            stopLocationService(context)
                            isLocationServiceRunning = false
                            viewModel.stopTimer()
                            showSaveTrackDialog = true

                        } else {
                            isLocationServiceRunning = true
                            val startTimerMillis = System.currentTimeMillis()
                            viewModel.startTimer(startTimerMillis)
                            startLocationService(
                                context,
                                startTimerMillis,
                                viewModel.getLocationUpdateInterval().toLong(),
                                viewModel.getPriority()
                            )
                        }
                    }
                ) {
                    Icon(
                        painter = painterResource(
                            if (isLocationServiceRunning) R.drawable.stop_location else R.drawable.play_location
                        ),
                        contentDescription = "3"
                    )
                }
            }
        }
        Map(
            lineColor = Color(viewModel.getTraCKColor().toULong()).toArgb(),
            lineWidth = viewModel.getTrackLineWidth().toFloat(),
            timerText = viewModel.timerState.value,
            mapData = mapDataState,
            topButtonIconId = R.drawable.my_location_over,
            middleButtonIconId = R.drawable.my_location,
            bottomButtonIconId = if (isLocationServiceRunning) {
                R.drawable.stop_location
            } else{ R.drawable.play_location
            },
            onTopButtonClick = { _, myLocationOverlay ->
                myLocationOverlay.enableFollowLocation()
            },
            onMiddleButtonClick = { mapView, myLocationOverlay ->
                mapView.controller.animateTo(myLocationOverlay?.myLocation)
            },
            onBottomButtonClick = { _, _ ->
                if (isLocationServiceRunning) {
                    stopLocationService(context)
                    isLocationServiceRunning = false
                    viewModel.stopTimer()
                    showSaveTrackDialog = true

                } else {
                    isLocationServiceRunning = true
                    val startTimerMillis = System.currentTimeMillis()
                    viewModel.startTimer(startTimerMillis)
                    startLocationService(
                        context,
                        startTimerMillis,
                        viewModel.getLocationUpdateInterval().toLong(),
                        viewModel.getPriority()
                    )
                }
            },
            onPolylineInit = { polyline ->
                myPolyLine = polyline
            }
        )



      /*  TrackDialog(
            title = stringResource(R.string.save_track),
            showDialog = showSaveTrackDialog,
            onDismiss = {
                showSaveTrackDialog = false
            },
            onConfirm = { trackName ->
                if (mapDataState == null) return@TrackDialog
                // viewModel.saveTrack(trackName)
                showSaveTrackDialog = false
                val tracerData = TrackData(
                    name = trackName,
                    date = TimeUtils.getDateAndTime(),
                    distance = mapDataState?.distance!!,
                    averagedSpeed = mapDataState?.averageSpeed!!,
                    geoPoints = geoPointsToString(myPolyLine?.actualPoints),
                )
                viewModel.insertTrack(tracerData)
            }
        )*/
    }
}

