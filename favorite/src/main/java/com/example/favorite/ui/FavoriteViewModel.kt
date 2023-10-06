package com.example.favorite.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.core.domain.AnimeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(private val animeUseCase: AnimeUseCase) : ViewModel() {
    fun getFavListAnime() = animeUseCase.getFavListAnime().asLiveData(Dispatchers.IO)
}