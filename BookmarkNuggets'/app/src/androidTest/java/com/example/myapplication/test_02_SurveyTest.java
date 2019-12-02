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
public class test_02_SurveyTest {

    @Rule public ActivityScenarioRule<MainActivity> activityScenarioRule
            = new ActivityScenarioRule<>(MainActivity.class);

    Context appContext = getInstrumentation().getTargetContext();

    int[] buttons = {
        R.id.answer1,
        R.id.answer2,
        R.id.answer3,
        R.id.answer4,
        R.id.Back
    };

    @Test
    public void test_01_SurveyChoices() {
        // Context of the app under test.
        SystemClock.sleep(4000);

        //Test only the choice buttons
        for(int i = 0; i < buttons.length-1; i++){

            for(int j = 0; j < 4; j++){
                onView(withId(buttons[i])).perform(click());
                SystemClock.sleep(500);
            }
            openActionBarOverflowOrOptionsMenu(appContext);

            SystemClock.sleep(500);

            onView(withText("Retake Survey")).perform(click());

            SystemClock.sleep(1000);

        }
    }

    @Test
    public void test_02_BackAndCancel() {
        SystemClock.sleep(4000);

        //stop at last question,
        for(int i = 0; i < 3; i++){
            onView(withId(buttons[i])).perform(click());
            SystemClock.sleep(1000);
        }
        //then go back to first question,
        for(int i = 0; i < 3; i++){
            onView(withId(R.id.Back)).perform(click());
            SystemClock.sleep(1000);
        }
        //and cancel out of the survey
        onView(withId(R.id.Back)).perform(click());
        SystemClock.sleep(1000);
    }
}
