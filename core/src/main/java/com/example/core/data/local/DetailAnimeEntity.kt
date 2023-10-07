package com.example.core.data.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "anime")
data class DetailAnimeEntity(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo
    var id: String,

    @ColumnInfo
    var title: String,

    @ColumnInfo
    var image: String,

    @ColumnInfo
    var genres: String,

    @ColumnInfo
    var totalEpisode: Int,

    @ColumnInfo
    var releaseDate: String,

    @ColumnInfo
    var description: String,

    @ColumnInfo
    var type: String,

    @ColumnInfo
    var status: String,

    @ColumnInfo
    var otherName: String,

    @ColumnInfo(name = "is_favorite")
    var isFavorite: Boolean = false
)