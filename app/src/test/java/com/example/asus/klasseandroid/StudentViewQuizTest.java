package com.example.asus.klasseandroid;

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
import org.robolectric.shadows.ShadowListView;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.*;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotEquals;

import com.example.asus.klasseandroid.StudentViewQuiz.*;

import java.util.concurrent.TimeUnit;

import io.reactivex.Scheduler;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.schedulers.ExecutorScheduler;
import io.reactivex.plugins.RxJavaPlugins;

/**
 * Created by wsq96 on 2018/4/3.
 */

@RunWith(RobolectricTestRunner.class)
//@Config(constants = BuildConfig.class)
@Config(sdk = Build.VERSION_CODES.JELLY_BEAN_MR2)

public class StudentViewQuizTest {
    private StudentViewQuiz activity;


    @Before
    public void setUp() throws Exception{
        activity = Robolectric.buildActivity(StudentViewQuiz.class)
                .create()
                .resume()
                .get();
    }

    @Test
    public void firstLineTest() throws Exception{
        TextView textView=(TextView)activity.findViewById(R.id.first);

        String text=textView.getText().toString();
        assertEquals("Quizzes",text);
    }

    @Test
    public void checkActivityNotNull() throws Exception{
        assertNotNull(activity);
    }

    @Test
    public void listViewTextTest() throws Exception{
        ListView listView=(ListView)activity.findViewById(R.id.quiz);
        assertNotNull("listView doesn't exist",listView);

        ShadowListView shadowListView= Shadows.shadowOf(listView);
        shadowListView.populateItems();

        StudentViewAdapter adapter=(StudentViewAdapter) listView.getAdapter();
        if(listView.getChildCount()>0){
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



    public View getViewByPosition(int pos, ListView listView) {
        final int firstListItemPosition = listView.getFirstVisiblePosition();
        final int lastListItemPosition = firstListItemPosition + listView.getChildCount() - 1;

        if (pos < firstListItemPosition || pos > lastListItemPosition ) {
            return listView.getAdapter().getView(pos, null, listView);
        } else {
            final int childIndex = pos - firstListItemPosition;
            return listView.getChildAt(childIndex);
        }
    }
}
