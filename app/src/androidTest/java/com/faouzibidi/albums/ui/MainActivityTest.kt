package com.faouzibidi.albums.ui


import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.faouzibidi.albums.MainActivity
import com.faouzibidi.albums.R
import com.faouzibidi.albums.interactor.AlbumInteractor
import com.faouzibidi.albums.repository.local.AlbumLocalRepository
import com.faouzibidi.albums.repository.local.AlbumRoomDatabse
import com.faouzibidi.albums.repository.remote.AlbumRemoteRepository
import kotlinx.android.synthetic.main.activity_main.view.*
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule

/**
 * this class is used for testing ui components in MainActivity
 *
 * we are going to
 */
@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    @get:Rule
    var activityRule: ActivityScenarioRule<MainActivity>
            = ActivityScenarioRule(MainActivity::class.java)


    /**
     * in this method we are going
     * to load MainActivity and check if there
     * is elements in recycler view
     */
    @Test
    fun loadAlbums(){
        onView(withId(R.id.album_recycler_view))
            .check(matches(isDisplayed()))
    }

}