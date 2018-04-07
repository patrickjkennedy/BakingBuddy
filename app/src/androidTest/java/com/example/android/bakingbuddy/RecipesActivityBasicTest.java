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
import static android.support.test.espresso.matcher.ViewMatchers.hasDescendant;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;

@RunWith(AndroidJUnit4.class)
public class RecipesActivityBasicTest {

    private static final String RECIPE_NAME_0 = "Nutella Pie";
    private static final String RECIPE_NAME_1 = "Brownies";
    private static final String RECIPE_STEP = "Prep the cookie crust.";

    @Rule
    public ActivityTestRule<RecipesActivity> mActivityTestRule =
            new ActivityTestRule<>(RecipesActivity.class);

    /**
     * Check the Recyclerview contains the correct recipes
     */
    @Test
    public void checkRecipeName(){
        // Check item at position 0 has "Nutella Pie"
        onView(RecyclerViewMatcher.withRecyclerView(R.id.rv_master_list).atPosition(0))
                .check(matches(hasDescendant(withText(RECIPE_NAME_0))));

        // Check item at position 1 has "Brownies"
        onView(RecyclerViewMatcher.withRecyclerView(R.id.rv_master_list).atPosition(1))
                .check(matches(hasDescendant(withText(RECIPE_NAME_1))));
    }


    /**
     * Clicks on a Recyclerview item and checks it opens up the OverviewActivity with the correct details.
     */
    @Test
    public void clickRecyclerViewItem_OpensOverviewActivity() {

        // Uses {@link Espresso#onData(org.hamcrest.Matcher)} to get a reference to a specific
        // recyclerview item and clicks it.
        onView(withId(R.id.rv_master_list)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));

        // Checks that the OverviewActivity opens with the correct recipe displayed
        onView(RecyclerViewMatcher.withRecyclerView(R.id.rv_steps).atPosition(1))
                .check(matches(hasDescendant(withText(RECIPE_STEP))));
    }
}
