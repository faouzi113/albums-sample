package com.faouzibidi.albums.repository.local

import com.faouzibidi.albums.model.Album
import com.faouzibidi.albums.repository.AlbumRepository

/**
 * this repository will fetch and store data from and into AlbumsDatabase
 */
class AlbumLocalRepository(private val albumDao : AlbumDao){

    fun getAlbums(): List<Album>? {
        return albumDao.getAllAlbums().value
    }

    suspend fun storeAlbums(albums : List<Album>){
        // store albums in db
        for (album in albums){
            albumDao.insertAlbum(album)
        }
    }

    suspend fun deleteAlbums(){
        albumDao.deleteAll()
    }

}