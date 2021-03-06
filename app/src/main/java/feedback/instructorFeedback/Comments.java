package feedback.instructorFeedback;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.asus.klasseandroid.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import feedback.studentFeedback.FeedbackDisplayAdapter;
import feedback.studentFeedback.FeedbackLayout;

/**
 * Created by harleen on 28/3/18.
 * This page displays all comments submitted
 * by the students!
 * It will display the name of the pdf, pg number
 * as well as the date and actual feedback
 */


public class Comments extends AppCompatActivity {


    //this is the JSON Data URL
    private static String URL;

    //a list to store all the products
    List<FeedbackLayout> feedbackList;

    //the recyclerview
    RecyclerView recyclerView;

    //room id

    private int room_id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setting the room id stuff
        Intent intent = getIntent();
        room_id=intent.getIntExtra("id", 11);

        setContentView(R.layout.activity_comments);
        URL = "http://"+getResources().getString(R.string.ip)+"/Klasse/api.php";
        //getting the recyclerview from xml
        recyclerView = findViewById(R.id.recylcerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        DividerItemDecoration itemDecor = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(itemDecor);
        //initializing the productlist
        feedbackList = new ArrayList<>();

        //this method will fetch and parse json
        //to display it in recyclerview
        loadFeedback();

    }

    private void loadFeedback() {

        /*
        * Creating a String Request
        * The request type is GET defined by first parameter
        * The URL is defined in the second parameter
        * Then we have a Response Listener and a Error Listener
        * In response listener we will get the JSON response as a String
        * */
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL+"?class="+room_id,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            //converting the string to json array object
                            JSONArray array = new JSONArray(response);

                            //traversing through all the object
                            for (int i = 0; i < array.length(); i++) {

                                //getting product object from json array
                                JSONObject feedback = array.getJSONObject(i);

                                //adding the product to feedback list
                                feedbackList.add(new FeedbackLayout(
                                        feedback.getString("feedback"),
                                        feedback.getString("pdfFileName").trim(),
                                        " pg " + feedback.getString("pgNumber"),
                                        feedback.getString("time")
                                ));
                                Log.i("anwesha",feedback.getString("feedback"));
                            }

                            //creating adapter object and setting it to recyclerview
                            FeedbackDisplayAdapter adapter = new FeedbackDisplayAdapter(Comments.this, feedbackList);
                            recyclerView.setAdapter(adapter);
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.i("anwesha","enterederror"+e.getMessage()+" ");
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.i("anwehsha",error.getMessage()+"eerer");
                    }
                });

        //adding our stringrequest to queue
        Volley.newRequestQueue(this).add(stringRequest);
    }
}