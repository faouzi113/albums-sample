package com.faouzibidi.albums.workmanager


import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.work.*
import com.faouzibidi.albums.R
import com.faouzibidi.albums.data.repository.remote.RefreshDataWorker
import com.faouzibidi.albums.util.RefreshWorkerHelper
import junit.framework.Assert.assertEquals
import net.bytebuddy.matcher.ElementMatchers.`is`
import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Rule
import java.util.concurrent.TimeUnit

/**
 * this class is used for testing ui components in MainActivity
 *
 * we are going to
 */
@RunWith(AndroidJUnit4::class)
class WorkManagerTest {

    @Test
    @Throws(Exception::class)
    fun testRefreshWorker() {
        // creating network constraints
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()
        // create PeriodicWorkerRequest as we do in RefreshWorkerHelper
        val request = PeriodicWorkRequestBuilder<RefreshDataWorker>(
            RefreshWorkerHelper.REPEAT_INTERVAL, TimeUnit.MINUTES)
            .setConstraints(constraints)
            .setInitialDelay(0, TimeUnit.MINUTES)
            .build()

        val workManager = WorkManager.getInstance(ApplicationProvider.getApplicationContext())
        // enqueu the work and and wait for result
        workManager.enqueue(request).result.get()
        // Get WorkInfo
        val workInfo = workManager.getWorkInfoById(request.id).get()
        // Assert that the work has been enqueued
        assertEquals(workInfo.state, WorkInfo.State.ENQUEUED)
    }


}