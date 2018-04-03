package com.example.asus.klasseandroid;

import android.widget.Button;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import feedback.PDF;
import feedback.PdfAdapter;
import feedback.WeekView;

import static org.junit.Assert.*;

/**
 * Created by harleen on 4/4/18.
 */
@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class)
public class PdfAdapterTest {
    PdfAdapter pdfAdapter

    @Test
    public void testSubmitFeedback() throws Exception {

        Button button = (Button) pdfAdapter.findViewById(R.id.pdffeedback);

        button.performClick();
        onView(withId(R.id.myDialogTextId)).check(matches(allOf(withText(myDialogText), isDisplayed()));



//        EditText input = (EditText) pdfAdapter.findViewById(R.id.input);
//        String txt=input.getText().toString();
//        chatMessage = new ChatMessage(txt, "Harleen", "question", 11, id);
//
//        assertEquals(chatMessage.getMessageType(),"question");
    }

}