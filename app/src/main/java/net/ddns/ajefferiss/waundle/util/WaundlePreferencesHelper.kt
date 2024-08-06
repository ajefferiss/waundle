package net.ddns.ajefferiss.waundle.util

import android.content.Context
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import net.ddns.ajefferiss.waundle.data.DataStoreDefaults
import net.ddns.ajefferiss.waundle.data.WaundlePreferences
import net.ddns.ajefferiss.waundle.data.WaundlePreferencesRepository

class WaundlePreferencesHelper(val context: Context) {
    private val prefs: WaundlePreferencesRepository = WaundlePreferencesRepository(context)
    private val scope = CoroutineScope(Job() + Dispatchers.Main)
    private val defaultPrefs = WaundlePreferences(
        mapType = DataStoreDefaults.mapType,
        nearbyDistance = DataStoreDefaults.nearbyDistance,
        baggingDistance = DataStoreDefaults.baggingDistance
    )

    fun isReady(): Boolean {
        return prefs.preferences.isInitialized
    }

    fun getPrefs(): WaundlePreferences {
        if (!isReady() || prefs.preferences.value == null) {
            return defaultPrefs
        }

        return prefs.preferences.value!!
    }

    fun updatePrefs(newPrefs: WaundlePreferences) {
        scope.launch {
            prefs.updateWaundlePrefs(newPrefs)
        }
    }

    fun defaultPrefs(): WaundlePreferences {
        updatePrefs(defaultPrefs)
        return defaultPrefs
    }
}