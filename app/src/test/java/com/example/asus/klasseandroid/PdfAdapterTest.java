package com.example.asus.klasseandroid;

import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import feedback.studentFeedback.PdfAdapter;

/**
 * Created by harleen on 4/4/18.
 */
@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class)
public class PdfAdapterTest {
    PdfAdapter pdfAdapter;

//    @Test
//    public void testDialogPopup() throws Exception {
//
//        Button button = (Button) pdfAdapter.findViewById(R.id.pdffeedback);
//
//        button.performClick();
//        onView(withId(R.id.dialog_rl)).check(matches(allOf(withText(myDialogText), isDisplayed()));
//
//
//
////        EditText input = (EditText) pdfAdapter.findViewById(R.id.input);
////        String txt=input.getText().toString();
////        chatMessage = new ChatMessage(txt, "Harleen", "question", 11, id);
////
////        assertEquals(chatMessage.getMessageType(),"question");
//    }
//
//    @Test
//    public void testCancelButton() throws Exception{
//        Button button = (Button) pdfAdapter.findViewById(R.id.cancel_button);
//        onView(withId(android.R.id.button2)).perform(click());
//
//    }
//    @Test
//    public void testButtonClickShouldShowToast() throws Exception {
//        PdfAdapter activity = Robolectric.buildActivity(PdfAdapter.class).create().get();
//        Button view = (Button) activity.findViewById(R.id.dialog_negative_btn);
//        assertNotNull(view);
//        view.performClick();
//        assertThat(ShadowToast.getTextOfLatestToast(), equalTo("No button clicked") );
//    }
//    @Test
//    public void testDialogSubmitButtonIfNoInput() throws Exception {
//        Button submit = Button) pdfAdapter.findViewById(R.id.dialog_positive_btn);
//        assertNotNull(submit);
//        submit.performClick();
//        String txt = "";
//        assertEquals((String)ShadowToast.getTextOfLatestToast(),"Feedback empty!");
//    }

}