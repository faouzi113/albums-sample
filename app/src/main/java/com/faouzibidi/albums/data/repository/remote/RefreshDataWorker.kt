package com.faouzibidi.albums.data.repository.remote

import android.content.Context
import android.util.Log
import androidx.work.*
import com.faouzibidi.albums.interactor.AlbumInteractor
import com.faouzibidi.albums.data.repository.local.AlbumLocalRepository
import com.faouzibidi.albums.data.repository.local.AlbumRoomDatabse
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope

/**
 * this worker is used for scheduling loading operation
 * to get data updated from the rest api results
 *
 * this worker needs network constraints
 *
 * @see CoroutineWorker
 */
class RefreshDataWorker(context: Context, params: WorkerParameters) : CoroutineWorker(context, params) {
    private val interactor : AlbumInteractor

    init {
        val remoteRepository = AlbumRemoteRepository()
        val localRepository = AlbumLocalRepository(AlbumRoomDatabse.getDatabase(context).AlbumDao())
        interactor = AlbumInteractor(remoteRepository, localRepository, context)
    }

    override suspend fun doWork(): Result = coroutineScope {
        Log.d("worker", "doWork")
        val job = async {
            // load data from api
            interactor.refershData()
        }
        // this will await for the refresh request until it ends
        // else the WorkManager will handle the request as failure
        job.await()
        Result.success()
    }
}
