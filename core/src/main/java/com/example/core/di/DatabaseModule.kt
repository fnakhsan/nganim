package com.example.core.di

import android.content.Context
import androidx.annotation.Keep
import androidx.room.Room
import com.example.core.data.local.AnimeDao
import com.example.core.data.local.AnimeDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import net.sqlcipher.database.SQLiteDatabase
import net.sqlcipher.database.SupportFactory
import javax.inject.Singleton

@Keep
@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {
    private val passphrase: ByteArray = SQLiteDatabase.getBytes("example".toCharArray())
    private val factory = SupportFactory(passphrase)

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): AnimeDatabase =
        Room.databaseBuilder(
            context,
            AnimeDatabase::class.java,
            "Anime.db"
        ).fallbackToDestructiveMigration().openHelperFactory(factory).build()


    @Provides
    fun provideAnimeDao(database: AnimeDatabase): AnimeDao = database.animeDao()
}