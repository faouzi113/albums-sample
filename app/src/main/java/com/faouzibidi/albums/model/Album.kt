package com.faouzibidi.albums.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json

/**
 * the model class for the Album entity
 *
 * @author faouzi BIDI
 */
@Entity(tableName = "album_table")
data class Album(
    @PrimaryKey val id : Int,
    val albumId : Int,
    val title : String,
    val url : String,
    // we have changed the name of this attribute for the simplicity of use
    // and the understandability
    @Json(name = "thumbnailUrl")
    // this is for db column name
    @ColumnInfo(name = "imageUrl")
    val imageUrl : String
)