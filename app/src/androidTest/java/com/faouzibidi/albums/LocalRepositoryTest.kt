package com.faouzibidi.albums


import android.app.Application
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.faouzibidi.albums.model.Album
import com.faouzibidi.albums.repository.local.AlbumLocalRepository
import com.faouzibidi.albums.repository.local.AlbumRoomDatabse
import com.faouzibidi.albums.repository.remote.AlbumRemoteRepository
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*

/**
 * this class is used for testing database operations
 *
 * for testing purpose we will use an InMemoryDatabase instead of th real db
 * so we will not make damages on the real db
 */
@RunWith(AndroidJUnit4::class)
class LocalRepositoryTest {
    //val db : AlbumRoomDatabse
    /**
     * in this test we will just insert a set of albums, check if they exists in the db
     * then we will delete them
     *
     * we will use an InMemoryDatabase for this
     */
    init {

    }

    /**
     * test the insert method if it works
     */
    @Test
    fun insert() = runBlocking {

        val db = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            AlbumRoomDatabse::class.java)
            .build()


        // create test data
        val albums = arrayListOf<Album>()
        for (i in 0..10){
            val a = Album(i,i*2,"Album Title "+i, "", "")
            albums.add(a)
        }
        // test repository
        val repository = AlbumLocalRepository(db.AlbumDao())
        //repository.storeAlbums(albums)
        for(a in albums){
            db.AlbumDao().insertAlbum(a)
        }

        // albums stored in database
        val dbAlbums = db.AlbumDao().getAll()
        assertNotNull(dbAlbums)
        assertEquals(dbAlbums!!.size, albums.size)

    }

}