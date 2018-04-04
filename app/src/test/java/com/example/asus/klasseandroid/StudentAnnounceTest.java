package com.example.asus.klasseandroid;

/**
 * Created by 1001737 on 4/4/18.
 */
import android.content.SharedPreferences;
import android.widget.Button;

import com.google.firebase.FirebaseApp;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowToast;

import java.util.ArrayList;

import announcements.Announcements;
import announcements.InstructorAnnounce;
import announcements.InstructorViewAnnounce;
import announcements.StudentAnnounce;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by 1001737 on 4/4/18.
 */

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class)
public class StudentAnnounceTest {
    private Announcements a;
    private StudentAnnounce announce;
    private ArrayList<String> content = new ArrayList<String>();
    private ArrayList<String> names=new ArrayList<>();
    private ArrayList<Integer> ids=new ArrayList<>();
    //private ArrayList<Announcements> announce=new ArrayList<>();
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
        announce = Robolectric.buildActivity( StudentAnnounce.class )
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
        a.setClassnum(11);
        content.add(a.getContent());
        names.add(a.getProf());
        ids.add(a.getId());
        assertEquals(content,a.getContent());
        assertEquals(names,a.getProf());
        assertEquals(ids,a.getId());


    }


}
