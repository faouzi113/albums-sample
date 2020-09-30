package com.faouzibidi.albums.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.faouzibidi.albums.interactor.AlbumInteractor
import com.faouzibidi.albums.model.Album
import com.faouzibidi.albums.repository.local.AlbumLocalRepository
import com.faouzibidi.albums.repository.local.AlbumRoomDatabse
import com.faouzibidi.albums.repository.remote.AlbumRemoteRepository
import kotlinx.coroutines.launch

/**
 * this is the viewmodel used to send data to
 * the views and notify views of data changes
 *
 * @see AndroidViewModel
 *
 * @author faouzi BIDI
 *
 */
class AlbumViewModel(application : Application) : AndroidViewModel(application){

    private  var albums = MutableLiveData<List<Album>>()
    // TODO("init interctaor with koin instance")
    private val interactor : AlbumInteractor

    init {
        val remoteRepository = AlbumRemoteRepository()
        val localRepository = AlbumLocalRepository(AlbumRoomDatabse.getDatabase(application).AlbumDao())
        interactor = AlbumInteractor(remoteRepository, localRepository, application, this)
    }

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
    fun loadAlbums(){
        viewModelScope.launch {
            interactor.loadAlbums()
        }

    }

}