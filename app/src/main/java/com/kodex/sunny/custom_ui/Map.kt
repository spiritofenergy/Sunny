package com.kodex.sunny.custom_ui

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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.kodex.sunny.custom_ui.RoundedCornerText
import com.kodex.sunny.location.data.MapData
import com.kodex.sunny.R
import com.kodex.sunny.main_screen.home.map_utils.initMyLocationOverlay
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Polyline
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay


@Composable
fun Map(
    lineColor: Int,
    lineWidth: Float,
    timerText: String,
    mapData: MapData?,
    buttonsPanel: @Composable () -> Unit = {},
    topTextPanel: @Composable () -> Unit = {},
    topButtonIconId: Int,
    middleButtonIconId: Int,
    bottomButtonIconId: Int,
    onTopButtonClick: (MapView, MyLocationNewOverlay) -> Unit,
    onMiddleButtonClick: (MapView, MyLocationNewOverlay) -> Unit,
    onBottomButtonClick: (MapView, MyLocationNewOverlay) -> Unit,
    onPolylineInit: (Polyline) -> Unit,
  ) {
    val context = LocalContext.current

    val mapView = remember {
        MapView(context).apply {
            setTileSource(TileSourceFactory.MAPNIK)
            setMultiTouchControls(true)
            controller.setZoom(15.0)

        }
    }
    var myLocationOverlay by remember {
        mutableStateOf<MyLocationNewOverlay?>(null)
    }

    LaunchedEffect(Unit) {
        val polyline = Polyline().apply {
            outlinePaint.color = lineColor
            outlinePaint.strokeWidth = lineWidth
        }
        onPolylineInit(polyline)

        myLocationOverlay = initMyLocationOverlay(mapView)
        mapView.overlays.add(polyline)
        mapView.overlays.add(myLocationOverlay)

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
            Column() {
                topTextPanel()
                RoundedCornerText(text = stringResource(R.string.time) + ": ${timerText}")
                Spacer(Modifier.padding(3.dp))
                RoundedCornerText(text = stringResource(R.string.average_speed) + ": ${mapData?.averageSpeed ?: 0.0} km/h")
                Spacer(Modifier.padding(3.dp))
                RoundedCornerText(text = stringResource(R.string.speed) + ": ${mapData?.speed ?: 0.0}  km/h")
                Spacer(Modifier.padding(3.dp))
                RoundedCornerText(text = stringResource(R.string.distance) + ": ${mapData?.distance ?: 0.0} km",
                    fontSize = 22,
                    fontWeight = FontWeight.Bold
                )
            }
            Column(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.End
            ) {
                buttonsPanel()

                Spacer(Modifier.height(5.dp))

                FloatingActionButton(
                    onClick = {
                        onTopButtonClick(mapView, myLocationOverlay!!)
                    }
                ) {
                    Icon(
                        painter = painterResource(topButtonIconId),
                        contentDescription = "Top Button"
                    )
                }

                Spacer(Modifier.height(5.dp))
                FloatingActionButton(
                    onClick = {
                        onMiddleButtonClick(mapView, myLocationOverlay!!)
                    }
                ) {
                    Icon(
                        painter = painterResource(middleButtonIconId),
                        contentDescription = "Middle Button"
                    )
                }
                Spacer(Modifier.height(5.dp))
                FloatingActionButton(
                    onClick = {
                       onBottomButtonClick(mapView, myLocationOverlay!!)
                    }
                ) {
                    Icon(
                        painter = painterResource(bottomButtonIconId),
                        contentDescription = "Button Botton"
                    )
                }
            }
            }
        }
    }
