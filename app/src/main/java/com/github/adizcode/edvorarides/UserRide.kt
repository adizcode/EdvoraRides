package com.github.adizcode.edvorarides

class UserRide private constructor(
    val user: User,
    val ride: Ride,
    val date: String,
    val distance: Int
) {
    companion object {
        // TODO: computeDistance freezes screen
        fun createUserRide(user: User, ride: Ride): UserRide {
            return UserRide(
                user = user,
                ride = ride,
                date = processDate(ride.date),
                distance = computeDistance(user.station_code, ride.station_path)
//                distance = (0..100).random()
            )
        }
    }
}