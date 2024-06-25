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

    fun searchForHill(text: String): Flow<List<Hill>> {
        return hillDAO.searchForHill(text)
    }
}