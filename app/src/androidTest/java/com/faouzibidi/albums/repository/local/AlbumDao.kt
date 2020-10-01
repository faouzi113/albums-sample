package com.faouzibidi.albums.repository.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.faouzibidi.albums.model.Album

/**
 * this class is used for testing purpose
 *
 * @author faouzi BIDI
 */
@Dao
interface AlbumDao {

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
    fun getAll(): List<Album>

    /**
     * get a first element
     */
    @Query("SELECT * from album_table LIMIT 1")
    fun getFirstElement(): Album

}