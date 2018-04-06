package com.example.asus.klasseandroid;

import android.content.Intent;
import android.os.Build;
import android.widget.Button;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.Shadows;
import org.robolectric.annotation.Config;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by wsq96 on 2018/4/4.
 */

@RunWith(RobolectricTestRunner.class)
@Config(sdk = Build.VERSION_CODES.JELLY_BEAN_MR2)

public class QuizTest {
    private quiz activity;

    @Before
    public void setUp(){
        Intent intent=new Intent();
        intent.putExtra("id",11);
        activity= Robolectric.buildActivity(quiz.class,intent)
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
    public void checkClickManageButton(){
        quiz activity=Robolectric.buildActivity(quiz.class).create().get();
        Button manage=(Button) activity.findViewById(R.id.manage);
        manage.performClick();
        Intent intent= Shadows.shadowOf(activity).peekNextStartedActivity();
        assertEquals(ManageQuiz.class.getCanonicalName(),intent.getComponent().getClassName());
    }

    @Test
    public void checkClickCreateButton(){
        quiz activity=Robolectric.buildActivity(quiz.class).create().get();
        Button create=(Button) activity.findViewById(R.id.create);
        create.performClick();
        Intent intent= Shadows.shadowOf(activity).peekNextStartedActivity();
        assertEquals(InstructorQuiz.class.getCanonicalName(),intent.getComponent().getClassName());
    }
}
