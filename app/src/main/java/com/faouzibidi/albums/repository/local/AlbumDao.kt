package com.faouzibidi.albums.repository.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.faouzibidi.albums.model.Album

/**
 *
 * this interface is the dao object used for room database
 * it defines all crud methodes
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

    @Query("SELECT * from album_table ORDER BY id ASC")
    fun getAllAlbums(): LiveData<List<Album>>

    @Query("SELECT * from album_table ORDER BY id ASC")
    fun getAll(): List<Album>

    /**
     * get a range of albums starting from the offset index to offset+limit
     * because limit IT IS NOT an index but the count of rows to retrieve
     */
    @Query("SELECT * from album_table ORDER BY id ASC LIMIT :limit OFFSET :offset")
    fun getAlbums(offset:Int, limit:Int): LiveData<List<Album>>

    /**
     * get an album by its id
     */
    @Query("SELECT * from album_table WHERE id =:id ORDER BY id ASC")
    fun getAlbumById(id:Int): LiveData<List<Album>>


}