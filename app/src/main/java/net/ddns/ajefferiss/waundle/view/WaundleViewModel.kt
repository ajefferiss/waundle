package net.ddns.ajefferiss.waundle.view

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import net.ddns.ajefferiss.waundle.Graph
import net.ddns.ajefferiss.waundle.data.Hill
import net.ddns.ajefferiss.waundle.data.HillRepository

class WaundleViewModel(
    private val hillRepository: HillRepository = Graph.hillRepository
) : ViewModel() {

    lateinit var walkedHills: Flow<List<Hill>>
    var loading: Flow<Boolean>

    init {
        loading = flowOf(true)
        viewModelScope.launch {
            walkedHills = hillRepository.getWalkedHills()
            loading = flowOf(false)
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

    fun searchBy(search: String): Flow<List<Hill>> {
        return hillRepository.searchByNameOrCountry(search)
    }
}