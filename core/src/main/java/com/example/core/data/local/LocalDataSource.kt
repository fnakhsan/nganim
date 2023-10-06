package com.example.core.data.local

import androidx.room.Delete
import androidx.room.Query
import androidx.room.RawQuery
import androidx.room.Upsert
import androidx.sqlite.db.SimpleSQLiteQuery
import androidx.sqlite.db.SupportSQLiteQuery
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocalDataSource @Inject constructor(private val animeDao: AnimeDao) {

    fun getListFavoriteAnime(): Flow<List<DetailAnimeEntity>> = animeDao.getListFavoriteAnime()

    private val simpleSQLiteQuery = "SELECT * FROM anime WHERE type ="
    fun getFavoriteAnime(id: String): Flow<DetailAnimeEntity> = animeDao.getFavoriteAnime(SimpleSQLiteQuery("$simpleSQLiteQuery '$id' LIMIT 1"))

    suspend fun upsertFavoriteAnime(animeEntity: DetailAnimeEntity) = animeDao.upsertFavoriteAnime(animeEntity)

    fun deleteFavoriteAnime(animeEntity: DetailAnimeEntity) = animeDao.deleteFavoriteAnime(animeEntity)
}