package com.example.asus.klasseandroid;

/**
 * Created by 1001737 on 4/4/18.
 */

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.support.design.widget.FloatingActionButton;
import android.text.TextUtils;
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

import chatroom.ChatMessage;
import chatroom.ChatRoom;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class)
public class LoginTest {

    private Login login;
    String type;
    String id;
    boolean CheckEditText;
    Button btnlogin = login.findViewById(R.id.signin);
    EditText email = login.findViewById(R.id.userId);
    EditText pw = login.findViewById(R.id.password);
    SharedPreferences pref;
    SharedPreferences prefName;
    SharedPreferences.Editor editor;
    SharedPreferences.Editor editorName;
    String t;
    String q ="";


    @Before
    public void setUp() throws Exception {
        login = Robolectric.buildActivity( Login.class )
                .create()
                .resume()
                .get();

    }

    @Test
    public void shouldNotBeNull() throws Exception
    {
        assertNotNull( login );
    }



    @Test
    public void testCheckEditText() throws Exception {
        String EmailHolder="";
        String PasswordHolder="";
        if (TextUtils.isEmpty(EmailHolder) || TextUtils.isEmpty(PasswordHolder)) {
            CheckEditText = false;
        }
        assertFalse(CheckEditText);
    }
}
