package com.faouzibidi.albums.repository.remote

import android.content.Context
import com.faouzibidi.albums.model.Album

/**
 * This repository is used for testing purpose
 * It uses a mock file of data instead of calling real api
 *
 */
class AlbumRemoteRepositoryFake(context: Context){
    private val albumParser: AlbumSequenceParser

    init {
        albumParser = AlbumSequenceParser(context)
    }
    /**
     * call the rest api and get a Sequence of Albums
     */
    suspend fun getAlbums(): Sequence<Album> {
        return albumParser.getAlbums()
    }
}
