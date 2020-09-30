package com.faouzibidi.albums.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.faouzibidi.albums.interactor.AlbumInteractor
import com.faouzibidi.albums.model.Album

/**
 * this is the viewmodel used to send data to
 * the views and notify views of data changes
 */
class AlbumViewModel(application : Application) : AndroidViewModel(application){

    private  var albums = MutableLiveData<List<Album>>()
    // TODO("init interctaor with koin instance")
    private lateinit var interactor : AlbumInteractor

    /**
     * return a LiveData of Albums
     */
    fun getAlbums() : LiveData<List<Album>>{
        return albums
    }

    /**
     * set the value and notify hte observers
     */
    fun setValue(albumsList : List<Album>){
        albums.value = albumsList
    }

    /**
     * used to set value from background Thread
     */
    fun postValue(albumsList : List<Album>){
        albums.postValue(albumsList)
    }



    /**
     * this method call the interactor to fetch data from repositories
     * then it notify the LiveData
     *
     * if there is stored data in local database we send them first
     * else we fetch data from remotre repository then we send them
     */
    suspend fun loadAlbums(){
        interactor.loadAlbums()
    }

}