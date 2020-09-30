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
    @PrimaryKey var id : Int,
    var albumId : Int,
    var title : String,
    var url : String,
    // we have changed the name of this attribute for the simplicity of use
    // and the understandability
    @Json(name = "thumbnailUrl")
    var imageUrl : String
)