package net.ddns.ajefferiss.waundle.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.maps.android.compose.MapType
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

val GOOGLE_MAP_TYPES_BY_NAME = listOf(
    MapType.SATELLITE.name,
    MapType.NORMAL.name,
    MapType.HYBRID.name,
    MapType.TERRAIN.name
)

val GOOGLE_MAP_TYPES_MAP = mapOf(
    MapType.SATELLITE.name to MapType.SATELLITE,
    MapType.NORMAL.name to MapType.NORMAL,
    MapType.HYBRID.name to MapType.HYBRID,
    MapType.TERRAIN.name to MapType.TERRAIN
)

data class WaundlePreferences(
    val mapType: MapType,
    val nearbyDistance: Int,
    val baggingDistance: Int
)

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "waundlePrefs")

object DataStoreDefaults {
    val mapType = MapType.TERRAIN
    val nearbyDistance = 5000
    val baggingDistance = 50
}

class WaundlePreferencesRepository(private val context: Context) {
    private val _prefs = MutableLiveData<WaundlePreferences>()
    val preferences: LiveData<WaundlePreferences> = _prefs

    private val mapType = stringPreferencesKey("map_type")
    private val nearbyDistance = intPreferencesKey("nearby_distance")
    private val baggingDistance = intPreferencesKey("bagging_distance")

    init {
        CoroutineScope(Dispatchers.Main).launch {
            context.dataStore.data.collect { prefs ->
                _prefs.value = WaundlePreferences(
                    mapType = MapType.valueOf(prefs[mapType] ?: DataStoreDefaults.mapType.name),
                    nearbyDistance = prefs[nearbyDistance] ?: DataStoreDefaults.nearbyDistance,
                    baggingDistance = prefs[baggingDistance] ?: DataStoreDefaults.baggingDistance
                )
            }
        }
    }

    suspend fun updateWaundlePrefs(waundlePreferences: WaundlePreferences) {
        context.dataStore.edit { prefs ->
            prefs[mapType] = waundlePreferences.mapType.name
            prefs[nearbyDistance] = waundlePreferences.nearbyDistance
            prefs[baggingDistance] = waundlePreferences.baggingDistance

            _prefs.value = waundlePreferences
        }
    }
}