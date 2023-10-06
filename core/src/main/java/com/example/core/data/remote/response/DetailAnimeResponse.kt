package com.example.core.data.remote.response

import com.google.gson.annotations.SerializedName

data class DetailAnimeResponse(

	@field:SerializedName("image")
	val image: String? = null,

	@field:SerializedName("totalEpisodes")
	val totalEpisodes: Int? = null,

	@field:SerializedName("releaseDate")
	val releaseDate: String? = null,

	@field:SerializedName("subOrDub")
	val subOrDub: String? = null,

	@field:SerializedName("description")
	val description: String? = null,

	@field:SerializedName("title")
	val title: String? = null,

	@field:SerializedName("type")
	val type: String? = null,

	@field:SerializedName("url")
	val url: String? = null,

	@field:SerializedName("genres")
	val genres: List<String?>? = null,

	@field:SerializedName("otherName")
	val otherName: String? = null,

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("episodes")
	val episodes: List<EpisodesItem?>? = null,

	@field:SerializedName("status")
	val status: String? = null
)

data class EpisodesItem(

	@field:SerializedName("number")
	val number: Int? = null,

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("url")
	val url: String? = null
)
