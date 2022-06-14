package com.github.adizcode.edvorarides

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

const val BASE_URL = "https://assessment.api.vweb.app/"

interface RidesService {
    @GET("rides")
    suspend fun listRides(): List<Ride>

    @GET("user")
    suspend fun getUser(): User
}

object RidesServiceHolder {
    private var INSTANCE: RidesService? = null

    fun getInstance(): RidesService {
        if (INSTANCE == null) {
            val retrofit =
                Retrofit.Builder().baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()

            INSTANCE = retrofit.create(RidesService::class.java)
        }

        return INSTANCE!!
    }
}