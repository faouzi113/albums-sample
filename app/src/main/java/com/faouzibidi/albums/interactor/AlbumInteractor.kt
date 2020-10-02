package com.faouzibidi.albums.interactor

import android.content.Context
import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import com.faouzibidi.albums.util.NetworkHelper
import com.faouzibidi.albums.data.model.Album
import com.faouzibidi.albums.data.repository.local.AlbumLocalRepository
import com.faouzibidi.albums.data.repository.remote.AlbumRemoteRepository
import com.faouzibidi.albums.util.RefreshWorkerHelper
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
class AlbumInteractor(val remoteRepository: AlbumRemoteRepository, val localRepository: AlbumLocalRepository, val context: Context) {

    private val CHUNCKED_SEQ_SIZE = 500
    /**
     * this method return a list of Albums
     *
     * if there is stored data in local database we send them first
     * else we fetch data from remotre repository then we send them
     */
    @WorkerThread
    suspend fun loadAlbums(){
        return withContext(Dispatchers.Default) {
            // check if we have data in db
            var album = localRepository.getFirstElement()
            // if we have no data we should call rest api
            if (album == null){
                if (!refershData()){
                    // if there is something wrong with refresh process
                    // we will schedule 
                    // a refresh task until the device get connected
                    RefreshWorkerHelper.launchRefreshRequest(context)
                }
            }else{
                // if we have local data to show we will
                // schedule a refresh job for next time
                RefreshWorkerHelper.launchRefreshRequest(context, RefreshWorkerHelper.REPEAT_INTERVAL)
            }
        }

    }

    /**
     * send a rest api request and store data into base
     * 
     * @return true if the loading of data has been successfully achieved
     *          false if there is somthing wrong (could be network connection or saving process)
     */
    @WorkerThread
    suspend fun refershData() : Boolean{
        if(NetworkHelper.isConnected(context)){
            // call rest api
            val albumsSeq = remoteRepository.getAlbums()
            // save data in db
            saveData(albumsSeq)
            // we already fetched new data so no need to call refresh request
            return true
        }else{
            return false
        }
    }

    /**
     * for optimizing saving process and in order to get
     * data available as soon for ui we try to store data in
     * small subsequences
     */
    private suspend fun saveData(seq: Sequence<Album>){
        val albumsChunkedList = seq.chunked(CHUNCKED_SEQ_SIZE)
        // then continue to insert all sublists
        /*
            split the Sequence inot small lists
            for each sublist we store elemets in db
            and we populate elements to the modelview
         */
        withContext(Dispatchers.IO) {
            albumsChunkedList.forEach {
                // store albums in local db
                localRepository.storeAlbums(it)
            }
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