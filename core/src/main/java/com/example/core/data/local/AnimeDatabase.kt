package com.example.core.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [DetailAnimeEntity::class], version = 1, exportSchema = false)
abstract class AnimeDatabase : RoomDatabase() {

    abstract fun animeDao(): AnimeDao

}