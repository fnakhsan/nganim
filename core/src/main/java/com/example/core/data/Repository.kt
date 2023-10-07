package com.example.core.data

import com.example.core.data.local.LocalDataSource
import com.example.core.data.remote.RemoteDataSource
import com.example.core.data.remote.response.DetailAnimeResponse
import com.example.core.domain.IAnimeRepository
import com.example.core.domain.model.AnimeModel
import com.example.core.domain.model.DetailAnimeModel
import com.example.core.utils.AppExecutors
import com.example.core.utils.DataMapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.transform
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Repository @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val appExecutors: AppExecutors
) : IAnimeRepository {

    override fun searchAnime(query: String, page: Int?): Flow<Resource<List<AnimeModel>>> =
        remoteDataSource.searchAnime(query, page)


    override fun getDetailAnime(id: String): Flow<Resource<DetailAnimeModel?>> =
        object : NetworkBoundResource<DetailAnimeModel?, DetailAnimeResponse>() {
            override fun loadFromDB(): Flow<DetailAnimeModel?> {
                return localDataSource.getFavoriteAnime(id).map {
                    DataMapper.mapEntitiesToDomain(it)
                }
            }

            override fun shouldFetch(data: DetailAnimeModel?): Boolean {
                return data == null
            }

            override suspend fun createCall(): Flow<Resource<DetailAnimeResponse>> {
                return remoteDataSource.getDetailAnime(id)
            }

            override suspend fun saveCallResult(data: DetailAnimeResponse) {
                val anime = DataMapper.mapResponsesToEntities(data)
                if (anime != null) {
                    localDataSource.upsertFavoriteAnime(anime)
                }
            }
        }.asFlow()

    override fun getFavListAnime(): Flow<List<AnimeModel>> {
        return localDataSource.getListFavoriteAnime().transform {
            it.forEach { data ->
                DataMapper.mapEntitiesToDomain(data)
            }
        }
    }

    override fun setFavAnime(detailAnimeModel: DetailAnimeModel): Flow<Boolean> = flow {
        emit(false)
        try {
            localDataSource.upsertFavoriteAnime(DataMapper.mapDomainToEntity(detailAnimeModel))
            emit(true)
        } catch (e: Exception) {
            emit(false)
        }
    }.flowOn(Dispatchers.IO)

    override fun deleteFavAnime(detailAnimeModel: DetailAnimeModel): Flow<Boolean> = flow {
        emit(false)
        try {
            appExecutors.diskIO().execute {
                localDataSource.deleteFavoriteAnime(DataMapper.mapDomainToEntity(detailAnimeModel))
            }
            emit(true)
        } catch (e: Exception) {
            emit(false)
        }
    }.flowOn(Dispatchers.IO)

}