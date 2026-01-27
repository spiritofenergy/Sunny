package com.kodex.sunny.utils

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build

object PermissionManager {

    fun checkFineLocation(
        context: Context,
        onPermissionGranted: () -> Unit,
        onPermissionDenied: () -> Unit
    ) {
        if (context.checkSelfPermission(
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            onPermissionGranted()
        } else {
            onPermissionDenied()
        }
    }

    fun checkBackgroundLocation(
        context: Context,
        onPermissionGranted: () -> Unit,
        onPermissionDenied: () -> Unit
    ) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) {
            if (context.checkSelfPermission(
                    Manifest.permission.ACCESS_BACKGROUND_LOCATION
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                onPermissionGranted()
            } else {
                onPermissionDenied()
            }
        }
    }
}

