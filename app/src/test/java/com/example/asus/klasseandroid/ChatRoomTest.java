package com.example.asus.klasseandroid;

/**
 * Created by 1001737 on 3/4/18.
 */

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.FloatingActionButton;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseListAdapter;
import com.google.android.gms.auth.api.Auth;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.FirebaseDatabase;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;



import org.junit.runner.RunWith;

import announcements.DisplayAdapaterInstructorAnnounce;
import chatroom.ChatMessage;
import chatroom.ChatRoom;

import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowAlertDialog;
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
    private ChatMessage chatMessage;
    int type = 0;
    String id;
    SharedPreferences pref;


    @Before
    public void setUp() throws Exception {
        chatroom = Robolectric.buildActivity( ChatRoom.class )
                .create()
                .resume()
                .get();
        if (FirebaseApp.getApps(chatroom).isEmpty()) {
            FirebaseApp.initializeApp(chatroom);
        }
        pref=chatroom .getApplicationContext().getSharedPreferences("UserDetails", Context.MODE_PRIVATE);
        pref.edit().putString("id","1000004").commit();
        Intent intent=new Intent();


    }

    @Test
    public void checkActivityDefault() throws Exception{
        assertNotNull(chatroom );
        Intent intent=chatroom .getIntent();
        intent.putExtra("id",11);
        int id=intent.getIntExtra("id",1);
        assertEquals(11,id);
        assertEquals("1000004",pref.getString("id","1"));
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
    public void testFloatingActionButtonIfTypeisOne() throws Exception {
        type = 1;
        FloatingActionButton fab = (FloatingActionButton) chatroom.findViewById(R.id.fab);
        assertNotNull(fab);
        fab.performClick();
        EditText input = (EditText) chatroom.findViewById(R.id.input);
        String txt=input.getText().toString();
        chatMessage = new ChatMessage(txt, "Harleen", "reply", 11, id);
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
        chatMessage = new ChatMessage(txt, "Roshni", "question", 11, id);

        assertEquals(chatMessage.getMessageType(),"question");
    }



    }

