package com.faouzibidi.albums.interactor

import android.content.Context
import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import com.faouzibidi.albums.helpers.NetworkHelper
import com.faouzibidi.albums.model.Album
import com.faouzibidi.albums.repository.local.AlbumLocalRepositoryFake
import com.faouzibidi.albums.repository.remote.AlbumRemoteRepositoryFake
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
 * then it will store them in the local repository
 *
 * The viewmodel will observe data changes and show them once
 * they are inserted in db
 *
 * @author faouzi BIDI
 */
class AlbumInteractorFake(val remoteRepository: AlbumRemoteRepositoryFake, val localRepository: AlbumLocalRepositoryFake, val context: Context) {

    private val CHUNCKED_SEQ_SIZE = 100
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
            var album = localRepository.getFirstElement()
            // if we have no data we should call rest api
            if (album == null){
                if(NetworkHelper.isConnected(context)){
                    val albumsSeq = remoteRepository.getAlbums()
                    // save data in db
                    saveData(albumsSeq)
                }
            }
            // in all cases we will schedule a loading work
            // to get db updated
            // TODO("schedule a work for workmanager")
        }

    }

    /**
     * for optimizing saving process and in order to get
     * data available as soon for ui we try to store data in
     * small subsequences
     */
    private suspend fun saveData(seq: Sequence<Album>){
        val albumsChunkedList = seq.chunked(CHUNCKED_SEQ_SIZE)
        /*
            split the Sequence inot small lists
            for each sublist we store elemets in db
            and we populate elements to the modelview
         */
        albumsChunkedList.forEach {
            // store albums in local db
            localRepository.storeAlbums(it)
        }
    }

    /**
     * return a LiveData of PagedList so the viewmodel can observe
     * data changes
     */
    fun  getPagedList() : LiveData<PagedList<Album>>{
        return localRepository.getAlbumsPagedList()
    }

}