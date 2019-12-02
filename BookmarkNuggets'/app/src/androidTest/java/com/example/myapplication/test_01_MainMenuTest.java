package com.example.myapplication;

import android.content.Context;
import android.os.SystemClock;

import androidx.test.runner.AndroidJUnit4;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu;
import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;

import org.junit.Rule;
import org.junit.runners.MethodSorters;

import androidx.test.ext.junit.rules.ActivityScenarioRule;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class test_01_MainMenuTest {

    @Rule public ActivityScenarioRule<MainActivity> activityScenarioRule
            = new ActivityScenarioRule<>(MainActivity.class);


    Context appContext = getInstrumentation().getTargetContext();

    @Test
    public void test_01_SearchButton() {

        SystemClock.sleep(4500);
        onView(withId(R.id.Back)).perform(click());

        SystemClock.sleep(2000);
        onView(withId(R.id.MainButtonSearch)).perform(click());

        SystemClock.sleep(2000);
        onView(withText("CANCEL")).perform(click());

    }

    @Test
    public void test_02_HistoryButton() {

        SystemClock.sleep(2000);
        onView(withId(R.id.Back)).perform(click());

        SystemClock.sleep(2000);
        onView(withId(R.id.button_history)).perform(click());

        SystemClock.sleep(2000);
        onView(withText("CANCEL")).perform(click());


    }

    @Test
    public void test_03_BookmarkButton() {

        SystemClock.sleep(2000);
        onView(withId(R.id.Back)).perform(click());

        SystemClock.sleep(2000);
        onView(withId(R.id.button_bookmark)).perform(click());

        SystemClock.sleep(2000);
        onView(withText("CANCEL")).perform(click());


    }

    @Test
    public void test_02_MenuOptions() {

        SystemClock.sleep(2000);
        onView(withId(R.id.Back)).perform(click());

        //settings
        SystemClock.sleep(2000);
        openActionBarOverflowOrOptionsMenu(appContext);

        SystemClock.sleep(2000);
        onView(withText("Settings")).perform(click());

        SystemClock.sleep(2000);
        onView(withText("CANCEL")).perform(click());

        //history
        SystemClock.sleep(2000);
        openActionBarOverflowOrOptionsMenu(appContext);

        SystemClock.sleep(2000);
        onView(withText("History")).perform(click());

        SystemClock.sleep(2000);
        onView(withText("CANCEL")).perform(click());

        //bookmark
        SystemClock.sleep(2000);
        openActionBarOverflowOrOptionsMenu(appContext);

        SystemClock.sleep(2000);
        onView(withText("History")).perform(click());

        SystemClock.sleep(2000);
        onView(withText("CANCEL")).perform(click());

        //retake survey
        SystemClock.sleep(2000);
        openActionBarOverflowOrOptionsMenu(appContext);

        SystemClock.sleep(2000);
        onView(withText("Retake Survey")).perform(click());

        SystemClock.sleep(2000);
        onView(withText("CANCEL")).perform(click());
    }
}
