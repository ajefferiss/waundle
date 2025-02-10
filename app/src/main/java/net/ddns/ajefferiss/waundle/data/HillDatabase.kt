package net.ddns.ajefferiss.waundle.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(
    entities = [Hill::class, HillsMeta::class],
    version = 1
)
@TypeConverters(Converters::class)
abstract class HillDatabase : RoomDatabase() {
    abstract fun hillDAO(): HillDAO
}