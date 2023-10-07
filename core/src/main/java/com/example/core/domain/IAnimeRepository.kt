package com.example.core.domain

import com.example.core.data.Resource
import com.example.core.domain.model.AnimeModel
import com.example.core.domain.model.DetailAnimeModel
import kotlinx.coroutines.flow.Flow

interface IAnimeRepository {
    fun searchAnime(query: String, page: Int?): Flow<Resource<List<AnimeModel>>>

    fun getDetailAnime(id: String): Flow<Resource<DetailAnimeModel?>>

    fun getFavListAnime(): Flow<List<AnimeModel>>

    fun setFavAnime(detailAnimeModel: DetailAnimeModel): Flow<Boolean>

    fun deleteFavAnime(detailAnimeModel: DetailAnimeModel): Flow<Boolean>
}