package net.ddns.ajefferiss.waundle.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
abstract class HillDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun addHill(hillEntity: Hill)

    @Query("SELECT * FROM `hills_table`")
    abstract fun getAllHills(): Flow<List<Hill>>

    @Query("SELECT * FROM `hills_table` WHERE climbed IS NOT NULL ORDER BY climbed DESC")
    abstract fun getWalkedHills(): Flow<List<Hill>>

    @Update
    abstract suspend fun updateHill(hillEntity: Hill)

    @Query("SELECT * FROM `hills_table` WHERE id = :id")
    abstract fun getHillById(id: Long): Flow<Hill>

    @Query("SELECT * FROM `hills_meta` WHERE id = 1")
    abstract fun getHillsMeta(): Flow<HillsMeta>

    @Update
    abstract suspend fun updateHillMeta(metaEntry: HillsMeta)
}