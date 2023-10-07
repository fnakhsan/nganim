package com.example.core.domain

import com.example.core.data.Resource
import com.example.core.domain.model.AnimeModel
import com.example.core.domain.model.DetailAnimeModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AnimeInteractor @Inject constructor(private val animeRepository: IAnimeRepository) :
    AnimeUseCase {
    override fun searchAnime(query: String, page: Int?): Flow<Resource<List<AnimeModel>>> {
        return animeRepository.searchAnime(query, page)
    }

    override fun getDetailAnime(id: String): Flow<Resource<DetailAnimeModel?>> {
        return animeRepository.getDetailAnime(id)
    }

    override fun getFavListAnime(): Flow<List<AnimeModel>> {
        return animeRepository.getFavListAnime()
    }

    override fun setFavAnime(detailAnimeModel: DetailAnimeModel) {
        return animeRepository.setFavAnime(detailAnimeModel)
    }

    override fun isFavAnime(id: String): Flow<Boolean> {
        return animeRepository.isFavAnime(id)
    }
}