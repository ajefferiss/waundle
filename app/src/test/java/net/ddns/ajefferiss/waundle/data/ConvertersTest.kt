package net.ddns.ajefferiss.waundle.data

import org.junit.Test
import org.junit.jupiter.api.Assertions.*
import java.time.LocalDate

class ConvertersTest() {

    private val converters = Converters()
    private val epochDay: Long = 20156

    @Test
    fun `Converter returns null for null local data`() {
        assertNull(converters.dateToTimestamp(null))
    }

    @Test
    fun `Converter returns null for null long`() {
        assertNull(converters.fromTimestamp(null))
    }

    @Test
    fun `Converter creates LocalDate from Long`() {
        val converted = converters.fromTimestamp(epochDay)
        assertNotNull(converted)

        assertEquals(2025, converted!!.year)
        assertEquals(3, converted!!.monthValue)
        assertEquals(9, converted!!.dayOfMonth)
    }

    @Test
    fun `Converter create Long from LocalDate`() {
        val localDate = LocalDate.of(2025, 3, 9)
        assertEquals(epochDay, converters.dateToTimestamp(localDate))
    }
}