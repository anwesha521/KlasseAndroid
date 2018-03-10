package com.example.asus.klasseandroid;


import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.JsonArray;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class StudentAnnounce extends AppCompatActivity {

    DisplayAdapter disadpt;


    private ArrayList<String> content = new ArrayList<String>();
    private ArrayList<String> names=new ArrayList<>();
    private ArrayList<Integer> ids=new ArrayList<>();
    private ArrayList<Announcements> announce=new ArrayList<>();
    private int room_id;

    private ListView list;
    private static final String HttpURL = "http://192.168.1.185/Klasse/get_announcements.php";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_student_announce);
        Intent intent = getIntent();
        room_id = intent.getIntExtra("id", 11);
        Log.i("anwesha",room_id+"");
        list=findViewById(R.id.list_of_announcements);
        loadAnnouncements();
        for(String n:names)
            Log.i("anweshaname",n);
        disadpt = new DisplayAdapter(StudentAnnounce.this, content,names,ids);
        list.setAdapter(disadpt);

    }
    public void loadAnnouncements()
    {

        RequestQueue requestQueue = Volley.newRequestQueue(StudentAnnounce.this);
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

                                announce.add(new Announcements(
                                        ann.getString("professor"),
                                        ann.getString("content"),
                                        ann.getInt("class"),
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
                       Log.i("anwesha",error.getMessage());
                    }
                }
        );
        requestQueue.add(jsonArrayRequest);

    }

    public void populate()
    {

        for(Announcements a:announce)
        {
            if(a.getClassnum()==room_id)
            {
                content.add(a.getContent());
                names.add(a.getProf());
                ids.add(a.getId());
                Log.i("anweshaa",a.getContent());

            }
            else
                Log.i("anweshaclass",a.getClassnum()+"");

        }
        disadpt = new DisplayAdapter(StudentAnnounce.this, content,names,ids);
        list.setAdapter(disadpt);
    }

}
