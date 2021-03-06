package com.example.myapplication;

import android.content.Context;
import android.os.SystemClock;

import androidx.test.runner.AndroidJUnit4;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;

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
public class test_03_SearchTest {

    @Rule
    public ActivityScenarioRule<MainActivity> activityScenarioRule
            = new ActivityScenarioRule<>(MainActivity.class);

    Context appContext = getInstrumentation().getTargetContext();

    @Test
    public void test_01_SearchButton() {

        SystemClock.sleep(5000);
        onView(withId(R.id.Back)).perform(click());

        SystemClock.sleep(2000);
        onView(withId(R.id.MainButtonSearch)).perform(click());

        SystemClock.sleep(2000);
        onView(withText("CANCEL")).perform(click());

    }
}

