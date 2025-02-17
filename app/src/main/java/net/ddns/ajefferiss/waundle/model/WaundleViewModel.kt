package net.ddns.ajefferiss.waundle.model

import android.graphics.PointF
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import net.ddns.ajefferiss.waundle.Graph
import net.ddns.ajefferiss.waundle.data.Hill
import net.ddns.ajefferiss.waundle.data.HillClassification
import net.ddns.ajefferiss.waundle.data.HillRepository
import net.ddns.ajefferiss.waundle.data.LocationData
import java.time.LocalDate
import kotlin.math.asin
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.sin

class WaundleViewModel(
    private val hillRepository: HillRepository = Graph.hillRepository
) : ViewModel() {
    private val _location = MutableStateFlow(LocationData())
    val location: StateFlow<LocationData> = _location.asStateFlow()

    lateinit var walkedHills: Flow<List<Hill>>
    var loading: Flow<Boolean>

    init {
        loading = flowOf(true)
        viewModelScope.launch {
            walkedHills = hillRepository.getWalkedHills()
            loading = flowOf(false)
        }
    }

    fun updateHill(hill: Hill) {
        viewModelScope.launch {
            hillRepository.updateHill(hill)
        }

        if (hill.climbed != null) {
            refreshWalkedHills()
        }
    }

    fun getHillById(id: Long): Flow<Hill> {
        return hillRepository.getHillById(id)
    }

    fun markHillWalked(hillId: Long, walkedDate: LocalDate) {
        viewModelScope.launch(Dispatchers.IO) {
            hillRepository.markHillWalked(hillId, walkedDate)
        }
    }

    fun refreshWalkedHills() {
        walkedHills = hillRepository.getWalkedHills()
    }

    fun searchBy(search: String): Flow<List<Hill>> {
        return hillRepository.searchByNameOrCountry(search)
    }

    fun searchNearbyHills(nearbyDistance: Int): Flow<List<Hill>> {
        val mult = 1.1
        val range = mult * nearbyDistance.toDouble()
        val p1 = calculateDerivedPosition(range, 0.0)
        val p2 = calculateDerivedPosition(range, 90.0)
        val p3 = calculateDerivedPosition(range, 180.0)
        val p4 = calculateDerivedPosition(range, 270.0)

        return hillRepository.searchNearbyLocation(p3.x, p1.x, p2.y, p4.y)
    }

    fun updateLocation(newLocation: LocationData) {
        _location.update { currentLocation ->
            currentLocation.copy(
                latitude = newLocation.latitude,
                longitude = newLocation.longitude
            )
        }
    }

    fun resetWalkedProgress() {
        viewModelScope.launch(Dispatchers.IO) {
            hillRepository.resetProgress()
        }
    }

    fun getHillsByCountryCategory(
        countryCode: String,
        category: String
    ): Flow<List<Hill>> {
        return hillRepository.searchByCountryAndHillCategory(
            countryCode,
            category
        )
    }

    fun getCountryOtherHills(
        countryCode: String,
        ignoreCategories: List<HillClassification>
    ): Flow<List<Hill>> {
        return hillRepository.getCountryOtherHills(countryCode, ignoreCategories)
    }

    private fun calculateDerivedPosition(range: Double, bearing: Double): PointF {
        // Taken from: https://stackoverflow.com/questions/3695224/sqlite-getting-nearest-locations-with-latitude-and-longitude
        val earthRadius = 6371000
        val latA = Math.toRadians(_location.value.latitude)
        val lonA = Math.toRadians(_location.value.longitude)
        val angularDistance = range / earthRadius
        val trueCourse = Math.toRadians(bearing)
        var lat = asin(
            sin(latA) * cos(angularDistance) + cos(latA) * sin(
                angularDistance
            ) * cos(trueCourse)
        )

        val dlon = atan2(
            sin(trueCourse) * sin(angularDistance) * cos(latA),
            cos(angularDistance) - sin(latA) * sin(lat)
        )
        var lon = ((lonA + dlon + Math.PI) % (Math.PI * 2)) - Math.PI

        lat = Math.toDegrees(lat)
        lon = Math.toDegrees(lon)

        val newPoint = PointF(lat.toFloat(), lon.toFloat())

        return newPoint
    }
}