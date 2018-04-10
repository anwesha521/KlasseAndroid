package com.example.asus.klasseandroid;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import feedback.studentFeedback.FeedbackDisplayAdapter;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;

/**
 * Created by harleen on 6/4/18.
 */
@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 18)
public class FeedbackDisplayAdapterTest {

    private FeedbackDisplayAdapter feedbackDisplayAdapter;
//    private FeedbackDisplayAdapter.FeedbackViewHolder holder;
//    private View mockView;
//    private Fragment mockFragment;
//
//    @Before
//    public void setUp() throws Exception {
//        ((TestApplication) RuntimeEnvironment.application).inject(this);
//        adapter = new FeedbackDisplayAdapter();
//        mockView = mock(View.class);
//        mockFragment = mock(Fragment.class);
//
//        stub(mockFragment.getString(anyInt())).toReturn("Candy");
//    }
    @Test
    public void shouldNotBeNull() throws Exception
    {
        assertNotNull(feedbackDisplayAdapter );
    }

//
//    @Test
//    public void onCreateViewHolder() throws Exception {
//    }
//
//    @Test
//    public void onBindViewHolder() throws Exception {
//    }
//
//    @Test
//    public void getItemCount() throws Exception {
//    }

}