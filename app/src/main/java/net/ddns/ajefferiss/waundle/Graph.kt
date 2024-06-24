package net.ddns.ajefferiss.waundle

import android.content.Context
import androidx.room.Room
import net.ddns.ajefferiss.waundle.data.HillDatabase
import net.ddns.ajefferiss.waundle.data.HillRepository

object Graph {
    lateinit var database: HillDatabase

    val hillRepository by lazy {
        HillRepository(hillDAO = database.hillDAO())
    }

    fun provide(context: Context) {
        database = Room.databaseBuilder(context, HillDatabase::class.java, "waundle.db")
            .createFromAsset("database/waundle.db")
            .build()
    }
}