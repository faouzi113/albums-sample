package com.faouzibidi.albums.data.repository.local

import androidx.paging.DataSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.faouzibidi.albums.data.model.Album

/**
 *
 * this interface is the dao object used for room database
 * it defines all crud methodes
 *
 * @author faouzi BIDI
 */
@Dao
interface AlbumDao {

    /**
     * insert one element
     */
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAlbum(album: Album)

    /**
     * insert a list of elements
     */
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAllAlbums(album: List<Album>)

    /**
     * this is not a safe method for the application, we just use it for testing
     * because it clear all data in databse
     */
    @Query("DELETE FROM album_table")
    suspend fun deleteAll()

    /**
     * get a PagedList for Albums in db
     */
    @Query("SELECT * from album_table")
    fun getAlbumsPagedList(): DataSource.Factory<Int, Album>

    /**
     * get a first element
     */
    @Query("SELECT * from album_table LIMIT 1")
    fun getFirstElement(): Album

}