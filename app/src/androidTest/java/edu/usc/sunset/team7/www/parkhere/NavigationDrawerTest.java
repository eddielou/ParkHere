package edu.usc.sunset.team7.www.parkhere;

import android.content.Intent;
import android.support.test.espresso.contrib.DrawerActions;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v4.widget.DrawerLayout;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import edu.usc.sunset.team7.www.parkhere.Activities.HomeActivity;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.Matchers.anything;

/**
 * Created by Justin on 11/5/2016.
 */

@RunWith(AndroidJUnit4.class)
@LargeTest
public class NavigationDrawerTest {

    @Rule
    public ActivityTestRule<HomeActivity> activityRule =
            new ActivityTestRule<>(HomeActivity.class, true, false);

    @Before
    public void initializeActivity() {
        FirebaseAuth.getInstance().signInWithEmailAndPassword("kunal@me.com", "hello12345");
    }

    @Test
    public void runNavigationDrawerTest(){
        Intent intent = new Intent();
        activityRule.launchActivity(intent);
        DrawerLayout drawer = (DrawerLayout) activityRule.getActivity()
                .findViewById(R.id.drawer_layout);
        ListView listView = (ListView) activityRule.getActivity().findViewById(R.id.left_drawer);

        for (int i = 0; i < 5; i++) {
            Assert.assertEquals(drawer.isDrawerOpen(listView), false);

            DrawerActions.openDrawer(R.id.drawer_layout);

            Assert.assertEquals(drawer.isDrawerOpen(listView), true);

            onData(anything()).inAdapterView(withId(R.id.left_drawer))
                    .atPosition(i).perform(click());
        }
    }
}
