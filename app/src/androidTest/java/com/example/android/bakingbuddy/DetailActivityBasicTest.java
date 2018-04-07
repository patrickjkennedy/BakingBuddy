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
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.core.StringContains.containsString;

@RunWith(AndroidJUnit4.class)
public class DetailActivityBasicTest {

    private String STARTING_PREP_DESC = "Preheat the oven to 350";
    private String COOKIE_CRUST_DESC = "Whisk the graham cracker crumbs";

    @Rule
    public ActivityTestRule<RecipesActivity> mActivityTestRule =
            new ActivityTestRule<>(RecipesActivity.class);

    /**
     * Navigate to the DetailActivity for the first step in the recipe
     */
    @Test
    public void navigateToDetailActivityAndCheckDesc() {

        // Uses {@link Espresso#onData(org.hamcrest.Matcher)} to get a reference to a specific
        // recyclerview item and clicks it.
        onView(withId(R.id.rv_master_list)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));

        // Click on a step in the OverviewActivity
        onView(withId(R.id.rv_steps)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));

        // Check the description contains the prep description as expected
        onView(withId(R.id.tv_detail_description)).check(matches(withText(containsString(STARTING_PREP_DESC))));
    }

    /**
     * Navigate to the DetailActivity for the first step in the recipe and the navigate right using the Next button
     */

    @Test
    public void navigateToNextStep() {

        // Navigate to DetailActivity
        onView(withId(R.id.rv_master_list)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        onView(withId(R.id.rv_steps)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));

        // Click the Next Button
        onView(withId(R.id.btn_next)).perform(click());

        // Check the Description of the next step
        onView(withId(R.id.tv_detail_description)).check(matches(withText(containsString(COOKIE_CRUST_DESC))));
    }

    /**
     * Navigate to the second step in the recipe and then click Previous
     */

    @Test
    public void navigateToFirstStep() {

        // Navigate to DetailActivity
        onView(withId(R.id.rv_master_list)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        onView(withId(R.id.rv_steps)).perform(RecyclerViewActions.actionOnItemAtPosition(1, click()));

        // Click the Next Button
        onView(withId(R.id.btn_previous)).perform(click());

        // Check the Description of the next step
        onView(withId(R.id.tv_detail_description)).check(matches(withText(containsString(STARTING_PREP_DESC))));
    }

}
