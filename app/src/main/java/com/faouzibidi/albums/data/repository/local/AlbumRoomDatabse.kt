package com.faouzibidi.albums.data.repository.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.faouzibidi.albums.data.model.Album

/**
 * this is the abstract class for the database
 * this database allows us to store data got from the webservice
 *
 * @author Faouzi BIDI
 */
@Database(entities = arrayOf(Album::class), version = 1, exportSchema = false)
abstract class AlbumRoomDatabse : RoomDatabase(){

    /**
     * this is the Dao object that expose all
     * methodes for getting and setting
     */
     abstract fun AlbumDao():AlbumDao

    companion object {
        // Singleton instance
        @Volatile
        private var INSTANCE: AlbumRoomDatabse? = null

        private val DB_NAME = "album_database"

        fun getDatabase(context: Context): AlbumRoomDatabse {
            val db = INSTANCE
            if (db != null) {
                return db
            }
            synchronized(this) {
                val db = Room.databaseBuilder(
                    context.applicationContext,
                    AlbumRoomDatabse::class.java,
                    DB_NAME
                ).build()
                INSTANCE = db
                return db
            }
        }
    }
}