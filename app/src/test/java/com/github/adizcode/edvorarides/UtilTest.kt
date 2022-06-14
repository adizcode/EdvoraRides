package com.github.adizcode.edvorarides

import org.junit.Test
import java.time.LocalDateTime

class UtilKtTest {

    @Test
    fun parseStringToDate() {
        val dateString = "03/13/2022 12:49 PM"
        var dateTime: LocalDateTime? = null

        dateTime = parseStringToDateTime(dateString)
    }
}