package com.example.core.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.RawQuery
import androidx.room.Upsert
import androidx.sqlite.db.SupportSQLiteQuery
import kotlinx.coroutines.flow.Flow

@Dao
interface AnimeDao {
    @Query("SELECT * FROM anime")
    fun getListFavoriteAnime(): Flow<List<DetailAnimeEntity>>

    @RawQuery(observedEntities = [DetailAnimeEntity::class])
    fun getFavoriteAnime(query: SupportSQLiteQuery): Flow<DetailAnimeEntity>

    @Upsert
    suspend fun upsertFavoriteAnime(animeEntity: DetailAnimeEntity)

    @Delete
    fun deleteFavoriteAnime(animeEntity: DetailAnimeEntity)
}