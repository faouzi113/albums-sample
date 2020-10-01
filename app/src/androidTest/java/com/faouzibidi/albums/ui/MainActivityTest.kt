package com.faouzibidi.albums.ui


import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.faouzibidi.albums.MainActivity
import com.faouzibidi.albums.R
import org.junit.Test
import org.junit.runner.RunWith

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