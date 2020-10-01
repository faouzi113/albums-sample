package com.faouzibidi.albums.repository.local

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import androidx.paging.toLiveData
import com.faouzibidi.albums.model.Album

/**
 * The local Repository who exposes methods to save and retrieve data
 * from the local database
 *
 * @author faouzi BIDI
 */
open class AlbumLocalRepository(private val albumDao : AlbumDao){

    // page size of PagedList
    private val PAGE_SIZE = 20

    /**
     * return LiveData of PagedList of albums
     * where the page size of PagedList is PAGE_SIZE
     */
    fun getAlbumsPagedList(): LiveData<PagedList<Album>> {
        return albumDao.getAlbumsPagedList().toLiveData(pageSize = PAGE_SIZE)
    }

    /**
     * return LiveData of PagedList of albums
     * where the page size of PagedList is PAGE_SIZE
     */
    fun getFirstElement(): Album {
        return albumDao.getFirstElement()
    }

    /**
     * store all elements in the list into
     * local databse
     */
    suspend fun storeAlbums(albums : List<Album>){
        // store albums in db
        albumDao.insertAllAlbums(albums)
    }

}