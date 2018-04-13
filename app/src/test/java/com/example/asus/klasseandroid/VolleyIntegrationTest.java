package com.example.asus.klasseandroid;

/**
 * Created by 1001737 on 4/4/18.
 */

import com.android.volley.Network;
import com.android.volley.NetworkResponse;
import com.android.volley.Request.Priority;
import com.android.volley.RequestQueue;
import com.android.volley.RequestQueue.RequestFinishedListener;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.NoCache;


import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowSystemClock;

import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.timeout;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;


// Doing Integration test for Volley which is used in all classes except chatroom



//@Config(manifest = "app/src/main/AndroidManifest.xml", sdk = 18)
@RunWith(RobolectricTestRunner.class)
@Config(shadows = {ShadowSystemClock.class})
public class VolleyIntegrationTest {

    private ResponseDelivery mDelivery;
    @Mock private Network mMockNetwork;
    @Mock private RequestFinishedListener<byte[]> mMockListener;
    @Mock private RequestFinishedListener<byte[]> mMockListener2;

    @Before public void setUp() throws Exception {
        mDelivery = new ResponseDelivery();
        initMocks(this);
    }

    @Test public void test_CacheWithDifferentPriority_Processing() throws Exception {

        /*
        See if request is processed correctly for different priority cache (high priority is 20ms)

        Test if 2nd request ONLY HANDLED AFTER 1st
         */
        MockRequest lowerPriorityReq = new MockRequest();
        MockRequest higherPriorityReq = new MockRequest();
        lowerPriorityReq.setCacheKey("1");
        higherPriorityReq.setCacheKey("2");
        lowerPriorityReq.setPriority(Priority.LOW);
        higherPriorityReq.setPriority(Priority.HIGH);

        Answer<NetworkResponse> delayAnswer = new Answer<NetworkResponse>() {
            @Override
            public NetworkResponse answer(InvocationOnMock invocationOnMock) throws Throwable {
                Thread.sleep(20);
                return mock(NetworkResponse.class);
            }
        };
        // for higher priority request
        when(mMockNetwork.performRequest(higherPriorityReq)).thenAnswer(delayAnswer);
        when(mMockNetwork.performRequest(lowerPriorityReq)).thenReturn(mock(NetworkResponse.class));

        RequestQueue queue = new RequestQueue(new NoCache(), mMockNetwork, 1, mDelivery);
        queue.addRequestFinishedListener(mMockListener);
        queue.add(lowerPriorityReq);
        queue.add(higherPriorityReq);
        queue.start();

        InOrder inOrder = inOrder(mMockListener);
        // higher priority req enters first
        inOrder.verify(mMockListener, timeout(10000)).onRequestFinished(higherPriorityReq);
        // lower priority req enters next
        inOrder.verify(mMockListener, timeout(10000)).onRequestFinished(lowerPriorityReq);

        queue.stop();
    }

    // same priority processed in order
    @Test public void test_CacheWithSamePriority_Processing() throws Exception {
      //Test if 2nd request ONLY HANDLED AFTER 1st
        MockRequest req1 = new MockRequest();
        MockRequest req2 = new MockRequest();
        Answer<NetworkResponse> delayAnswer = new Answer<NetworkResponse>() {
            @Override
            public NetworkResponse answer(InvocationOnMock invocationOnMock) throws Throwable {
                Thread.sleep(20);
                return mock(NetworkResponse.class);
            }
        };
        // for first request
        when(mMockNetwork.performRequest(req1)).thenAnswer(delayAnswer);
        when(mMockNetwork.performRequest(req2)).thenReturn(mock(NetworkResponse.class));

        RequestQueue queue = new RequestQueue(new NoCache(), mMockNetwork, 3, mDelivery);
        queue.addRequestFinishedListener(mMockListener);
        queue.add(req1);
        queue.add(req2);
        queue.start();

        InOrder inOrder = inOrder(mMockListener);
        // req1 enters first
        inOrder.verify(mMockListener, timeout(10000)).onRequestFinished(req1);
        // req2 enters next
        inOrder.verify(mMockListener, timeout(10000)).onRequestFinished(req2);

        queue.stop();
    }

    // Test if RequestFinishedListeners are informed when requests are canceled
    @Test public void test_requestCancelled() throws Exception {
        MockRequest request = new MockRequest();
        Answer<NetworkResponse> delayAnswer = new Answer<NetworkResponse>() {
            @Override
            public NetworkResponse answer(InvocationOnMock invocationOnMock) throws Throwable {
                Thread.sleep(200);
                return mock(NetworkResponse.class);
            }
        };
        RequestQueue queue = new RequestQueue(new NoCache(), mMockNetwork, 1, mDelivery);

        when(mMockNetwork.performRequest(request)).thenAnswer(delayAnswer);

        queue.addRequestFinishedListener(mMockListener);
        queue.start();
        queue.add(request);

        request.cancel();
        verify(mMockListener, timeout(10000)).onRequestFinished(request);
        queue.stop();
    }

    // Test if RequestFinishedListeners are informed when requests are successfully delivered
    @Test public void test_requestDelivered() throws Exception {
        MockRequest request = new MockRequest();
        RequestQueue queue = new RequestQueue(new NoCache(), mMockNetwork, 1, mDelivery);

        queue.addRequestFinishedListener(mMockListener);
        queue.addRequestFinishedListener(mMockListener2);
        queue.start();
        queue.add(request);

        verify(mMockListener, timeout(10000)).onRequestFinished(request);
        verify(mMockListener2, timeout(10000)).onRequestFinished(request);

        queue.stop();
    }

    // Test if Volley error correctly invoked if error occurs
    @Test public void atest_requestError() throws Exception {
        MockRequest request = new MockRequest();
        RequestQueue queue = new RequestQueue(new NoCache(), mMockNetwork, 1, mDelivery);

        when(mMockNetwork.performRequest(request)).thenThrow(new VolleyError());

        queue.addRequestFinishedListener(mMockListener);
        queue.start();
        queue.add(request);

        verify(mMockListener, timeout(10000)).onRequestFinished(request);
        queue.stop();
    }
}
