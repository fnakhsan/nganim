package com.example.core.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.RawQuery
import androidx.sqlite.db.SupportSQLiteQuery
import kotlinx.coroutines.flow.Flow

@Dao
interface AnimeDao {
    @Query("SELECT * FROM anime where anime.is_favorite = 1")
    fun getListFavoriteAnime(): Flow<List<DetailAnimeEntity>>

    @RawQuery(observedEntities = [DetailAnimeEntity::class])
    fun getFavoriteAnime(query: SupportSQLiteQuery): Flow<DetailAnimeEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun upsertFavoriteAnime(animeEntity: DetailAnimeEntity)

    @Query("SELECT EXISTS(SELECT * FROM anime WHERE anime.id = :id AND anime.is_favorite = 1 )")
    fun isFavoriteAnime(id: String): Flow<Boolean>
}