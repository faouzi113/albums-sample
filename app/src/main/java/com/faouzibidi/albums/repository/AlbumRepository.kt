package com.faouzibidi.albums.repository

import com.faouzibidi.albums.model.Album

abstract class  AlbumRepository(){

    abstract suspend fun getAlbums(): List<Album>
}