package com.example.asus.klasseandroid;

import android.content.Intent;
import android.os.Build;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.Shadows;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowListView;
import org.robolectric.shadows.ShadowToast;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.*;

/**
 * Created by wsq96 on 2018/4/4.
 */

@RunWith(RobolectricTestRunner.class)
@Config(sdk = Build.VERSION_CODES.JELLY_BEAN_MR2)

public class StudentQuizTest {
    private StudentQuiz activity;

    @Before
    public void setUp(){
        Intent intent=new Intent();
        intent.putExtra("id",11);
        activity= Robolectric.buildActivity(StudentQuiz.class,intent)
                .create()
                .resume()
                .get();

        activity.pref=activity.getApplicationContext().getSharedPreferences("UserDetails", MODE_PRIVATE);
        activity.pref.edit().putString("id","2000003").commit();
    }

    @Test
    public void checkActivityDefault(){
        assertNotNull(activity);
        Intent intent=activity.getIntent();
        assertNotEquals(0,activity.room_id);
        assertEquals(11,intent.getIntExtra("id",0));
        assertEquals("2000003",activity.pref.getString("id",""));
    }

    @Test
    public void checkListViewText(){
        ListView listView=(ListView)activity.findViewById(R.id.question_list);
        assertNotNull("listView doesn't exist",listView);

        ShadowListView shadowListView= Shadows.shadowOf(listView);
        shadowListView.populateItems();

        StudentQuizAdapter adapter=(StudentQuizAdapter) listView.getAdapter();
        if(listView.getChildCount()>0){
            assertNotNull(adapter.getAnswer());
            assertNotNull(adapter.getCorrect());
            assertNotNull(adapter.getMarks());
            assertNotNull(adapter.getType());
            assertNotEquals(0,adapter.getTotal());
            assertEquals(adapter.getAnswer().size(),adapter.getCorrect().size());
            assertEquals(adapter.getType().size(),adapter.getCorrect().size());
            assertEquals(adapter.getType().size(),adapter.getMarks().size());

            for(int i=0;i<adapter.getAnswer().size();i++){
                assertNotNull(adapter.getAnswer().get(i));
                assertNotNull(adapter.getCorrect().get(i));
                assertNotNull(adapter.getType().get(i));
                assertNotNull(adapter.getMarks().get(i));
            }
        }
    }

    @Test
    public void checkListViewNumber() throws Exception{
        ListView listView=(ListView)activity.findViewById(R.id.question_list);
        assertNotNull(listView);

        ShadowListView shadowListView=Shadows.shadowOf(listView);
        shadowListView.populateItems();

        StudentViewAdapter adapter=(StudentViewAdapter)listView.getAdapter();

        //assertNotEquals(0,listView.getChildCount());
    }

    @Test
    public void checkSubmit(){
        StudentQuiz activity=Robolectric.buildActivity(StudentQuiz.class).create().get();
        Button button=(Button)activity.findViewById(R.id.submit);
        button.performClick();
        assertThat(ShadowToast.getTextOfLatestToast(),equalTo("Submited Successfully"));
    }
}
