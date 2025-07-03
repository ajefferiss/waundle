package net.ddns.ajefferiss.waundle.util

import net.ddns.ajefferiss.waundle.data.Hill
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Test
import java.time.LocalDate

class CSVUtilTest {

    @Test
    fun `CSV Util Parses Hill Bagging Export`() {
        val expectedHillImports = listOf(
            HillBaggingImport(1963, fromHillBaggingDate("01/07/2013")),
            HillBaggingImport(2137, fromHillBaggingDate("01/09/2004")),
            HillBaggingImport(2359, fromHillBaggingDate("25/03/2015")),
            HillBaggingImport(2779, fromHillBaggingDate("05/05/2014")),
            HillBaggingImport(2780, fromHillBaggingDate("05/05/2014")),
            HillBaggingImport(2783, fromHillBaggingDate("05/05/2014")),
            HillBaggingImport(5385, fromHillBaggingDate("23/11/2019"))
        )

        val inputStream =
            object {}.javaClass.getResourceAsStream("/hillsImport.csv")

        assertNotNull("Resource file is null", inputStream)

        val importedFiles = parseHillBaggingCSV(inputStream!!)
        assertEquals(expectedHillImports, importedFiles)
    }

    @Test
    fun `CSV Util Creates Hill Bagging Import`() {
        val expectedExport = "hillnumber,climbed\r\n123,2025-03-07\r\n234,2025-03-01"
        val toExport = listOf(
            Hill(hillId = 1, number = 123, climbed = LocalDate.of(2025, 3, 7)),
            Hill(hillId = 2, number = 234, climbed = LocalDate.of(2025, 3, 1))
        )

        assertEquals(expectedExport, exportHillBaggingCSV(toExport))
    }
}