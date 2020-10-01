package com.faouzibidi.albums.interactor

import android.content.Context
import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.faouzibidi.albums.helpers.NetworkHelper
import com.faouzibidi.albums.model.Album
import com.faouzibidi.albums.repository.local.AlbumLocalRepository
import com.faouzibidi.albums.repository.remote.AlbumRemoteRepository
import com.faouzibidi.albums.ui.AlbumViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * We use in this Application MVVMi architecture, and this
 * class is the interactor who deal with local and remote repositories
 *
 * We use this interctor to separate between application business logic
 * and data layer
 *
 * This interctor will fetch first data from the remote repository
 * then it will store them in the local repository and it send them finally
 * to the viewModel
 *
 * When the viewmodel call the AlbumInteractor@getAlbums method
 * we sent data stored in the local database, then we send
 * a fetch request to the API to get newest data.
 * So that, the only source of truth will be the local data
 *
 *
 *
 *
 * @author faouzi BIDI
 */
class AlbumInteractor(val remoteRepository: AlbumRemoteRepository, val localRepository: AlbumLocalRepository, val context: Context, val albumViewModel: AlbumViewModel) {

    /**
     * this method return a list of Albums
     *
     * if there is stored data in local database we send them first
     * else we fetch data from remotre repository then we send them
     */
    @WorkerThread
    suspend fun loadAlbums(){
        return withContext(Dispatchers.IO) {
            // check if we have data in db
            var albums = localRepository.getAlbums()
            if (albums != null){
                // we send first data stored in db
                // for observers
                albumViewModel.postValue(albums)
            }else{
                if(NetworkHelper.isConnected(context)){
                    val albumsSeq = remoteRepository.getAlbums()
                    // fetch data
                    fetchData(albumsSeq)
                }else{
                    TODO("schedule a work for workmanager")
                }

            }
        }

    }

    /**
     * this private method contains all the logic of
     * fetching data from Sequence Object
     */
    private suspend fun fetchData(seq: Sequence<Album>){
        val albumsChunkedList = seq.chunked(200)
        /*
            split the Sequence inot small lists of 200 elements
            for each sublist we store elemets in db
            and we populate elements to the modelview
         */
        albumsChunkedList.forEach {
            // set Livedata value and dispatch notification event
            // for observers
            albumViewModel.addElements(it)
            // store albums in local db
            localRepository.storeAlbums(it)
        }
    }

}