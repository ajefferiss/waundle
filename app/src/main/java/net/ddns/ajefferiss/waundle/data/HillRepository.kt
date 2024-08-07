package net.ddns.ajefferiss.waundle.data

import kotlinx.coroutines.flow.Flow

class HillRepository(private val hillDAO: HillDAO) {

    suspend fun addHill(hill: Hill) {
        hillDAO.addHill(hill)
    }

    fun getAllHills(): Flow<List<Hill>> = hillDAO.getAllHills()

    fun getWalkedHills(): Flow<List<Hill>> = hillDAO.getWalkedHills()

    fun getHillById(id: Long): Flow<Hill> {
        return hillDAO.getHillById(id)
    }

    suspend fun updateHill(hill: Hill) {
        hillDAO.updateHill(hill)
    }

    fun searchByNameOrCountry(searchText: String): Flow<List<Hill>> {
        return hillDAO.filterHillsByNameOrCountry(searchText)
    }

    fun searchNearbyLocation(
        latLow: Float,
        latHi: Float,
        lonLow: Float,
        lonHi: Float
    ): Flow<List<Hill>> {
        return hillDAO.nearbyHills(latLow, latHi, lonLow, lonHi)
    }

    fun resetProgress() {
        hillDAO.resetWalkedProgress()
    }
}