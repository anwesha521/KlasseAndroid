package com.example.asus.klasseandroid;

import android.content.SharedPreferences;
import android.widget.Button;

import com.android.volley.Header;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.google.firebase.FirebaseApp;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.Shadows;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowListView;
import org.robolectric.shadows.ShadowLog;
import org.robolectric.shadows.ShadowToast;
import android.test.suitebuilder.annotation.SmallTest;
import android.widget.ListView;

import com.android.volley.Request.Priority;

import junit.framework.TestCase;
import com.android.volley.Request.Priority;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import static org.hamcrest.Matchers.containsInAnyOrder;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import announcements.Announcements;
import announcements.InstructorAnnounce;
import announcements.InstructorViewAnnounce;
import announcements.StudentAnnounce;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

/**
 * Created by 1001737 on 4/4/18.
 */

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class)
public class InstructorViewAnnounceTest {
    private Announcements a;
    private InstructorViewAnnounce announce;
    private ArrayList<String> content = new ArrayList<String>();
    private ArrayList<String> names = new ArrayList<>();
    private ArrayList<Integer> ids = new ArrayList<>();
    //private ArrayList<Announcements> announce=new ArrayList<>();
    int type = 0;
    String id;
    //final EditText e=announce.findViewById(R.id.announcetext);
    SharedPreferences pref;
    SharedPreferences prefName;
    SharedPreferences.Editor editor;
    SharedPreferences.Editor editorName;
    String t;
    String q = "";
    private ListView lstView;


    @Before
    public void setUp() throws Exception {
        announce = Robolectric.buildActivity(InstructorViewAnnounce.class)
                .create()
                .resume()
                .get();
        lstView=(ListView)announce.findViewById(R.id.list_of_announcements);


    }

    @Test
    public void shouldNotBeNull() throws Exception {
        assertNotNull(announce);
    }
/*
    @Test
    public void shouldFindListView()throws Exception{
        assertNotNull("ListView not found ", lstView);
        ShadowListView shadowListView = Shadows.shadowOf(lstView); //we need to shadow the list view

        shadowListView.populateItems();// will populate the adapter
        ShadowLog.d("Checking the first country name in adapter " ,
                (lstView.getAdapter().getItem(0)).getCountry());

        assertTrue("Country Japan doesnt exist", "Japan".equals(((RowModel) lstView.getAdapter().getItem(0)).getCountry()));
        assertTrue(3==lstView.getChildCount());
    }
*/
    /* testing volley with database */

    // testing volley request queue
    @Test
    public void compareTo() {
        int sequence = 0;
        TestRequest low = new TestRequest(Priority.LOW);
        low.setSequence(sequence++);
        TestRequest low2 = new TestRequest(Priority.LOW);
        low2.setSequence(sequence++);
        TestRequest high = new TestRequest(Priority.HIGH);
        high.setSequence(sequence++);
        TestRequest immediate = new TestRequest(Priority.IMMEDIATE);
        immediate.setSequence(sequence++);

        assertTrue(low.compareTo(high) > 0);
        assertTrue(high.compareTo(low) < 0);
        assertTrue(low.compareTo(low2) < 0);
        assertTrue(low.compareTo(immediate) > 0);
        assertTrue(immediate.compareTo(high) < 0);
    }
    // test volley request priority
    private class TestRequest extends Request<Object> {
        private Priority mPriority = Priority.NORMAL;

        public TestRequest(Priority priority) {
            super(Request.Method.GET, "", null);
            mPriority = priority;
        }

        @Override
        public Priority getPriority() {
            return mPriority;
        }

        @Override
        protected void deliverResponse(Object response) {
        }

        @Override
        protected Response<Object> parseNetworkResponse(NetworkResponse response) {
            return null;
        }
    }
    // test if url is able to be parsed
    @Test
    public void testUrlParsing() {
        UrlParseRequest nullUrl = new UrlParseRequest(null);
        assertEquals(0, nullUrl.getTrafficStatsTag());
        UrlParseRequest emptyUrl = new UrlParseRequest("");
        assertEquals(0, emptyUrl.getTrafficStatsTag());
        UrlParseRequest noHost = new UrlParseRequest("http:///");
        assertEquals(0, noHost.getTrafficStatsTag());
        UrlParseRequest badProtocol = new UrlParseRequest("bad:http://foo");
        assertEquals(0, badProtocol.getTrafficStatsTag());
        UrlParseRequest goodProtocol = new UrlParseRequest("http://foo");
        assertFalse(0 == goodProtocol.getTrafficStatsTag());
    }

    private class UrlParseRequest extends Request<Object> {
        public UrlParseRequest(String url) {
            super(Request.Method.GET, url, null);
        }

        @Override
        protected void deliverResponse(Object response) {
        }

        @Override
        protected Response<Object> parseNetworkResponse(NetworkResponse response) {
            return null;
        }

    }
    // testing network response queue
    @SuppressWarnings("deprecation")
    @Test
    public void mapToList() {
        Map<String, String> headers = new HashMap<>();
        headers.put("professor", "Sudipta");
        headers.put("content", "Hello class");
        headers.put("class", "11");
        headers.put("pid", "1");

        NetworkResponse resp = new NetworkResponse(200, null, headers, false);

        List<Header> expectedHeaders = new ArrayList<>();
        expectedHeaders.add(new Header("professor", "Sudipta"));
        expectedHeaders.add(new Header("content", "Hello class"));
        expectedHeaders.add(new Header("class", "11"));
        expectedHeaders.add(new Header("pid", "1"));

        assertThat(expectedHeaders,
                containsInAnyOrder(resp.allHeaders.toArray(new Header[resp.allHeaders.size()])));
    }

    @Test
    public void listToMap() {
        List<Header> headers = new ArrayList<>();
        headers.add(new Header("professor", "Sudipta"));
        // wrong value
        headers.add(new Header("content", "Sorry wrong value"));
        headers.add(new Header("content", "Hello class"));
        headers.add(new Header("class", "11"));
        headers.add(new Header("pid", "1"));

        NetworkResponse resp = new NetworkResponse(200, null, false, 0L, headers);

        Map<String, String> expectedHeaders = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
        expectedHeaders.put("professor", "Sudipta");
        expectedHeaders.put("content", "Hello class");
        expectedHeaders.put("class", "11");
        expectedHeaders.put("pid", "1");

        assertEquals(expectedHeaders, resp.headers);
    }
    // check if null does not crash volley
    @SuppressWarnings("deprecation")
    @Test
    public void nullValuesDontCrash() {
        new NetworkResponse(null);
        new NetworkResponse(null, null);
        new NetworkResponse(200, null, null, false);
        new NetworkResponse(200, null, null, false, 0L);
        new NetworkResponse(200, null, false, 0L, null);
    }
}
