package com.example.asus.klasseandroid;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import feedback.studentFeedback.WeekView;

import static org.junit.Assert.*;

/**
 * Created by harleen on 4/4/18.
 */
@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class)
public class WeekViewTest {
    private WeekView weekView;

    @Before
    public void setUp() throws Exception {
        weekView = Robolectric.buildActivity( WeekView.class )
                .create()
                .resume()
                .get();


    }
    @Test
    public void shouldNotBeNull() throws Exception
    {
        assertNotNull(weekView );
    }




}