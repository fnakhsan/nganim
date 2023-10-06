package com.example.core.domain.model

data class DetailAnimeModel(
    var animeEntity: AnimeModel,
    var genres: List<GenreModel>,
    var totalEpisode: Int,
    var releaseDate: String,
    var description: String,
    var type: String,
    var status: String,
    var otherName: String,
)
