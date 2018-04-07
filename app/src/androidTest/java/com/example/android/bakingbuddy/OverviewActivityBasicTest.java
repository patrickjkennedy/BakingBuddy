package com.example.android.bakingbuddy;

import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import com.example.android.bakingbuddy.ui.RecipesActivity;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.swipeRight;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.assertion.ViewAssertions.matches;

@RunWith(AndroidJUnit4.class)
public class OverviewActivityBasicTest {

    @Rule
    public ActivityTestRule<RecipesActivity> mActivityTestRule =
            new ActivityTestRule<>(RecipesActivity.class);

    /**
     * Checks the OverviewActivity displays tabs
     */
    @Test
    public void checkForTabs() {

        // Uses {@link Espresso#onData(org.hamcrest.Matcher)} to get a reference to a specific
        // recyclerview item and clicks it.
        onView(withId(R.id.rv_master_list)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));

        // Opens the OverviewActivity and checks tabs exist
        onView(withId(R.id.sliding_tabs)).check(matches(isDisplayed()));
    }

    /**
     * Checks the user can swipe right to the ingredients tab
     */
    @Test
    public void swipe() {

        // Uses {@link Espresso#onData(org.hamcrest.Matcher)} to get a reference to a specific
        // recyclerview item and clicks it.
        onView(withId(R.id.rv_master_list)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));

        // Opens the OverviewActivity and checks tabs exist
        onView(withId(R.id.sliding_tabs)).perform(swipeRight());
    }

}
