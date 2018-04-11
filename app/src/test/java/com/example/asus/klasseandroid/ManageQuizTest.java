package com.example.asus.klasseandroid;

import android.content.Intent;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
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
import static org.junit.Assert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

/**
 * Created by wsq96 on 2018/4/4.
 */

@RunWith(RobolectricTestRunner.class)
@Config(sdk = Build.VERSION_CODES.JELLY_BEAN_MR2)

public class ManageQuizTest {
    private ManageQuiz activity;


    @Before
    public void setUp() throws Exception{
        Intent intent=new Intent();
        intent.putExtra("id",11);
        activity = Robolectric.buildActivity(ManageQuiz.class,intent)
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
    public void checkFirstLine() throws Exception{
        TextView textView=(TextView)activity.findViewById(R.id.quizzes);
        assertEquals("Quizzes",textView.getText());
        assertEquals(26,Math.round(textView.getTextSize()));
    }

    @Test
    public void checklistViewText() throws Exception{
        ListView listView=(ListView)activity.findViewById(R.id.quiz_list);
        assertNotNull("listView doesn't exist",listView);

        ShadowListView shadowListView= Shadows.shadowOf(listView);
        shadowListView.populateItems();

        ManageQuizAdapter adapter=(ManageQuizAdapter) listView.getAdapter();
        assertNotNull(adapter);
        try{
            if(adapter.getCount()>0){
                assertNotNull(adapter.context);
                assertNotNull(adapter.quizzes);
                assertEquals(11,adapter.id);

                for(int i=0;i<adapter.quizzes.size();i++){
                    assertNotNull(adapter.quizzes.get(i));
                }
            }
        }catch (NullPointerException e){
            System.out.println("Adapter not initialized!");
        }
    }

    @Test
    public void checkListViewFunction() throws Exception{
        ListView listView=(ListView)activity.findViewById(R.id.quiz_list);
        ShadowListView shadowListView= Shadows.shadowOf(listView);
        shadowListView.populateItems();

        ManageQuizAdapter adapter=(ManageQuizAdapter) listView.getAdapter();
        if(adapter.getCount()>0){
            View view=adapter.getView(0,null,null);
            Button viewQuiz=(Button)view.findViewById(R.id.view);
            Button edit=(Button)view.findViewById(R.id.edit);

            viewQuiz.performClick();
            Intent intent = Shadows.shadowOf(activity).peekNextStartedActivity();
            assertEquals(InstructorViewQuiz.class.getCanonicalName(),intent.getComponent().getClassName());
        }
    }
}
