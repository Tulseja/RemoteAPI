package com.remoteapi.nikhilkumar.remoteapi.ui


import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.Espresso.pressBack
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.test.filters.LargeTest

import android.view.View
import android.view.ViewGroup
import com.remoteapi.nikhilkumar.remoteapi.R
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers.allOf
import org.hamcrest.TypeSafeMatcher
import org.junit.Rule
import org.junit.Test


@LargeTest
class HomeActivityTest {

    @Rule
    @JvmField
    var mActivityTestRule = ActivityTestRule(HomeActivity::class.java)

    @Test
    fun HomeActivityTest() {
        val imageButton = onView(
                allOf(withId(R.id.fab_bottom_btn),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                3),
                        isDisplayed()))
        imageButton.check(matches(isDisplayed()))

        val imageButton2 = onView(
                allOf(withId(R.id.fab_bottom_btn),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                3),
                        isDisplayed()))
        imageButton2.check(matches(isDisplayed()))

        val imageButton3 = onView(
                allOf(withId(R.id.fab_bottom_btn),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                3),
                        isDisplayed()))
        imageButton3.check(matches(isDisplayed()))

        val imageButton4 = onView(
                allOf(withId(R.id.fab_bottom_btn),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                3),
                        isDisplayed()))
        imageButton4.check(matches(isDisplayed()))

        val imageButton5 = onView(
                allOf(withId(R.id.fab_bottom_btn),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                3),
                        isDisplayed()))
        imageButton5.check(matches(isDisplayed()))

        val imageButton6 = onView(
                allOf(withId(R.id.fab_bottom_btn),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                3),
                        isDisplayed()))
        imageButton6.check(matches(isDisplayed()))

        val floatingActionButton = onView(
                allOf(withId(R.id.fab_bottom_btn),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                5),
                        isDisplayed()))
        floatingActionButton.perform(click())

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        Thread.sleep(700)

        val appCompatImageView = onView(
                allOf(withId(R.id.plus_iv),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.invoiceRv),
                                        0),
                                5),
                        isDisplayed()))
        appCompatImageView.perform(click())

        val appCompatImageView2 = onView(
                allOf(withId(R.id.plus_iv),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.invoiceRv),
                                        4),
                                5),
                        isDisplayed()))
        appCompatImageView2.perform(click())

        val appCompatImageView3 = onView(
                allOf(withId(R.id.plus_iv),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.invoiceRv),
                                        4),
                                5),
                        isDisplayed()))
        appCompatImageView3.perform(click())

        val appCompatTextView = onView(
                allOf(withId(R.id.create_inv_tv), withText("Create Invoice"),
                        childAtPosition(
                                allOf(withId(R.id.create_inv_parent_lyt),
                                        childAtPosition(
                                                withId(android.R.id.content),
                                                0)),
                                8),
                        isDisplayed()))
        appCompatTextView.perform(click())

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        Thread.sleep(700)

        pressBack()
    }

    private fun childAtPosition(
            parentMatcher: Matcher<View>, position: Int): Matcher<View> {

        return object : TypeSafeMatcher<View>() {
            override fun describeTo(description: Description) {
                description.appendText("Child at position $position in parent ")
                parentMatcher.describeTo(description)
            }

            public override fun matchesSafely(view: View): Boolean {
                val parent = view.parent
                return parent is ViewGroup && parentMatcher.matches(parent)
                        && view == parent.getChildAt(position)
            }
        }
    }
}
