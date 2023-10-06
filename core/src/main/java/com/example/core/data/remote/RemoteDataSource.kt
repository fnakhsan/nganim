package com.example.core.data.remote

import android.util.Log
import com.example.core.data.Resource
import com.example.core.data.remote.network.ApiService
import com.example.core.data.remote.response.DetailAnimeResponse
import com.example.core.domain.model.AnimeModel
import com.example.core.utils.DataMapper
import com.example.core.utils.UiText
import com.example.nganim.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RemoteDataSource @Inject constructor(private val apiService: ApiService) {
    fun searchAnime(query: String, page: Int?): Flow<Resource<List<AnimeModel>>> {
        //get data from remote api
        return flow {
            emit(Resource.Loading)
            try {
                val response = apiService.searchAnime(query, page)
                if (!response.results.isNullOrEmpty()){
                    emit(Resource.Success(DataMapper.mapResponsesToDomain(response)))
                } else {
                    emit(Resource.Empty)
                }
            } catch (e : Exception){
                Log.e("RemoteDataSource", e.toString())
                if (e.message.isNullOrBlank()) {
                    emit(Resource.Error(UiText.StringResource(R.string.unknown_error)))
                } else {
                    emit(Resource.Error(UiText.DynamicString(e.message.toString())))
                }
            }
        }.flowOn(Dispatchers.IO)
    }

    fun getDetailAnime(id: String): Flow<Resource<DetailAnimeResponse>> {
        return flow {
            emit(Resource.Loading)
            try {
                val response = apiService.getDetailAnime(id)
                if (!response.id.isNullOrBlank()){
                    emit(Resource.Success(response))
                } else {
                    emit(Resource.Empty)
                }
            } catch (e : Exception){
                Log.e("RemoteDataSource", e.toString())
                if (e.message.isNullOrBlank()) {
                    emit(Resource.Error(UiText.StringResource(R.string.unknown_error)))
                } else {
                    emit(Resource.Error(UiText.DynamicString(e.message.toString())))
                }
            }
        }.flowOn(Dispatchers.IO)
    }
}

