package com.example.asus.klasseandroid;

/**
 * Created by 1001737 on 3/4/18.
 */

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
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

/**
 * Created by 1001737 on 2/4/18.
 */
@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class)
public class ChatRoomTest {

    private ChatRoom chatroom;
    @Before
    public void setUp() throws Exception {
        activity = Robolectric.buildActivity( WelcomeActivity.class )
                .create()
                .resume()
                .get();

    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void onStart() throws Exception {
    }

    @Test
    public void onCreate() throws Exception {
    }

    @Test
    public void displayChatMessages() throws Exception {
    }

}
