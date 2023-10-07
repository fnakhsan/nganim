package com.example.core.utils

import android.util.Log
import com.example.core.data.local.DetailAnimeEntity
import com.example.core.data.remote.response.DetailAnimeResponse
import com.example.core.data.remote.response.SearchAnimeResponse
import com.example.core.domain.model.AnimeModel
import com.example.core.domain.model.DetailAnimeModel

object DataMapper {
    fun mapResponsesToEntities(input: DetailAnimeResponse): DetailAnimeEntity? =
        input.let {
            if (!it.id.isNullOrBlank()) {
                DetailAnimeEntity(
                    id = it.id,
                    title = it.title.toString(),
                    image = it.image ?: "https://demofree.sirv.com/nope-not-here.jpg?w=100",
                    genres = it.genres?.joinToString(",") ?: "",
                    totalEpisode = it.totalEpisodes ?: 0,
                    releaseDate = it.releaseDate.toString(),
                    description = it.description.toString(),
                    type = it.type.toString(),
                    status = it.status.toString(),
                    otherName = it.otherName.toString()
                )
            } else null
        }

    fun mapResponsesToDomain(input: SearchAnimeResponse): List<AnimeModel> {
        val listAnime = ArrayList<AnimeModel>()
        input.results?.map {
            it?.let { anime ->
                anime.id?.let { id ->
                    listAnime.add(
                        AnimeModel(
                            id = id,
                            title = anime.title.toString(),
                            image = anime.image.toString()
                        )
                    )
                }
            }
        }
        return listAnime
    }


    fun mapEntitiesToDetailDomain(input: DetailAnimeEntity?): DetailAnimeModel? {
        if (input != null) {
            Log.d("data", "satu")
            return input.let {
                DetailAnimeModel(
                    animeEntity = AnimeModel(
                        id = it.id,
                        title = it.title,
                        image = it.image
                    ),
                    genres = it.genres.split(',').toList(),
                    totalEpisode = it.totalEpisode,
                    releaseDate = it.releaseDate,
                    description = it.description,
                    type = it.type,
                    status = it.status,
                    otherName = it.otherName
                )
            }
        } else {
            return null
        }
    }

    fun mapEntitiesToDomain(input: DetailAnimeEntity): AnimeModel = AnimeModel(
        id = input.id,
        title = input.title,
        image = input.image
    )


    fun mapDomainToEntity(input: DetailAnimeModel): DetailAnimeEntity {
        return DetailAnimeEntity(
            id = input.animeEntity.id,
            title = input.animeEntity.title,
            image = input.animeEntity.image,
            genres = input.genres.joinToString(","),
            totalEpisode = input.totalEpisode,
            releaseDate = input.releaseDate,
            description = input.description,
            type = input.type,
            status = input.status,
            otherName = input.otherName,
            isFavorite = input.isFav ?: false
        )
    }
}