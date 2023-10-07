package com.example.core.data.remote.network

import com.example.core.data.remote.response.DetailAnimeResponse
import com.example.core.data.remote.response.SearchAnimeResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("{query}")
    suspend fun searchAnime(
        @Path("query") query: String,
        @Query("page") page: Int? = null,
    ): SearchAnimeResponse

    @GET("info/{id}")
    suspend fun getDetailAnime(
        @Path("id") id: String
    ): DetailAnimeResponse
}