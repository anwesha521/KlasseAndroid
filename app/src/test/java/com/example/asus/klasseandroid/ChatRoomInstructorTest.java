package com.example.asus.klasseandroid;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
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

import chatroom.ChatMessage;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by 1001737 on 4/4/18.
 */


import android.content.Context;


import chatroom.ChatRoomInstructor;


    /**
     * Created by 1001737 on 2/4/18.
     */
    @RunWith(RobolectricTestRunner.class)
    @Config(constants = BuildConfig.class)
    public class ChatRoomInstructorTest {

        private ChatRoomInstructor chatroom;
        private ChatMessage chatMessage;
        int type = 0;
        String id;
        SharedPreferences pref;
        SharedPreferences prefName;
        SharedPreferences.Editor editor;
        SharedPreferences.Editor editorName;
        String t;
        String q ="";


        @Before
        public void setUp() throws Exception {
            chatroom = Robolectric.buildActivity( ChatRoomInstructor.class )
                    .create()
                    .resume()
                    .get();
            if (FirebaseApp.getApps(chatroom).isEmpty()) {
                FirebaseApp.initializeApp(chatroom);
            }
            pref=chatroom .getApplicationContext().getSharedPreferences("UserDetails", Context.MODE_PRIVATE);
            pref.edit().putString("id","2000003").commit();
            Intent intent=new Intent();


        }

        @Test
        public void checkActivityDefault() throws Exception{
            assertNotNull(chatroom );
            Intent intent=chatroom .getIntent();
            intent.putExtra("id",11);
            int id=intent.getIntExtra("id",1);
            assertEquals(11,id);
            assertEquals("2000003",pref.getString("id","1"));
        }




        @Test
        public void testFloatingActionButtonIfNoInput() throws Exception {
            FloatingActionButton fab = (FloatingActionButton) chatroom.findViewById(R.id.fab);
            assertNotNull(fab);
            fab.performClick();
            String txt = "";
            assertEquals((String) ShadowToast.getTextOfLatestToast(),"Please enter message.");
        }
        @Test
        public void testFloatingActionButtonIfTypeisOne() throws Exception {
            type = 1;
            FloatingActionButton fab = (FloatingActionButton) chatroom.findViewById(R.id.fab);
            assertNotNull(fab);
            fab.performClick();
            EditText input = (EditText) chatroom.findViewById(R.id.input);
            String txt=input.getText().toString();
            chatMessage = new ChatMessage(txt, "Sudipta", "reply", 11, id);
            //chatMessage.setQuestion(q);

            //FirebaseDatabase.getInstance()
            //.getReference().child(id).setValue(chatMessage);
            assertEquals(chatMessage.getMessageType(),"reply");
        }
        @Test
        public void testFloatingActionButtonIfTypeisNotOne() throws Exception {
            type = 0;
            FloatingActionButton fab = (FloatingActionButton) chatroom.findViewById(R.id.fab);
            //assertNotNull(fab);
            fab.performClick();
            EditText input = (EditText) chatroom.findViewById(R.id.input);
            String txt=input.getText().toString();
            chatMessage = new ChatMessage(txt, "Sudipta", "question", 11, id);

            assertEquals(chatMessage.getMessageType(),"question");
        }
        @Test
        public void testPopulateView() throws Exception {
            View v = new View(chatroom);
            final int position;
            TextView messageText = (TextView) v.findViewById(R.id.message_text);
            TextView messageUser = (TextView) v.findViewById(R.id.message_user);
            TextView messageTime = (TextView) v.findViewById(R.id.message_time);
            EditText input = (EditText) chatroom.findViewById(R.id.input);
            String txt=input.getText().toString();
            chatMessage = new ChatMessage(txt, "Sudipta", "question", 11, id);
            // Set their text
            final String message= chatMessage.getMessageText();
            final int msg_id = chatMessage.get_id();

            assertEquals(message,txt);
            assertEquals(msg_id,11);
        }

    }


