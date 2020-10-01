package com.faouzibidi.albums.repository.local

import android.content.Context
import androidx.room.Room
import com.faouzibidi.albums.model.Album

/**
 * This repository extends AlbumLocalRepository but use an InMemoeryDatabase
 * for testing purpose
 *
 * @author faouzi BIDI
 */
class AlbumLocalRepositoryFake(private val albumDao : AlbumDao): AlbumLocalRepository(albumDao){

    fun getAlbums(): List<Album>{
        return albumDao.getAll()
    }

    suspend fun deleteAll(){
        return albumDao.deleteAll()
    }
    companion object {
        // Singleton instance
        @Volatile
        private var INSTANCE: AlbumLocalRepositoryFake? = null

        fun getInstance(context: Context): AlbumLocalRepositoryFake {
            val repository =
                INSTANCE
            if (repository != null) {
                return repository
            }
            synchronized(this) {
                val dao = Room.inMemoryDatabaseBuilder(
                    context.applicationContext,
                    AlbumRoomDatabse::class.java).build()
                    .AlbumDao()
                val repository =
                    AlbumLocalRepositoryFake(
                        dao
                    )
                INSTANCE = repository
                return repository
            }
        }
    }

}