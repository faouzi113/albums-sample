package com.faouzibidi.albums.util

import android.content.Context
import android.util.Log
import androidx.work.*
import com.faouzibidi.albums.data.repository.remote.RefreshDataWorker
import java.util.concurrent.TimeUnit

/**
 * this util class is used for creating
 * a refesh periodic request and start the worker
 *
 * @author Faouzi BIDI
 *
 * @see PeriodicWorkRequestBuilder
 * @see Constraints
 * @see WorkManager
 *
 */
class RefreshWorkerHelper{

    companion object {
        // set refresh periodicity interval
        val REPEAT_INTERVAL = 6L // in minutes
        private val WORKER_TAG = "REFRESH_WORKER"

        fun launchRefreshRequest(context: Context){
            // calling refresh method withoud intial delay
            launchRefreshRequest(context, 0)
        }

        /**
         * schedule a refresh task by crearting a RefreshDataWorker
         * and sending a periodic request to the WorkManager
         */
        fun launchRefreshRequest(context: Context, initialDelay: Long){
            // since the worker need internet connection
            // to load data we will use a constraint for network
            val constraints = Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build()
            // create PeriodicWorkerRequest
            val refreshPeriodicRequest = PeriodicWorkRequestBuilder<RefreshDataWorker>(REPEAT_INTERVAL, TimeUnit.MINUTES)
                .setConstraints(constraints)
                .setInitialDelay(initialDelay, TimeUnit.MINUTES)
                .build()
            //
            WorkManager.getInstance(context)
                .enqueueUniquePeriodicWork(WORKER_TAG,
                    // if we have already an enqued request so keep it
                    ExistingPeriodicWorkPolicy.KEEP,
                    refreshPeriodicRequest)
            //
            Log.d("worker", "refresh request enqued")
        }
    }
}