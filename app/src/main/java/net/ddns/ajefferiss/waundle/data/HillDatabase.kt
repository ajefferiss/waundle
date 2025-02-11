package net.ddns.ajefferiss.waundle.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

@Database(
    entities = [Hill::class, HillsMeta::class],
    version = 2
)
@TypeConverters(Converters::class)
abstract class HillDatabase : RoomDatabase() {
    abstract fun hillDAO(): HillDAO
}

val MIGRATION_1_2 = object : Migration(1, 2) {
    override fun migrate(db: SupportSQLiteDatabase) {
        // Nothing to actually do...
    }
}