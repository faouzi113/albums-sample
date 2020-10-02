package com.faouzibidi.albums

import android.app.Application
import com.faouzibidi.albums.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class AlbumApplication : Application(){

    override fun onCreate() {
        super.onCreate()
        // start koin
        startKoin{
            // Android context
            androidContext(this@AlbumApplication)
            // modules
            modules(appModule)
        }
    }
}