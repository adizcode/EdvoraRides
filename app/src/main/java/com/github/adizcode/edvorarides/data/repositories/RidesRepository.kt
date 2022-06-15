package com.github.adizcode.edvorarides.data.repositories

import com.github.adizcode.edvorarides.data.model.Ride
import com.github.adizcode.edvorarides.data.network.ApiServiceHolder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class RidesRepository {

    private val apiService = ApiServiceHolder.getInstance()

    suspend fun getRides(): List<Ride> {
        return withContext(Dispatchers.IO) { apiService.listRides() }
    }
}