package net.ddns.ajefferiss.waundle.view

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import net.ddns.ajefferiss.waundle.Graph
import net.ddns.ajefferiss.waundle.data.Hill
import net.ddns.ajefferiss.waundle.data.HillRepository

class WaundleViewModel(
    private val hillRepository: HillRepository = Graph.hillRepository
) : ViewModel() {

    lateinit var walkedHills: Flow<List<Hill>>

    init {
        viewModelScope.launch {
            walkedHills = hillRepository.getWalkedHills()
        }
    }

    fun addHill(hill: Hill) {
        viewModelScope.launch(Dispatchers.IO) {
            hillRepository.addHill(hill)
        }
    }

    fun updateHill(hill: Hill) {
        viewModelScope.launch {
            hillRepository.updateHill(hill)
        }

        if (hill.climbed != null) {
            walkedHills = hillRepository.getWalkedHills()
        }
    }

    fun getHillById(id: Long): Flow<Hill> {
        return hillRepository.getHillById(id)
    }
}