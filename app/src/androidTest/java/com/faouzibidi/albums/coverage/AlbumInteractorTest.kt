package com.faouzibidi.albums.coverage


import android.content.Context
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.faouzibidi.albums.mock.interactor.AlbumInteractorFake
import com.faouzibidi.albums.mock.repository.local.AlbumLocalRepositoryFake
import com.faouzibidi.albums.mock.repository.remote.AlbumRemoteRepositoryFake
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

    lateinit var interctor : AlbumInteractorFake

    @Before
    fun initialize(){
        val ctx : Context = ApplicationProvider.getApplicationContext()
        // create local and remote repositories
        val localRepository = AlbumLocalRepositoryFake.getInstance(ctx)
        val remoteRepository = AlbumRemoteRepositoryFake(ctx)
        // crate the interactor instance
        interctor = AlbumInteractorFake(remoteRepository, localRepository, ApplicationProvider.getApplicationContext())
    }
    /**
     * test if loadAlbums method works
     */
    @Test
    fun getAlbums() = runBlocking {
        // call the method
        interctor.loadAlbums()
        // wait for loading data
        Thread.sleep(10000)
        // check if we have data in db
        val album = interctor.localRepository.getFirstElement()
        assertNotNull(album)
    }

}