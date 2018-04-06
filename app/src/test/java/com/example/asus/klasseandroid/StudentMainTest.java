package com.example.asus.klasseandroid;

/**
 * Created by 1001737 on 4/4/18.
 */
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import static org.robolectric.Shadows.shadowOf;
import com.android.volley.Cache;
import com.android.volley.Header;
import com.android.volley.Network;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonRequest;
import com.google.firebase.FirebaseApp;

import org.json.JSONArray;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowActivity;
import org.robolectric.shadows.ShadowIntent;
import org.robolectric.shadows.ShadowToast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import chatroom.ChatMessage;
import chatroom.ChatRoom;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class)
public class StudentMainTest {

    private studentMain main;
    String type;
    String id;
    boolean CheckEditText;
    Button btnlogin = main.findViewById(R.id.signin);
    EditText email = main.findViewById(R.id.userId);
    EditText pw = main.findViewById(R.id.password);
    SharedPreferences pref;
    SharedPreferences prefName;
    SharedPreferences.Editor editor;
    SharedPreferences.Editor editorName;
    String t;
    String q ="";

    @Before
    public void setUp() throws Exception {
        main = Robolectric.buildActivity( studentMain.class )
                .create()
                .resume()
                .get();

        pref=main.getApplicationContext().getSharedPreferences("UserDetails", Context.MODE_PRIVATE);
        pref.edit().putString("id","1000004").commit();
        Intent intent=new Intent();



    }

    @Test
    public void checkActivityDefault() throws Exception{
        assertNotNull(main);

        Intent intent=main.getIntent();
        intent.putExtra("id",11);
        int id=intent.getIntExtra("id",11);
        assertEquals(11,id);
        assertEquals("1000004",pref.getString("id","1"));
    }


    /* testing volley with database */

    // testing volley request queue
    @Test
    public void compareTo() {
        int sequence = 0;
        TestRequest low = new TestRequest(Request.Priority.LOW);
        low.setSequence(sequence++);
        TestRequest low2 = new TestRequest(Request.Priority.LOW);
        low2.setSequence(sequence++);
        TestRequest high = new TestRequest(Request.Priority.HIGH);
        high.setSequence(sequence++);
        TestRequest immediate = new TestRequest(Request.Priority.IMMEDIATE);
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
        headers.put("week", "1");
        headers.put("percentage", "0.9");
        headers.put("class_id", "11");

        NetworkResponse resp = new NetworkResponse(200, null, headers, false);

        List<Header> expectedHeaders = new ArrayList<>();
        expectedHeaders.add(new Header("week", "1"));
        expectedHeaders.add(new Header("percentage", "0.9"));
        expectedHeaders.add(new Header("class_id", "11"));

        assertThat(expectedHeaders,
                containsInAnyOrder(resp.allHeaders.toArray(new Header[resp.allHeaders.size()])));
    }

    @Test
    public void listToMap() {
        List<Header> headers = new ArrayList<>();
        headers.add(new Header("week", "1"));
        // wrong value
        headers.add(new Header("percentage", "0.0"));
        headers.add(new Header("percentage", "0.9"));
        headers.add(new Header("class_id", "11"));


        NetworkResponse resp = new NetworkResponse(200, null, false, 0L, headers);

        Map<String, String> expectedHeaders = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
        expectedHeaders.put("week", "1");
        expectedHeaders.put("percentage", "0.9");
        expectedHeaders.put("class_id", "11");

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

    // test if volley request queue methods are not null
    @Test
    public void publicMethods() throws Exception {
        // for request queue
        assertNotNull(RequestQueue.class.getConstructor(Cache.class, Network.class, int.class));
        assertNotNull(RequestQueue.class.getConstructor(Cache.class, Network.class));

        assertNotNull(RequestQueue.class.getMethod("start"));
        assertNotNull(RequestQueue.class.getMethod("stop"));
        assertNotNull(RequestQueue.class.getMethod("getSequenceNumber"));
        assertNotNull(RequestQueue.class.getMethod("getCache"));
        assertNotNull(RequestQueue.class.getMethod("cancelAll", RequestQueue.RequestFilter.class));
        assertNotNull(RequestQueue.class.getMethod("cancelAll", Object.class));
        assertNotNull(RequestQueue.class.getMethod("add", Request.class));
        assertNotNull(RequestQueue.class.getDeclaredMethod("finish", Request.class));

        // for json array request
        assertNotNull(JsonRequest.class.getConstructor(String.class, String.class,
                Response.Listener.class, Response.ErrorListener.class));
        assertNotNull(JsonRequest.class.getConstructor(int.class, String.class, String.class,
                Response.Listener.class, Response.ErrorListener.class));

        assertNotNull(JsonArrayRequest.class.getConstructor(String.class,
                Response.Listener.class, Response.ErrorListener.class));
        assertNotNull(JsonArrayRequest.class.getConstructor(int.class, String.class, JSONArray.class,
                Response.Listener.class, Response.ErrorListener.class));

    }
}
