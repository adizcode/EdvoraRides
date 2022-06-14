package com.github.adizcode.edvorarides

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class RidesRepository {

    private val apiService = RidesServiceHolder.getInstance()

    suspend fun getRides(): List<Ride> {
        return withContext(Dispatchers.IO) { apiService.listRides() }
    }

    suspend fun getUser(): User {
        return withContext(Dispatchers.IO) { apiService.getUser() }
    }
}