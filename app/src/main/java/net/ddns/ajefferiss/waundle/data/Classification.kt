package net.ddns.ajefferiss.waundle.data

class Classification {
    private val classificationMap: HashMap<String, String> = hashMapOf(
        "Ma" to "Marilyn",
        "Hu" to "Hump",
        "Sim" to "Simm",
        "5" to "Dodd",
        "M" to "Munro",
        "MT" to "Munro Top",
        "F" to "Furth",
        "C" to "Corbett",
        "G" to "Graham",
        "D" to "Donald",
        "DT" to "Donald Top",
        "Hew" to "Hewitt",
        "N" to "Nuttall",
        "Dew" to "Dewey",
        "DDew" to "Donald Dewey",
        "HF" to "Highland Five",
        "4" to "400-499m Tump",
        "3" to "300-399m Tump (GB)",
        "2" to "200-299m Tump (GB)",
        "1" to "100-199m Tump (GB)",
        "0" to "0-99m Tump (GB)",
        "W" to "Wainwright",
        "WO" to "Wainwright Outlying Fell",
        "B" to "Birkett",
        "Sy" to "Synge",
        "Fel" to "Fellranger",
        "CoH" to "County Top – Historic (pre-1974)",
        "CoA" to "County Top – Administrative (1974 to mid-1990s)",
        "CoU" to "County Top – Current County or Unitary Authority",
        "CoL" to "County Top – Current London Borough",
        "SIB" to "Significant Island of Britain",
        "Dil" to "Dillon",
        "A" to "Arderin",
        "VL" to "Vandeleur-Lynam",
        "O" to "Other list",
        "Un" to "unclassified"
    )

    fun toClassifications(codes: String): List<String> {
        val classifications: MutableList<String> = mutableListOf()

        val values = codes.split(",").map { it -> it.trim() }
        values.forEach {
            if (it.isNotEmpty()) {
                classifications.add(classificationMap.getOrDefault(it, it))
            }
        }

        return classifications
    }
}
