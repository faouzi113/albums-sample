package com.faouzibidi.albums.coverage


import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.faouzibidi.albums.data.model.Album
import com.faouzibidi.albums.mock.repository.local.AlbumLocalRepositoryFake
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

/**
 * this class is used for testing database operations
 *
 * for testing purpose we will use an InMemoryDatabase instead of th real db
 * so we will not make damages on the real db
 */
@RunWith(AndroidJUnit4::class)
class LocalRepositoryTest {
    private lateinit var repository : AlbumLocalRepositoryFake
    /**
     * in this test we will just insert a set of albums, check if they exists in the db
     * then we will delete them
     *
     * we will use an InMemoryDatabase for this
     */
    @Before
    fun init (){
        repository = AlbumLocalRepositoryFake.getInstance(ApplicationProvider.getApplicationContext())
    }

    /**
     * clear data after each test
     */
    @After
    fun tearDown() = runBlocking {
        repository.deleteAll()
    }

    /**
     * test the insert method if it works
     */
    @Test
    fun insert() = runBlocking {
        // genearte test data
        val albums = generateTestData()
        // insert data into db
        repository.storeAlbums(albums)
        // retreive stored data
        val dbAlbums = repository.getAlbums()
        assertNotNull(dbAlbums)
        assertEquals(dbAlbums.size, albums.size)
        //
    }

    /**
     * generate data for test
     */
    private fun generateTestData(): List<Album>{
        // create test data
        val albums = arrayListOf<Album>()
        for (i in 0..10){
            val a = Album(i,i*2,"Album Title "+i, "", "")
            albums.add(a)
        }
        return albums
    }

}