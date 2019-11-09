package com.example.myapplication;

import android.content.Context;
import android.os.SystemClock;

import androidx.test.platform.app.InstrumentationRegistry;
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
public class test_04_HistoryTest {

    @Rule
    public ActivityScenarioRule<MainActivity> activityScenarioRule
            = new ActivityScenarioRule<>(MainActivity.class);

    Context appContext = getInstrumentation().getTargetContext();

    int[] buttons = {
            R.id.item_1,
            R.id.item_2,
            R.id.item_3,
            R.id.item_4,
            R.id.item_5,
            R.id.item_6,
            R.id.item_7,
            R.id.item_8,
            R.id.item_9,
            R.id.item_10,
    };

    @Test
    public void test_01_HistoryTest() {
        SystemClock.sleep(4500);
        onView(withId(R.id.Back)).perform(click());

        SystemClock.sleep(1500);
        onView(withId(R.id.button_history)).perform(click());

        //Test only the history items 1-10
        for(int i = 0; i < buttons.length; i++){
            SystemClock.sleep(500);
            onView(withId(buttons[i])).perform(click());
            SystemClock.sleep(1000);
            onView(withText("CANCEL")).perform(click());
        }

        SystemClock.sleep(500);
        onView(withText("CANCEL")).perform(click());


    }
}