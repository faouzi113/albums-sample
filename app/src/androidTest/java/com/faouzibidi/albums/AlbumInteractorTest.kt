package com.faouzibidi.albums


import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.faouzibidi.albums.interactor.AlbumInteractor
import com.faouzibidi.albums.repository.local.AlbumLocalRepository
import com.faouzibidi.albums.repository.local.AlbumRoomDatabse
import com.faouzibidi.albums.repository.remote.AlbumRemoteRepository
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Before

/**
 * this class is used for testing interactor operations
 *
 */
@RunWith(AndroidJUnit4::class)
class AlbumInteractorTest {

    lateinit var interctor : AlbumInteractor

    @Before
    fun init(){
        // craete InMemory db for test purpose
        val db = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            AlbumRoomDatabse::class.java)
            .build()
        // create local and remote repositories
        val localRepository = AlbumLocalRepository(db.AlbumDao())
        val remoteRepository = AlbumRemoteRepository()
        // crate the interactor instance
        interctor = AlbumInteractor(remoteRepository, localRepository, ApplicationProvider.getApplicationContext())
    }
    /**
     * test the insert method if it works
     */
    @Test
    fun getAlbums() = runBlocking {
        interctor.loadAlbums()
        val albums = interctor.albumsLocalList
        assertNotNull(albums)
        // assert that we have at least one element
        assertNotEquals(albums.size, 0)
    }

}