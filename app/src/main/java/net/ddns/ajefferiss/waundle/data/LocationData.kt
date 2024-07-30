package net.ddns.ajefferiss.waundle.data

const val UNSET_LOCATION = Double.MAX_VALUE

data class LocationData(
    val latitude: Double = UNSET_LOCATION,
    val longitude: Double = UNSET_LOCATION
)
