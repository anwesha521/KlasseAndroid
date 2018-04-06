package com.example.asus.klasseandroid;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.Shadows;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowActivity;
import org.robolectric.shadows.ShadowListView;
import org.robolectric.shadows.ShadowToast;

import static android.content.Context.MODE_PRIVATE;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.*;

import com.example.asus.klasseandroid.StudentViewQuiz.*;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import io.reactivex.Scheduler;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.schedulers.ExecutorScheduler;
import io.reactivex.plugins.RxJavaPlugins;

/**
 * Created by wsq96 on 2018/4/3.
 */

@RunWith(RobolectricTestRunner.class)
@Config(sdk = Build.VERSION_CODES.JELLY_BEAN_MR2)

public class StudentViewQuizTest {
    private StudentViewQuiz activity;


    @Before
    public void setUp() throws Exception{
        Intent intent=new Intent();
        intent.putExtra("id",11);
        activity = Robolectric.buildActivity(StudentViewQuiz.class,intent)
                .create()
                .resume()
                .get();

        activity.pref=activity.getApplicationContext().getSharedPreferences("UserDetails", MODE_PRIVATE);
        activity.pref.edit().putString("id","2000003").commit();
    }

    @Test
    public void firstLineTest() throws Exception{
        TextView textView=(TextView)activity.findViewById(R.id.first);

        String text=textView.getText().toString();
        assertEquals("Quizzes",text);
    }

    @Test
    public void checkActivityDefault() throws Exception{
        assertNotNull(activity);
        Intent intent=activity.getIntent();
        int id=intent.getIntExtra("id",1);
        assertEquals(11,id);
        assertEquals("2000003",activity.pref.getString("id","1"));
    }

    @Test
    public void listViewTextTest() throws Exception{
        ListView listView=(ListView)activity.findViewById(R.id.quiz);
        assertNotNull("listView doesn't exist",listView);

        ShadowListView shadowListView= Shadows.shadowOf(listView);
        shadowListView.populateItems();

        StudentViewAdapter adapter=(StudentViewAdapter) listView.getAdapter();
        if(adapter.getCount()>0){
            assertNotNull(adapter.viewQuizzes);
            assertNotNull(adapter.context);
            assertNotNull(adapter.statuses);
            assertNotEquals(0,adapter.room_id);
            assertEquals(adapter.statuses.size(),adapter.viewQuizzes.size());

            for(int i=0;i<adapter.viewQuizzes.size();i++){
                assertNotNull(adapter.viewQuizzes.get(i));
                assertNotNull(adapter.statuses.get(i));
            }
        }
    }

    @Test
    public void listViewClickTest() throws Exception{
        ListView listView=(ListView)activity.findViewById(R.id.quiz);
        assertNotNull(listView);

        ShadowListView shadowListView=Shadows.shadowOf(listView);
        shadowListView.populateItems();

        StudentViewAdapter adapter=(StudentViewAdapter)listView.getAdapter();

        //assertNotEquals(0,listView.getChildCount());
        if(adapter.getCount()>0){
            ArrayList<String> status=adapter.statuses;
            for(int i=0;i<status.size();i++){
                if(status.get(i).equals("Completed")){
                    shadowListView.performItemClick(i);
                    assertThat(ShadowToast.getTextOfLatestToast(),equalTo("You have already completed this quiz."));
                }else if(status.get(i).equals("")){
                    shadowListView.performItemClick(i);
                    Intent intent=Shadows.shadowOf(activity).peekNextStartedActivity();
                    assertEquals(StudentQuiz.class.getCanonicalName(),intent.getComponent().getClassName());
                }else {
                    fail();
                }
            }
        }
    }
}
