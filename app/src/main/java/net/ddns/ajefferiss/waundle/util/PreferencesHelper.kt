package net.ddns.ajefferiss.waundle.util

import android.content.Context
import android.content.SharedPreferences
import com.google.maps.android.compose.MapType

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

object PreferencesHelper {
    val WAUNDLE_PREFS = "WaundlePrefs"
    val MAP_TYPE = "MapType"

    fun sharedPreferences(context: Context): SharedPreferences = context.getSharedPreferences(
        WAUNDLE_PREFS, Context.MODE_PRIVATE
    )

    inline fun SharedPreferences.edit(operation: (SharedPreferences.Editor) -> Unit) {
        val editor = edit()
        operation(editor)
        editor.apply()
    }

    var SharedPreferences.mapType
        get() = getString(MAP_TYPE, MapType.SATELLITE.name)
        set(value) {
            edit {
                it.putString(MAP_TYPE, value)
            }
        }
}