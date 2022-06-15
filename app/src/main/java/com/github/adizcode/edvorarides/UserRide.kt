package com.github.adizcode.edvorarides

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