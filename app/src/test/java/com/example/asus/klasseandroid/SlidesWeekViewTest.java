package com.example.asus.klasseandroid;

import android.content.Intent;
import android.widget.Button;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.Shadows;
import org.robolectric.annotation.Config;


import feedback.studentFeedback.SlidesWeekView;
import feedback.studentFeedback.WeekView;

import static org.junit.Assert.*;

/**
 * Created by harleen on 4/4/18.
 */
@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class)
public class SlidesWeekViewTest {
    private SlidesWeekView slidesWeekView;

    @Before
    public void setUp() throws Exception {
        slidesWeekView = Robolectric.buildActivity( SlidesWeekView.class )
                .create()
                .resume()
                .get();


    }
    @Test
    public void shouldNotBeNull() throws Exception
    {
        assertNotNull(slidesWeekView );
    }



    @Test
    public void week1() throws Exception {
        Button button = (Button) slidesWeekView.findViewById( R.id.weekV1 );
        button.performClick();
        Intent intent = Shadows.shadowOf(slidesWeekView).peekNextStartedActivity();
        assertEquals(WeekView.class.getCanonicalName(), intent.getComponent().getClassName());
    }

    @Test
    public void week2() throws Exception {
        Button button = (Button) slidesWeekView.findViewById( R.id.weekV2 );
        button.performClick();
        Intent intent = Shadows.shadowOf(slidesWeekView).peekNextStartedActivity();
        assertEquals(WeekView.class.getCanonicalName(), intent.getComponent().getClassName());
    }

    @Test
    public void week3() throws Exception {
        Button button = (Button) slidesWeekView.findViewById( R.id.weekV3 );
        button.performClick();
        Intent intent = Shadows.shadowOf(slidesWeekView).peekNextStartedActivity();
        assertEquals(WeekView.class.getCanonicalName(), intent.getComponent().getClassName());
    }

}