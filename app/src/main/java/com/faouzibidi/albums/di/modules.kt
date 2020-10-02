package com.faouzibidi.albums.di

import com.faouzibidi.albums.data.repository.local.AlbumLocalRepository
import com.faouzibidi.albums.data.repository.local.AlbumRoomDatabse
import com.faouzibidi.albums.data.repository.remote.AlbumRemoteRepository
import com.faouzibidi.albums.interactor.AlbumInteractor
import com.faouzibidi.albums.ui.AlbumViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

/**
 * this kotlin file is for declaring
 * modules for depemdency injection with koin
 *
 * @see org.koin.androidx.viewmodel
 */
var appModule = module {

    // AlbumRemoteRepository
    factory { AlbumRemoteRepository() }
    // AlbumLocalRepository
    factory { AlbumLocalRepository(AlbumRoomDatabse.getDatabase(get()).AlbumDao()) }
    // AlbumLocalRepository
    single { AlbumInteractor(get(), get(), get()) }
    // viewmodel
    viewModel { AlbumViewModel(get(), get()) }

}