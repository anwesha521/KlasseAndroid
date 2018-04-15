package com.example.asus.klasseandroid;

/**
 * Created by 1001737 on 4/4/18.
 */

import android.content.SharedPreferences;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.util.Random;

import general.Login;

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

    SharedPreferences pref;
    SharedPreferences prefName;
    SharedPreferences.Editor editor;
    SharedPreferences.Editor editorName;
    String t;
    String q ="";
    int l;


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

    @Test
    public void testCFuzzerEmail() throws Exception {
        EditText emailEdit = login.findViewById(R.id.userId);
        String f=fuzzer();
        emailEdit.setText(f);

        assertEquals(f.equals(emailEdit.getText().toString()),true);
    }


    public String fuzzer()
    {
        Random rand = new Random();

        int  len = rand.nextInt(25) + 0;
        String s="";

        for(int i=0;i<len;i++)
        {

            int j=rand.nextInt(3) + 1;
            switch(j)
            {
                case 1:
                    s=s+Character.toString((char)(rand.nextInt(10)+48));
                    break;
                case 2:
                    s=s+Character.toString((char)(rand.nextInt(26)+65));
                    break;
                case 3:
                    s=s+Character.toString((char)(rand.nextInt(26)+97));
                    break;

                default:
                    System.out.println("Error");

            }


        }
        s=s+"A";
        l=s.length();

        return s;
    }
}
