package com.example.nganim.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.core.domain.AnimeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val animeUseCase: AnimeUseCase) : ViewModel() {

    fun searchAnime(
        query: String,
        page: Int?
    ) = animeUseCase.searchAnime(query, page).asLiveData(Dispatchers.IO)
}