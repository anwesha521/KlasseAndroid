package com.example.asus.klasseandroid;


import android.app.Activity;
import android.app.Instrumentation;
import android.content.Intent;
import android.net.Uri;
import android.provider.ContactsContract;
import android.widget.Button;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.Shadows;
import org.robolectric.annotation.Config;

import chatroom.ChatRoom;
import feedback.ChooseSlidesActivity;
import feedback.Comments;
import feedback.UploadSlides;

import static org.junit.Assert.*;

/**
 * Created by harleen on 4/4/18.
 * COMPLETED!!
 */
@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class)
public class ChooseSlidesActivityTest {


    private ChooseSlidesActivity chooseSlidesActivity;

    @Before
    public void setUp() throws Exception {
        chooseSlidesActivity = Robolectric.buildActivity( ChooseSlidesActivity.class )
                .create()
                .resume()
                .get();


    }
    @Test
    public void shouldNotBeNull() throws Exception
    {
        assertNotNull(chooseSlidesActivity );
    }

    //testing intents

    @Test
    public void uploadPdf() throws Exception
    {
        Button button = (Button) chooseSlidesActivity.findViewById( R.id.chooseUpload );
        button.performClick();
        Intent intent = Shadows.shadowOf(chooseSlidesActivity).peekNextStartedActivity();
        assertEquals(UploadSlides.class.getCanonicalName(), intent.getComponent().getClassName());
    }
    @Test
    public void ViewFeedback() throws Exception
    {
        Button button = (Button) chooseSlidesActivity.findViewById( R.id.chooseViewFeedback );
        button.performClick();
        Intent intent = Shadows.shadowOf(chooseSlidesActivity).peekNextStartedActivity();
        assertEquals(Comments.class.getCanonicalName(), intent.getComponent().getClassName());
    }


}