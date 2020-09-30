package com.faouzibidi.albums

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.faouzibidi.albums.repository.remote.AlbumRemoteRepository
import kotlinx.coroutines.runBlocking

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*

/**
* this class is used for testing api calls
 */
@RunWith(AndroidJUnit4::class)
class RemoteRepositoryTest {

    /**
     * in this test we will just test the call of the api
     * and check if at least it returns a valuable data
     */
    @Test
    fun callApi() = runBlocking {
        val repository = AlbumRemoteRepository()
        val albums = repository.getAlbums()
        assertNotNull(albums)
        // assert that ther is at least one element in the list
        //assertNotEquals(albums.size, 0)
    }
}