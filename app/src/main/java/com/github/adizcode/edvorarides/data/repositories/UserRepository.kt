package com.github.adizcode.edvorarides.data.repositories

import com.github.adizcode.edvorarides.data.model.User
import com.github.adizcode.edvorarides.data.network.ApiServiceHolder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class UserRepository {

    private val apiService = ApiServiceHolder.getInstance()

    suspend fun getUser(): User {
        return withContext(Dispatchers.IO) { apiService.getUser() }
    }
}