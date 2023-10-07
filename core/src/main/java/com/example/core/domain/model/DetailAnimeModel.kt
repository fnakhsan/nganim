package com.example.core.domain.model

data class DetailAnimeModel(
    var animeEntity: AnimeModel,
    var genres: List<String>,
    var totalEpisode: Int,
    var releaseDate: String,
    var description: String,
    var type: String,
    var status: String,
    var otherName: String,
    var isFav: Boolean? = false,
)
