package com.example.asus.klasseandroid;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Comments extends AppCompatActivity {

    FeedbackDisplayAdapter disadpt;


    private ArrayList<String> content = new ArrayList<String>();
    private ArrayList<String> pdfFileNames=new ArrayList<>();
    private ArrayList<Integer> ids=new ArrayList<>();
    private ArrayList<FeedbackLayout> feedback=new ArrayList<>();

    private ListView list;
    private static final String HttpURL = "http://192.168.0.121/get_feedback.php";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_comments);
        Intent intent = getIntent();
        list=findViewById(R.id.comments);
        loadFeedback();
        disadpt = new FeedbackDisplayAdapter(this,content,pdfFileNames,ids); //DisplayAdapter(this, content,pdfFileNames,ids);
        list.setAdapter(disadpt);

    }
    public void loadFeedback()
    {

        RequestQueue requestQueue = Volley.newRequestQueue(Comments.this);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET,
                HttpURL,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {


                        try{

                            for(int i=0;i<response.length();i++){
                                // Get current json object
                                JSONObject ann = response.getJSONObject(i);

                                feedback.add(new FeedbackLayout(
                                        ann.getString("feedback"),
                                        ann.getString("pdfFileName"),
                                        ann.getInt("pid")
                                ));
                            }
                        }catch (JSONException e){
                            e.printStackTrace();
                            Log.i("anwesha",e.getMessage().toString());
                        }
                        populate();
                    }
                },
                new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error){
                        // Do something when error occurred
                        Log.i("help",error.getMessage());
                    }
                }
        );
        requestQueue.add(jsonArrayRequest);

    }

    public void populate()
    {

        for(FeedbackLayout a:feedback)
        {
            content.add(a.getFeedback());
            pdfFileNames.add(a.getPdfFileName());
            ids.add(a.getId());
            Log.i("helpus",a.getFeedback());

            }

        disadpt = new FeedbackDisplayAdapter(this, content,pdfFileNames,ids);
        list.setAdapter(disadpt);
    }


}
