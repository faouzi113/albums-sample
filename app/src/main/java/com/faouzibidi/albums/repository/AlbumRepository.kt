package com.faouzibidi.albums.repository

import androidx.lifecycle.LiveData
import com.faouzibidi.albums.model.Album

abstract class  AlbumRepository(){

    abstract suspend fun getAlbums(): List<Album>
}