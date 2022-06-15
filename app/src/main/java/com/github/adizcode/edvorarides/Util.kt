package com.github.adizcode.edvorarides

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.math.abs

fun isFutureDate(dateString: String): Boolean {
    val dateTime = parseProcessedStringToDateTime(dateString)
    return isFutureDate(dateTime)
}

fun isPastDate(dateString: String): Boolean {
    val dateTime = parseProcessedStringToDateTime(dateString)
    return isPastDate(dateTime)
}

private fun isFutureDate(dateTime: LocalDateTime): Boolean {
    return dateTime.isAfter(LocalDateTime.now())
}

private fun isPastDate(dateTime: LocalDateTime): Boolean {
    return dateTime.isBefore(LocalDateTime.now())
}

private fun parseProcessedStringToDateTime(dateString: String): LocalDateTime {
    val formatter = DateTimeFormatter.ofPattern("dd MMM yyyy HH:mm")
    return LocalDateTime.parse(dateString, formatter)
}

fun processDate(dateString: String): String {
    val dateTime = parseStringToDateTime(dateString)
    return parseDateTimeToString(dateTime)
}

private fun parseDateTimeToString(dateTime: LocalDateTime): String {
    val formatter = DateTimeFormatter.ofPattern("dd MMM yyyy HH:mm")
    return dateTime.format(formatter)
}

private fun parseStringToDateTime(dateString: String): LocalDateTime {
    val formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy hh:mm a", Locale.US)
    return LocalDateTime.parse(dateString, formatter)
}

fun computeDistance(userStationCode: Int, stationPath: List<Int>): Int {

    val closestStationCode = findClosest(userStationCode, stationPath)

    return abs(closestStationCode - userStationCode)
}

private fun findClosest(userStationCode: Int, stationPath: List<Int>): Int {

    if (userStationCode <= stationPath.first()) {
        return stationPath.first()
    }

    if (userStationCode >= stationPath.last()) {
        return stationPath.last()
    }

    if (stationPath.size > 2) {
        // Binary Search
        var low = 0
        var high = stationPath.lastIndex

        while (low + 1 < high) {
            val mid = low + (high - low) / 2
            if (stationPath[mid] == userStationCode) {
                return stationPath[mid]
            }

            if (stationPath[mid] > userStationCode) {
                high = mid
            } else {
                low = mid
            }
        }

        // Post processing
        return if (abs(stationPath[low] - userStationCode) < abs(stationPath[high] - userStationCode))
            stationPath[low]
        else stationPath[high]
    }

    // Linear Search
    return if (abs(stationPath[0] - userStationCode) < abs(stationPath[1] - userStationCode))
        stationPath[0]
    else stationPath[1]
}