package net.ddns.ajefferiss.waundle.data

import androidx.sqlite.db.SimpleSQLiteQuery
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking

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

    fun searchByCountryAndHillCategory(
        country: String,
        hillCategory: String
    ): Flow<List<Hill>> {
        return hillDAO.getHillsByCountryAndCategory(country, hillCategory)
    }

    fun resetProgress() {
        hillDAO.resetWalkedProgress()
    }

    fun getCountryOtherHills(
        countryCode: String,
        ignoreCategories: List<HillClassification>
    ): Flow<List<Hill>> {
        // SELECT * FROM hills_table WHERE country = "M" AND classifications NOT LIKE "%|Ma|%" AND classifications NOT LIKE "%|SIB|%"
        val placeHolders = ignoreCategories.joinToString(" AND ") { "classifications NOT LIKE ?" }
        val args = ignoreCategories.map { "%|${it.code}|%" }.toTypedArray()
        val query = SimpleSQLiteQuery(
            "SELECT * FROM `hills_table` WHERE country LIKE '%$countryCode%' AND $placeHolders",
            args
        )

        return runBlocking {
            flowOf(hillDAO.getCountryOtherHills(query))
        }
    }
}