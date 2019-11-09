package com.example.myapplication;

import android.content.Context;
import android.os.SystemClock;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.GrantPermissionRule;
import androidx.test.runner.AndroidJUnit4;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu;
import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;
import static org.junit.Assert.*;
import org.junit.Rule;
import org.junit.runners.MethodSorters;

import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.filters.LargeTest;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class test_07_StoragePermissionsTest {

    @Rule
    public ActivityScenarioRule<MainActivity> activityScenarioRule
            = new ActivityScenarioRule<>(MainActivity.class);

//    @Rule public GrantPermissionRule mRuntimePermissionRule
//            = GrantPermissionRule .grant(android.Manifest.permission.WRITE_EXTERNAL_STORAGE);

    Context appContext = getInstrumentation().getTargetContext();

    @Test
    public void test_01_SearchButton() {

        SystemClock.sleep(4500);
        onView(withId(R.id.Back)).perform(click());

        SystemClock.sleep(1000);
        onView(withId(R.id.button_search)).perform(click());

        SystemClock.sleep(1000);
        onView(withText("Allow")).perform(click());

        SystemClock.sleep(2000);
        onView(withText("CANCEL")).perform(click());

    }
}