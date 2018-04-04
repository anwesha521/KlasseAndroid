package com.example.asus.klasseandroid;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.FirebaseApp;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowToast;

import announcements.InstructorAnnounce;
import chatroom.ChatMessage;
import chatroom.ChatRoom;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by 1001737 on 4/4/18.
 */

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class)
public class InstructorAnnounceTest {

    private InstructorAnnounce announce;
    int type = 0;
    String id;
    //final EditText e=announce.findViewById(R.id.announcetext);
    SharedPreferences pref;
    SharedPreferences prefName;
    SharedPreferences.Editor editor;
    SharedPreferences.Editor editorName;
    String t;
    String q ="";


    @Before
    public void setUp() throws Exception {
        announce = Robolectric.buildActivity( InstructorAnnounce.class )
                .create()
                .resume()
                .get();
        if (FirebaseApp.getApps(announce).isEmpty()) {
            FirebaseApp.initializeApp(announce);
        }

    }

    @Test
    public void shouldNotBeNull() throws Exception
    {
        assertNotNull( announce );
    }



    @Test
    public void testButtonIfNoInput() throws Exception {
        Button b = (Button) announce.findViewById(R.id.postannounce);
        assertNotNull(b);
        b.performClick();
        String a = "";
        assertEquals((String) ShadowToast.getTextOfLatestToast(),"Please enter message.");
    }


}

