package com.example.asus.klasseandroid;

import android.view.View;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.junit.Before;
import org.junit.Test;
import org.robolectric.Robolectric;
import org.robolectric.Shadows;
import org.robolectric.shadows.ShadowListView;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.*;

import com.example.asus.klasseandroid.StudentViewQuiz.*;

/**
 * Created by wsq96 on 2018/4/3.
 */

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
        assertEquals(equalTo("Quizzes"),text);
    }

    @Test
    public void checkActivityNotNull() throws Exception{
        assertNotNull(activity);
    }

    @Test
    public void listViewTextTest(){
        ListView listView=(ListView)activity.findViewById(R.id.quiz);
        assertNotNull("listView doesn't exist",listView);

        ShadowListView shadowListView= Shadows.shadowOf(listView);
        shadowListView.populateItems();

        StudentViewAdapter adapter=(StudentViewAdapter) listView.getAdapter();
        if(adapter.getCount()!=0){
            assertNotNull(adapter.viewQuizzes);
            assertNotNull(adapter.context);
            assertNotNull(adapter.statuses);
            //assertNotEquals();
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
