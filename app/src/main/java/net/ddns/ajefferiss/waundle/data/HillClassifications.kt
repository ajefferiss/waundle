package net.ddns.ajefferiss.waundle.data

import net.ddns.ajefferiss.waundle.R

@Suppress("UNUSED")
enum class HillClassification(val code: String, val codeName: String, val imageId: Int? = null) {
    Marilyn("Ma", "Marilyn", R.drawable.classification_marilyn),
    Hump("Hu", "Hump"),
    Simm("Sim", "Simm"),
    Dodd("5", "Dodd"),
    Munro("M", "Munro", R.drawable.classification_munro),
    MunroTop("MT", "Munro Top"),
    Furth("F", "Furth"),
    Corbett("C", "Corbett", R.drawable.classification_corbett),
    Graham("G", "Graham", R.drawable.classification_graham),
    Donald("D", "Donald", R.drawable.classification_donald),
    DonaldTop("DT", "Donald Top"),
    Hewitt("Hew", "Hewitt", R.drawable.classification_hewitt),
    Nuttall("N", "Nuttall", R.drawable.classification_nuttall),
    Dewey("Dew", "Dewey"),
    DonaldDewey("DDew", "Donald Dewey"),
    HighlandFive("HF", "Highland Five"),
    FourHundredTump("4", "400-499m Tump"),
    ThreeHundredTump("3", "300-399m Tump (GB)"),
    TwoHundredTump("2", "200-299m Tump (GB)"),
    OneHundredTyump("1", "100-199m Tump (GB)"),
    Tump("0", "0-99m Tump (GB)"),
    Wainwright("W", "Wainwright", R.drawable.classification_wainwright),
    WainwrightOutlyingFell("WO", "Wainwright Outlying Fell"),
    Birkett("B", "Birkett", R.drawable.classification_birkett),
    Synge("Sy", "Synge"),
    Fellranger("Fel", "Fellranger"),
    CountyTopHistoric("CoH", "County Top – Historic (pre-1974)"),
    CountyTopAdmin("CoA", "County Top – Administrative (1974 mid-1990s)"),
    CountyTopCurrnt("CoU", "County Top – Current County or Unitary Authority"),
    CountyTopLondon("CoL", "County Top – Current London Borough"),
    SignificantIslandOfBritain("SIB", "Significant Island of Britain"),
    Dillon("Dil", "Dillon", R.drawable.classification_dillon),
    Arderin("A", "Arderin", R.drawable.classification_arderin),
    VandeleurLynam("VL", "Vandeleur-Lynam"),
    OtherList("O", "Other list"),
    Unclassified("Un", "unclassified"),
    Murdo("Mur", "Murdo", R.drawable.classification_murdo),
    Carn("Ca", "Carn"),
    Binnion("Bin", "Binnion"),
    OtherHills("Oths", "Other Hills");

    companion object {
        fun findByCode(value: String): HillClassification? = entries.find { it.code == value }

        fun namesFromCode(values: List<String>): String {
            var names = ""
            values.forEach { value ->
                val classification = findByCode(value)
                if (classification != null) {
                    names += classification.codeName + ", "
                }
            }

            return names.trim().trim(',')
        }
    }
}

val CountryClassifications = mapOf(
    CountryCode.Scotland to listOf(
        HillClassification.Corbett,
        HillClassification.Donald,
        HillClassification.Graham,
        HillClassification.Munro,
        HillClassification.Marilyn,
        HillClassification.Murdo,
        HillClassification.OtherHills
    ),
    CountryCode.England to listOf(
        HillClassification.Birkett,
        HillClassification.Hewitt,
        HillClassification.Marilyn,
        HillClassification.Nuttall,
        HillClassification.Wainwright,
        HillClassification.OtherHills
    ),
    CountryCode.Ireland to listOf(
        HillClassification.Arderin,
        HillClassification.Binnion,
        HillClassification.Dillon,
        HillClassification.Hewitt,
        HillClassification.Marilyn,
        HillClassification.OtherHills
    ),
    CountryCode.Wales to listOf(
        HillClassification.Hewitt,
        HillClassification.Marilyn,
        HillClassification.Nuttall,
        HillClassification.OtherHills
    ),
    CountryCode.IsleOfMan to listOf(
        HillClassification.Marilyn,
        HillClassification.OtherHills
    )
)