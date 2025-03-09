package net.ddns.ajefferiss.waundle.data

import org.junit.Test
import org.junit.jupiter.api.Assertions.*

class HillClassificationTest {

    @Test
    fun `Find hill by classification return Enum`() {
        val foundClassification = HillClassification.findByCode("W")
        assertEquals(foundClassification, HillClassification.Wainwright)
    }

    @Test
    fun `Unknown classification returns null`() {
        assertNull(HillClassification.findByCode("Unknown"))
    }

    @Test
    fun `Full names are returned for codes`() {
        assertEquals("Wainwright", HillClassification.namesFromCode(listOf("W")))
        assertEquals("Wainwright, Birkett", HillClassification.namesFromCode(listOf("W", "B")))
    }

    @Test
    fun `Empty string returned for unknown codes`() {
        assertEquals("", HillClassification.namesFromCode(listOf("Unknown")))
    }
}