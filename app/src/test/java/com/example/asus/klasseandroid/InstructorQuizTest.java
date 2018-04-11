package com.example.asus.klasseandroid;

import android.content.Intent;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import org.bouncycastle.LICENSE;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowToast;

import static android.content.Context.MODE_PRIVATE;
//import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.junit.Assert.*;
import static org.hamcrest.core.IsEqual.equalTo;

/**
 * Created by wsq96 on 2018/4/4.
 */

@RunWith(RobolectricTestRunner.class)
@Config(sdk = Build.VERSION_CODES.JELLY_BEAN_MR2)

public class InstructorQuizTest {
    private InstructorQuiz activity;


    @Before
    public void setUp() throws Exception{
        Intent intent=new Intent();
        intent.putExtra("id",11);
        activity = Robolectric.buildActivity(InstructorQuiz.class,intent)
                .create()
                .resume()
                .get();

        activity.pref=activity.getApplicationContext().getSharedPreferences("UserDetails", MODE_PRIVATE);
        activity.pref.edit().putString("id","2000003").commit();
    }

    @Test
    public void checkActivityDefault() throws Exception{
        assertNotNull(activity);
        Intent intent=activity.getIntent();
        int id=intent.getIntExtra("id",1);
        assertEquals(11,id);
        assertEquals("2000003",activity.pref.getString("id","1"));
        assertNotNull(activity.getRequestQueue());
    }

    @Test
    public void checkListView() throws Exception{
        ListView listView=(ListView)activity.findViewById(R.id.questionList);
        InstructorQuizAdapter adapter=(InstructorQuizAdapter)listView.getAdapter();
        assertEquals(1,adapter.getCount());

        View view=adapter.getView(0,null,null);
        assertNotNull(view);

        Button add=(Button)view.findViewById(R.id.addNext);
        add.performClick();
        assertEquals(2,adapter.getCount());

        View view1=adapter.getView(1,null,null);
        Button del=(Button)view.findViewById(R.id.delete);
        del.performClick();
        assertEquals(1,adapter.getCount());
    }

    @Test
    public void checkSave(){
        InstructorQuiz activity=Robolectric.buildActivity(InstructorQuiz.class).create().get();
        Button save=(Button)activity.findViewById(R.id.save);
        save.performClick();
        assertEquals("Data Sent Successfully", ShadowToast.getTextOfLatestToast());
    }
}
