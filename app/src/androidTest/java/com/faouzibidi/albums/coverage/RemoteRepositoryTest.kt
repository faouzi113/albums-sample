package com.faouzibidi.albums.coverage

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry.getInstrumentation
import com.faouzibidi.albums.mock.repository.remote.AlbumRemoteRepositoryFake
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertNotEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

/**
* this class is used for testing api calls
 *
 *
 */
@RunWith(AndroidJUnit4::class)
class RemoteRepositoryTest {
    private lateinit var repositoryFake : AlbumRemoteRepositoryFake

    @Before
    fun initialize(){
        // use getInstrumentation().getContext() instead of ApplicationProvider.getApplicationContext
        // to get context with test resources
        repositoryFake =
            AlbumRemoteRepositoryFake(
                getInstrumentation().getContext()
            )
    }
    /**
     * test getAlbums method
     */
    @Test
    fun getAlbums() = runBlocking {
        val albums = repositoryFake.getAlbums()
        assertNotNull(albums)
        // assert that ther is at least one element in the list
        assertNotEquals(albums.count(), 0)
    }
}