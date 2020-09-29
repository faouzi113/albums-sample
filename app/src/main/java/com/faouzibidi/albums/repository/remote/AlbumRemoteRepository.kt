package com.faouzibidi.albums.repository.remote

import com.faouzibidi.albums.model.Album
import com.faouzibidi.albums.repository.AlbumRepository
import kotlinx.coroutines.flow.callbackFlow

/**
 * This repository will fetch Albums data from leboincoin webservice
 *
 */
class AlbumRemoteRepository : AlbumRepository(){

    override suspend fun getAlbums(): List<Album> {
        return AlbumApi.retrofitService.getAlbums()
    }

}