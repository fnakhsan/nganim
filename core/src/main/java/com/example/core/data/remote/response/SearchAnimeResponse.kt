package com.example.core.data.remote.response

import com.google.gson.annotations.SerializedName

data class SearchAnimeResponse(

	@field:SerializedName("hasNextPage")
	val hasNextPage: Boolean? = null,

	@field:SerializedName("currentPage")
	val currentPage: String? = null,

	@field:SerializedName("results")
	val results: List<ResultsItem?>? = null
)

data class ResultsItem(

	@field:SerializedName("image")
	val image: String? = null,

	@field:SerializedName("releaseDate")
	val releaseDate: String? = null,

	@field:SerializedName("subOrDub")
	val subOrDub: String? = null,

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("title")
	val title: String? = null,

	@field:SerializedName("url")
	val url: String? = null
)
