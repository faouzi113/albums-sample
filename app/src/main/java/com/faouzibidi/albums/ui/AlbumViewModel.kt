package com.faouzibidi.albums.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.faouzibidi.albums.model.Album

/**
 * this is the viewmodel used to send data to
 * the views and notify views of data changes
 */
class AlbumViewModel : ViewModel(){

    var albums = MutableLiveData<List<Album>>()

    /**
     * return a LiveData of Albums
     */
    fun getAlbums() : LiveData<List<Album>>{
        return albums
    }

    fun loadAlbums(){
        // load data from repository
    }

}