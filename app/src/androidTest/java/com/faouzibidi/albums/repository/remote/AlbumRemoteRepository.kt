package com.faouzibidi.albums.repository.remote

import com.faouzibidi.albums.model.Album

/**
 * This repository will fetch Albums data from leboincoin webservice
 *
 */
class AlbumRemoteRepository{

    suspend fun getAlbumsSequence(): Sequence<Album> {
        return AlbumApi.albumParser.getAlbums()
    }

    suspend fun getAlbums(): List<Album> {
        return AlbumApi.retrofitService.getAlbums()
    }

}