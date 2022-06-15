package com.github.adizcode.edvorarides.data.network

import com.github.adizcode.edvorarides.data.model.Ride
import com.github.adizcode.edvorarides.data.model.User
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

const val BASE_URL = "https://assessment.api.vweb.app/"

interface ApiService {
    @GET("rides")
    suspend fun listRides(): List<Ride>

    @GET("user")
    suspend fun getUser(): User
}

object ApiServiceHolder {
    private var INSTANCE: ApiService? = null

    // TODO: Make this thread-safe
    fun getInstance(): ApiService {
        if (INSTANCE == null) {
            val retrofit =
                Retrofit.Builder().baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()

            INSTANCE = retrofit.create(ApiService::class.java)
        }

        return INSTANCE!!
    }
}