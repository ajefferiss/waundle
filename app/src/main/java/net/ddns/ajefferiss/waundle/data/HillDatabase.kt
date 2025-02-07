package net.ddns.ajefferiss.waundle.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(
    entities = [Hill::class, HillsMeta::class, HillClassifications::class, HillClassificationLookup::class],
    version = 2,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class HillDatabase : RoomDatabase() {
    abstract fun hillDAO(): HillDAO
}