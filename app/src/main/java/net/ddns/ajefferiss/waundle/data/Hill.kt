package net.ddns.ajefferiss.waundle.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate

@Entity(tableName = "hills_table")
data class Hill(
    @PrimaryKey(autoGenerate = true) val hillId: Long = 0L,
    @ColumnInfo(name = "number") val number: Long = 0L,
    @ColumnInfo(name = "name") val name: String = "",
    @ColumnInfo(name = "parent_num") val parent: Long? = null,
    @ColumnInfo(name = "section") val section: String = "",
    @ColumnInfo(name = "region") val region: String = "",
    @ColumnInfo(name = "area") val area: String? = null,
    @ColumnInfo(name = "island") val island: String? = null,
    @ColumnInfo(name = "topoSection") val topoSection: String = "",
    @ColumnInfo(name = "county") val county: String = "",
    @ColumnInfo(name = "metres") val metres: Float = 0F,
    @ColumnInfo(name = "feet") val feet: Float = 0F,
    @ColumnInfo(name = "climbed") val climbed: LocalDate? = null,
    @ColumnInfo(name = "country") val country: String = "",
    @ColumnInfo(name = "hillBaggingUrl") val hillBaggingUrl: String = "",
    @ColumnInfo(name = "latitude") val latitude: Float = 0F,
    @ColumnInfo(name = "longitude") val longitude: Float = 0F,
    @ColumnInfo(name = "classifications") val classifications: String? = ""
)

@Entity(tableName = "hills_meta")
data class HillsMeta(
    @PrimaryKey(autoGenerate = true) val id: Long = 0L,
    @ColumnInfo(name = "csv_name") val csvName: String = "",
    @ColumnInfo(name = "csv_hash") val csvHash: String = ""
)