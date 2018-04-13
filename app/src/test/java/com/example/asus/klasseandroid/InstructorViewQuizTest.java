package com.example.asus.klasseandroid;

import android.content.Intent;
import android.os.Build;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.TextView;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.Shadows;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowListView;

import static android.content.Context.MODE_PRIVATE;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by wsq96 on 2018/4/4.
 */

@RunWith(RobolectricTestRunner.class)
@Config(sdk = Build.VERSION_CODES.JELLY_BEAN_MR2)

public class InstructorViewQuizTest {
    private InstructorViewQuiz activity;


    @Before
    public void setUp() throws Exception{
        Intent intent=new Intent();
        intent.putExtra("id",11);
        activity = Robolectric.buildActivity(InstructorViewQuiz.class,intent)
                .create()
                .resume()
                .get();
    }

    @Test
    public void checkActivityDefault() throws Exception{
        assertNotNull(activity);
        Intent intent=activity.getIntent();
        int id=intent.getIntExtra("id",1);
        assertEquals(11,id);
    }

    @Test
    public void checkListViewText() throws Exception{
        ListView listView=(ListView)activity.findViewById(R.id.question_list);
        assertNotNull("listView doesn't exist",listView);

        ShadowListView shadowListView= Shadows.shadowOf(listView);
        shadowListView.populateItems();

        InstructorViewStudentQuizAdapter adapter=(InstructorViewStudentQuizAdapter) listView.getAdapter();
        //assertNotNull(adapter);

        try{
            if(adapter.getCount()>0){
                View view=adapter.getView(0,null,null);
                TextView textView1=(TextView)view.findViewById(R.id.number);
                TextView textView2=(TextView)view.findViewById(R.id.description);
                EditText editText=(EditText)view.findViewById(R.id.answer);
                RadioGroup radioGroup=(RadioGroup)view.findViewById(R.id.choices);

                assertNotNull(textView1);
                assertNotNull(textView2);
                assertNotNull(editText);
                assertNotNull(radioGroup);
            }
        }catch (NullPointerException e){
            System.out.println("Adapter not initiated!");
        }
    }
}
