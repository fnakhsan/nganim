package com.example.core.di

import android.content.Context
import androidx.room.Room
import com.example.core.data.local.AnimeDao
import com.example.core.data.local.AnimeDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): AnimeDatabase = Room.databaseBuilder(
        context,
        AnimeDatabase::class.java,
        "Anime.db"
    ).fallbackToDestructiveMigration().build()

    @Provides
    fun provideAnimeDao(database: AnimeDatabase): AnimeDao = database.animeDao()
}