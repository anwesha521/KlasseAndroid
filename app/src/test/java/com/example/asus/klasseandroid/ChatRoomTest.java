package com.example.asus.klasseandroid;

/**
 * Created by 1001737 on 3/4/18.
 */

import android.content.Context;
import android.content.SharedPreferences;
import android.support.design.widget.FloatingActionButton;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;



import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import chatroom.ChatRoom;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowToast;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.*;
/**
 * Created by 1001737 on 2/4/18.
 */
@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class)
public class ChatRoomTest {

    private ChatRoom chatroom;


    @Before
    public void setUp() throws Exception {
        chatroom = Robolectric.buildActivity( ChatRoom.class )
                .create()
                .resume()
                .get();

    }

    @Test
    public void shouldNotBeNull() throws Exception
    {
        assertNotNull( chatroom );
    }



    @Test
    public void testFloatingActionButtonIfNoInput() throws Exception {
        FloatingActionButton fab = (FloatingActionButton) chatroom.findViewById(R.id.fab);
        assertNotNull(fab);
        fab.performClick();
        String txt = "";
        assertEquals((String)ShadowToast.getTextOfLatestToast(),"Please enter message.");
    }

    @Test
    public void displayChatMessages() throws Exception {
    }

}
