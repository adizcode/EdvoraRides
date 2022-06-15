package com.github.adizcode.edvorarides.data.model

import com.github.adizcode.edvorarides.util.computeDistance
import com.github.adizcode.edvorarides.util.processDate

class UserRide private constructor(
    val ride: Ride,
    val date: String,
    val distance: Int
) {
    companion object {
        fun createUserRide(userStationCode: Int, ride: Ride): UserRide {
            return UserRide(
                ride = ride,
                date = processDate(ride.date),
                distance = computeDistance(userStationCode, ride.station_path)
            )
        }
    }
}