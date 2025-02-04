package net.ddns.ajefferiss.waundle.data

import net.ddns.ajefferiss.waundle.R

enum class CountryCode(val countryCode: String) {
    Scotland("S"),
    England("E"),
    Wales("W"),
    Ireland("I"),
    IsleOfMan("M")
}

data class CountryDetail(
    val nameId: Int,
    val descriptionId: Int,
    val image: Int,
    val countryCode: CountryCode
)

val SCOTLAND = CountryDetail(
    R.string.scotland,
    R.string.scotland_description,
    R.drawable.scotland_hill,
    CountryCode.Scotland
)
val ENGLAND = CountryDetail(
    R.string.england,
    R.string.england_description,
    R.drawable.english_hill,
    CountryCode.England
)
val IRELAND = CountryDetail(
    R.string.ireland,
    R.string.ireland_description,
    R.drawable.irish_hill,
    CountryCode.Ireland
)
val WALES = CountryDetail(
    R.string.wales,
    R.string.wales_description,
    R.drawable.welsh_hill,
    CountryCode.Wales
)
val ISLE_OF_MAN = CountryDetail(
    R.string.isle_of_man,
    R.string.isle_of_man_description,
    R.drawable.isle_of_man_hill,
    CountryCode.IsleOfMan
)

val CountriesList = listOf(SCOTLAND, ENGLAND, IRELAND, WALES, ISLE_OF_MAN)
val CountriesMap = mapOf<CountryCode, CountryDetail>(
    CountryCode.Scotland to SCOTLAND,
    CountryCode.England to ENGLAND,
    CountryCode.Ireland to IRELAND,
    CountryCode.Wales to WALES,
    CountryCode.IsleOfMan to ISLE_OF_MAN
)