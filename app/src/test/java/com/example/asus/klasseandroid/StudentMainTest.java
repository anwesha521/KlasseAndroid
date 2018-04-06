package com.example.asus.klasseandroid;

/**
 * Created by 1001737 on 4/4/18.
 */
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;

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
public class StudentMainTest {

    private studentMain main;
    String type;
    String id;
    boolean CheckEditText;
    Button btnlogin = main.findViewById(R.id.signin);
    EditText email = main.findViewById(R.id.userId);
    EditText pw = main.findViewById(R.id.password);
    SharedPreferences pref;
    SharedPreferences prefName;
    SharedPreferences.Editor editor;
    SharedPreferences.Editor editorName;
    String t;
    String q ="";


    @Before
    public void setUp() throws Exception {
        main = Robolectric.buildActivity( studentMain.class )
                .create()
                .resume()
                .get();

    }

    @Test
    public void shouldNotBeNull() throws Exception
    {
        assertNotNull( main );
    }



    @Test
    public void testStudentAnalytics() throws Exception {

    }
}
