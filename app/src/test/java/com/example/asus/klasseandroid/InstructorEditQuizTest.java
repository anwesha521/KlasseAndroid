package com.example.asus.klasseandroid;

import android.content.Intent;
import android.os.Build;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.Shadows;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowListView;
import org.robolectric.shadows.ShadowToast;

import java.io.BufferedReader;

import static android.content.Context.MODE_PRIVATE;
import static org.bouncycastle.asn1.x500.style.RFC4519Style.l;
import static org.bouncycastle.asn1.x500.style.RFC4519Style.postalAddress;
import static org.junit.Assert.*;

/**
 * Created by wsq96 on 2018/4/6.
 */

@RunWith(RobolectricTestRunner.class)
@Config(sdk = Build.VERSION_CODES.JELLY_BEAN_MR2)

public class InstructorEditQuizTest {
    private InstructorEditQuiz activity;


    @Before
    public void setUp() throws Exception{
        Intent intent=new Intent();
        intent.putExtra("id",11);
        activity = Robolectric.buildActivity(InstructorEditQuiz.class,intent)
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
    public void listViewTextTest() throws Exception{
        ListView listView=(ListView)activity.findViewById(R.id.questionList);
        assertNotNull("listView doesn't exist",listView);

        ShadowListView shadowListView= Shadows.shadowOf(listView);
        shadowListView.populateItems();

        InstructorEditQuizAdapter adapter=(InstructorEditQuizAdapter) listView.getAdapter();
        try{
            if(adapter.getCount()>0){
                assertNotNull(adapter.getQl());
                for(int i=0;i<adapter.getQl().size();i++){
                    assertNotNull(adapter.getQl().get(i));
                }

                View view=adapter.getView(0,null,null);
                TextView textView=(TextView)view.findViewById(R.id.a);
                EditText editText=(EditText)view.findViewById(R.id.a_description);
                Spinner spinner=(Spinner)view.findViewById(R.id.type);
                Button button=(Button)view.findViewById(R.id.addNext);
                assertNotNull(textView);
                assertNotNull(editText);
                assertNotNull(spinner);
                assertNotNull(button);

                int size=adapter.getCount();
                button.performClick();
                assertEquals(size+1,adapter.getCount());
            }

        }catch (NullPointerException e){
            System.out.println("Adapter not initialized!");
        }
    }

    @Test
    public void checkSave(){
        ListView listView=(ListView)activity.findViewById(R.id.questionList);
        InstructorEditQuizAdapter adapter=(InstructorEditQuizAdapter)listView.getAdapter();

        InstructorQuiz activity=Robolectric.buildActivity(InstructorQuiz.class).create().get();
        Button save=(Button)activity.findViewById(R.id.save);
        save.performClick();

        try{
            if(adapter.getCount()>0){
                assertEquals("Data Sent Successfully", ShadowToast.getTextOfLatestToast());
            }
        }catch (NullPointerException e){
            System.out.println("Adapter not initiated!");
        }
    }
}
