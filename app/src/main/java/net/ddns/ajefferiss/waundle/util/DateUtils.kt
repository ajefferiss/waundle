package net.ddns.ajefferiss.waundle.util

import java.time.LocalDate
import java.time.format.DateTimeFormatter

val WALKED_DATE_FORMAT = DateTimeFormatter.ofPattern("dd/MM/yyyy")

fun formatWalkedDate(walkedDate: LocalDate): String {
    return walkedDate.format(WALKED_DATE_FORMAT).toString()
}