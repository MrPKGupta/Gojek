package com.example.gojek;

import com.example.gojek.api.ServiceGenerator;
import com.example.gojek.ui.MainActivity;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.rule.ActivityTestRule;
import okhttp3.mockwebserver.MockResponse;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;

public class MainActivityFailTest {
    @Rule
    public ActivityTestRule<MainActivity> activityRule
            = new ActivityTestRule<>(MainActivity.class, true, false);

    @Rule
    public OkHttpIdlingResourceRule okHttpIdlingResourceRule = new OkHttpIdlingResourceRule();

    @Rule
    public MockWebServerRule mockWebServerRule = new MockWebServerRule();

    @Before
    public void setBaseUrl() {
        ServiceGenerator.setBaseUrl(mockWebServerRule.server.url("/").toString());
    }

    @Test
    public void status404() {
        mockWebServerRule.server.enqueue(new MockResponse().setResponseCode(404));

        activityRule.launchActivity(null);

        onView(ViewMatchers.withId(R.id.tv_error_message)).check(matches(isDisplayed()));
    }
}
