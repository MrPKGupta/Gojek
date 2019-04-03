package com.example.gojek;

import com.example.gojek.ui.MainActivity;

import org.junit.Rule;
import org.junit.Test;

import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.rule.ActivityTestRule;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;

public class MainActivityTest {
    @Rule
    public ActivityTestRule<MainActivity> activityRule =
            new ActivityTestRule<>(MainActivity.class);

    @Rule
    public OkHttpIdlingResourceRule okHttpIdlingResourceRule = new OkHttpIdlingResourceRule();


    @Test
    public void testSuccess() {
        onView(ViewMatchers.withId(R.id.tv_temp)).check(matches(isDisplayed()));
    }
}
