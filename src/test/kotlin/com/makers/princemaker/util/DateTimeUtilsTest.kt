package com.makers.princemaker.util

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.time.LocalDateTime

internal class DateTimeUtilsTest {
    @Test
    fun localDateTimeStringTest() {
        val result = LocalDateTime.of(2023, 12, 21, 10, 10)
            .getLocalDateTimeString()

        assertEquals("2023-12-21 탄생", result)
    }
}