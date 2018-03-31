package com.example.asus.klasseandroid;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;

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

public class InstructorViewAnnounce extends AppCompatActivity {
    DisplayAdapaterInstructorAnnounce disadpt;


    private ArrayList<String> content = new ArrayList<String>();
    private ArrayList<String> names=new ArrayList<>();
    private ArrayList<Integer> ids=new ArrayList<>();
    private ArrayList<Announcements> announce=new ArrayList<>();
    private int room_id;
    //private static final String HttpURL = "http://192.168.1.185/Klasse/get_announcements.php";
   private static final String HttpURL = "http://10.12.195.1/Klasse/get_announcements.php";
    private String name;
    SharedPreferences prefName;
    SharedPreferences.Editor editorName;

    private ListView list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        prefName=getApplicationContext().getSharedPreferences("UserDetails",MODE_PRIVATE);
        editorName=prefName.edit();
        setContentView(R.layout.instructor_view_announce);
        Intent intent = getIntent();
        room_id = intent.getIntExtra("id", 11);
        TextView head=findViewById(R.id.instruct_view_header);
        switch(room_id)
        {
            case 11:
                head.setText("Your announcements for Elements of Software Construction");
                break;
            case 21:
                head.setText("Your announcements for Computer Systems Engineering");
                break;
            case 31:
                head.setText("Your announcements for Probability and Statistics");
                break;
            default:
                head.setText("Uhhh");


        }
        list=findViewById(R.id.list_of_announcements);
        name=prefName.getString("name","Anonymous");
        loadAnnouncements();
        disadpt = new DisplayAdapaterInstructorAnnounce(InstructorViewAnnounce.this, content,names,ids);
        list.setAdapter(disadpt);
    }
    public void loadAnnouncements()
    {

        RequestQueue requestQueue = Volley.newRequestQueue(InstructorViewAnnounce.this);
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
                                Log.i("anwesha",name+","+ann.getString("professor"));
                                if(name.equalsIgnoreCase(ann.getString("professor")))
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
                Log.i("anweshaclass","not entered"+a.getClassnum()+"");

        }
        disadpt = new DisplayAdapaterInstructorAnnounce(InstructorViewAnnounce.this, content,names,ids);
        list.setAdapter(disadpt);
    }
}
