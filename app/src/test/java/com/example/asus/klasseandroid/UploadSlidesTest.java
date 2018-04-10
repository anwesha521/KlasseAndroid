package com.example.asus.klasseandroid;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowToast;

import feedback.instructorFeedback.UploadSlides;

import static org.junit.Assert.*;

/**
 * Created by harleen on 6/4/18.
 */
@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class)
public class UploadSlidesTest {

    private UploadSlides uploadSlides;

    @Before
    public void setUp() throws Exception {
        uploadSlides = Robolectric.buildActivity( UploadSlides.class )
                .create()
                .resume()
                .get();


    }
    @Test
    public void shouldNotBeNull() throws Exception
    {
        assertNotNull(uploadSlides );
    }


//
//    @Test
//    public void startChoose() throws Exception {
//        ImageButton button = (ImageButton) uploadSlides.findViewById( R.id.btn_choose );
//        button.performClick();
//        Intent intent = Shadows.shadowOf(uploadSlides).peekNextStartedActivity();
//        assertEquals(UploadSlides.class.getCanonicalName(), intent.getComponent().getClassName());
//    }


//    @Test
//    public void startUpload() throws Exception {
//    }
//
//    @Test
//    public void onActivityResult() throws Exception {
//    }

    @Test
    public void onRequestPermissionsResult() throws Exception {
        assertEquals((String) ShadowToast.getTextOfLatestToast(),null);
    }

}