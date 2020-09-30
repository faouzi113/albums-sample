package com.faouzibidi.albums.interactor

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.faouzibidi.albums.helpers.NetworkHelper
import com.faouzibidi.albums.model.Album
import com.faouzibidi.albums.repository.local.AlbumLocalRepository
import com.faouzibidi.albums.repository.remote.AlbumRemoteRepository
import com.faouzibidi.albums.ui.AlbumViewModel

/**
 *
 * THIS CLASS IS USED ONLY FOR TEST PURPOSE
 * TO TEST the INTERACTOR SEPARATELY
 *
 *
 *
 *
 * @author faouzi BIDI
 */
class AlbumInteractor(val remoteRepository: AlbumRemoteRepository, val localRepository: AlbumLocalRepository, val context: Context /*, val albumViewModel: AlbumViewModel */) {

    /**
     * this list is used for storing inmemory data
     * to avoid calling VieModel operations
     * and test it separately
     */
    lateinit var albumsLocalList: List<Album>
    /**
     * this method return a list of Albums
     *
     * if there is stored data in local database we send them first
     * else we fetch data from remotre repository then we send them
     */
    suspend fun loadAlbums(){
        // check if we have data in db
        var albums = localRepository.getAlbums()
        if (albums != null){
            // we send first data stored in db
            // for observers
            //albumViewModel.postValue(albums)
            albumsLocalList = albums
        }else{
            if(NetworkHelper.isConnected(context)){
                albums = remoteRepository.getAlbums()
                // set Livedata value and dispatch notification event
                // for observers
                //albumViewModel.postValue(albums)
                albumsLocalList = albums
                // store albums in local db
                localRepository.storeAlbums(albums)
            }else{
                TODO("schedule a work for workmanager")
            }

        }
    }

}