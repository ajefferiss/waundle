package net.ddns.ajefferiss.waundle.util

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat
import com.google.android.gms.location.LocationServices

class LocationUtils(private val context: Context) {

    private val _fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)

    fun hasLocationPermission(context: Context): Boolean {

        val hasFine = ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED

        val hasCoarse = ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED

        return hasFine && hasCoarse
    }
}