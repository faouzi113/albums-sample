package com.faouzibidi.albums.data.repository.remote

import com.faouzibidi.albums.data.model.Album
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory

/**
 * This repository will fetch Albums data from leboincoin webservice
 *
 */
class AlbumRemoteRepository{

    /**
     * call the rest api and get a Sequence of Albums
     */
    suspend fun getAlbums(): Sequence<Album> {
        return AlbumApi.albumParser.getAlbums()
    }
}

/*
 * building a moshi adapter for kotlin compatibility
 */
private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()
/**
 * public object
 * which will be used outside to get Albums from a network request
 */
object AlbumApi {
    val albumParser : AlbumSequenceParser by lazy {
        AlbumSequenceParser(moshi) }
}