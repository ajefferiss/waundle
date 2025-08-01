package net.ddns.ajefferiss.waundle.util

import net.ddns.ajefferiss.waundle.data.Hill
import org.apache.commons.csv.CSVFormat
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.io.StringWriter
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException

data class HillBaggingImport(val hillNumber: Long, val climbed: LocalDate)

const val CLIMBED_IDX_SHORT = 7
const val CLIMBED_IDX_LONG = 24
const val SHORT_FILE_COLUMNS = 13

fun fromHillBaggingDate(walkedDate: String) = LocalDate.parse(
    walkedDate,
    DateTimeFormatter.ofPattern("dd/MM/yyyy")
)

@Throws(IOException::class, DateTimeParseException::class)
fun parseHillBaggingCSV(inputStream: InputStream): List<HillBaggingImport> =
    BufferedReader(InputStreamReader(inputStream)).use { reader ->
        return CSVFormat.Builder.create(CSVFormat.RFC4180).apply {
            setHeader()
            setSkipHeaderRecord(true)
            setIgnoreSurroundingSpaces(true)
        }.get().parse(reader)
            .map {
                val walked = if (it.size() > SHORT_FILE_COLUMNS) {
                    it[CLIMBED_IDX_LONG]
                } else {
                    it[CLIMBED_IDX_SHORT]
                }

                HillBaggingImport(
                    hillNumber = it[0].toLong(),
                    climbed = fromHillBaggingDate(walked)
                )
            }.toList()
    }

fun exportHillBaggingCSV(hills: List<Hill>): String {
    val stringWriter = StringWriter()
    CSVFormat.RFC4180.print(stringWriter).apply {
        printRecord("hillnumber", "climbed")
        hills.filter { it.climbed != null }
            .forEach { hill ->
                printRecord(
                    hill.number,
                    hill.climbed?.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
                )
            }
    }

    return stringWriter.toString().trim()
}