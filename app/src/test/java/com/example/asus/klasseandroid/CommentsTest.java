package com.example.asus.klasseandroid;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import org.apache.tools.ant.types.Assertions;
import org.hamcrest.Matchers;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.support.v4.SupportFragmentTestUtil;

import java.util.List;

import javax.inject.Inject;

import feedback.Comments;
import feedback.FeedbackDisplayAdapter;
import feedback.FeedbackLayout;

import static java.util.Arrays.asList;
import static org.junit.Assert.*;
import static org.mockito.Mockito.verify;

/**
 * Created by harleen on 4/4/18.
 * can't really figure this out-
 * maybe using an espresso test base would work better
 */

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class)
public class CommentsTest {
    private Comments fragment;

//    // We could not inject a layoutManager as a dependency because it is an Android class and it has no public, zero-argument constructor.
//    // The solution here was to new one up in the real fragment in a method called getLayoutManager(), then subclass that fragment specifically for testing purposes
//    // and override getLayoutManager() to return a layout manager from a field on the object.
//    // we set that field to the mockLayoutManager seen below.
//    private LinearLayoutManager mockLayoutManager;
//
//    @Inject
//    FeedbackLayout mockBroadcastReceiver;
//
//    @Inject
//    FeedbackDisplayAdapter mockAdapter;
//
//    @Before
//    public void setUp() {
//        ((TestCommentsApplication) RuntimeEnvironment.application).inject(this);
//
//        //making a mock of the layout manager...
//        mockLayoutManager = Mockito.mock(LinearLayoutManager.class);
//        fragment = new Comments();
//
//        //setting it on our testable subclass...
//        fragment.setLayoutManager(mockLayoutManager);
//
//        //Start the fragment!
//        SupportFragmentTestUtil.startFragment(fragment);
//    }
//
//    @Test
//    public void defaultDisplay() {
//        RecyclerView recyclerView = (RecyclerView) fragment.getView().findViewById(R.id.recylcerView);
//        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
//
//        //assertThat(LayoutManager layoutManager) is provided to us by the assertj-android-recyclerview library.
//        assertThat(layoutManager).isEqualTo(mockLayoutManager);
//    }
//
//    @Test
//    public void onViewCreated_setsAdapterAndAttributes() {
//
//        //because we inject our mockAdapter into our fragment, we use a mock here to make sure that we give it
//        //everything it needs to function.
//        //it needs the list of candies in order to present them,
//        //and it needs a context so that, when we click on a candy, the adapter can use the context
//        //to launch a new activity that displays details about the candy.
//        verify(mockAdapter).setComments(Matchers.<List>any());
//        verify(mockAdapter).setContext(fragment);
//    }
//
//    //Now we make sure that the adapter is showing us our candies in the list.
//    @Test
//    public void onSuccess_populatesViewWithListOfComments() {
//        Comments comments = new Comments();
//        comments.setName("Chocolate Frogs");
//        comments.setDescription("Eat this delicious magical treat before it hops away!");
//        fragment.onSuccess(FeedbackLayout.Actions.GET_CANDIES, asList(comments));
//        FeedbackDisplayAdapter adapter = (FeedbackDisplayAdapter) fragment.getRecyclerView().getAdapter();
//        Assertions.assertThat(adapter.getItemCount()).isEqualTo(1);
//        Assertions.assertThat(adapter.getItemAtPosition(0)).isSameAs(comments);
//
//        Comments otherComments = new Comments();
//        fragment.onSuccess(mockBroadcastReceiver.Actions.GET_CANDIES, asList(otherComments));
//        Assertions.assertThat(adapter.getItemCount()).isEqualTo(1);
//        Assertions.assertThat(adapter.getItemAtPosition(0)).isSameAs(otherComments);
//
//    }
//
//    //Here is the subclass of CandiesFragment that we use for testing. //It overrides getLayoutManager to return a mock so we can assert on the mock.
//
//    public static class TestableCandiesFragment extends Comments {
//        private LinearLayoutManager mockLayoutManager;
//
//        public void setLayoutManager(LinearLayoutManager mockLayoutManager) {
//            this.mockLayoutManager = mockLayoutManager;
//        }
//
//        @Override
//        public LinearLayoutManager getLayoutManager() {
//            return mockLayoutManager;
//        }
//    }
//}


}