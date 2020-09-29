package com.faouzibidi.albums.model

import com.squareup.moshi.Json

/**
 * the model class for the Album entity
 *
 * @author faouzi BIDI
 */
data class Album(
    val id : Int,
    val albumId : Int,
    val title : String,
    val url : String,
    // we have changed the name of this attribute for the simplicity of use
    // and the understandability
    @Json(name = "thumbnailUrl") val imageUrl : String
)