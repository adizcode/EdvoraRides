package com.github.adizcode.edvorarides

import org.junit.Test

class UtilTest {

    @Test
    fun processDateTimeString() {
        val dateTimeString = "03/13/2022 12:49 PM"
        val processedDateTimeString = processDate(dateTimeString)

        assert(processedDateTimeString == "13 Mar 2022 12:49")
    }

    @Test
    fun computeDistance() {
        val userStationCode = 99
        val stationPath = listOf(10, 15, 99, 200, 9000)

        val distance = computeDistance(userStationCode, stationPath)

        assert(distance == 0)
    }
}