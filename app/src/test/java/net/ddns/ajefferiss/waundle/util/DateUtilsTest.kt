package net.ddns.ajefferiss.waundle.util

import org.junit.Assert.assertEquals
import org.junit.Test
import java.time.LocalDate

class DateUtilsTest {

    @Test
    fun `Date Utils Formats Date Correctly Parses Hill Bagging Export`() {
        val date = LocalDate.of(2025, 3, 9)
        val expectedDateString = "09/03/2025"

        assertEquals(expectedDateString, formatWalkedDate(date))
    }
}