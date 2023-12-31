package com.example.nganim.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.core.domain.AnimeUseCase
import com.example.core.domain.model.DetailAnimeModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(private val animeUseCase: AnimeUseCase) : ViewModel() {
    fun getDetailAnime(id: String) = animeUseCase.getDetailAnime(id).asLiveData(Dispatchers.IO)

    fun setFavAnime(detailAnimeModel: DetailAnimeModel) = animeUseCase.setFavAnime(detailAnimeModel)

    fun isFavAnime(id: String) = animeUseCase.isFavAnime(id).asLiveData(Dispatchers.IO)
}