package com.faouzibidi.albums.repository.local

import com.faouzibidi.albums.model.Album
import com.faouzibidi.albums.repository.AlbumRepository

/**
 * this repository will fetch and store data from and into AlbumsDatabase
 */
class AlbumLocalRepository : AlbumRepository(){
    override suspend fun getAlbums(): List<Album> {
        TODO("Not yet implemented")
        return listOf()
    }

    fun storeAlbums(albums : List<Album>){
        // store albums in db
    }

}