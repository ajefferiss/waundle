package net.ddns.ajefferiss.waundle.data

import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.Assertions.assertEquals

class ClassificationTest {

    private lateinit var classification: Classification

    @Before
    fun setup() {
        classification = Classification()
    }

    @Test
    fun testClassify() {
        assertEquals(listOf("Munro"), classification.toClassifications("M"))
        assertEquals(listOf("Munro"), classification.toClassifications(",M,"))
        assertEquals(listOf("Munro"), classification.toClassifications("  , M, "))
        assertEquals(listOf("Munro", "T"), classification.toClassifications("M,T"))
    }
}