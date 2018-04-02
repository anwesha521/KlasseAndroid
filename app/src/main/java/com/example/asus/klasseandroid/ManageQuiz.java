package com.example.asus.klasseandroid;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ManageQuiz extends AppCompatActivity {
    String url;
    ListView listView;
    ArrayList<Manage> Quizzes;
    int room_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_quiz);

        Intent intent=getIntent();
        room_id=intent.getIntExtra("id",11);
        Log.i("anweshaquiz",room_id+" ");
        url="http://"+getResources().getString(R.string.ip)+"/Klasse/get_quiz.php?class_id=";
        Quizzes=new ArrayList<>();

        listView=(ListView)findViewById(R.id.quiz_list);
        ManageReadData();
    }

    public void ManageReadData(){
        final RequestQueue requestQueue= Volley.newRequestQueue(ManageQuiz.this);
        StringRequest stringRequest=new StringRequest(Request.Method.GET, url+room_id,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray array=new JSONArray(response);
                            Map<Integer,Integer> weekMap=new HashMap<>();
                            for(int i=0;i<array.length();i++){
                                JSONObject week=array.getJSONObject(i);
                                int weekNumber=week.getInt("week");
                                if(!weekMap.containsKey(weekNumber)){
                                    weekMap.put(weekNumber,1);
                                }else {
                                    weekMap.put(weekNumber,weekMap.get(weekNumber)+1);
                                }
                            }
                            for(int w:weekMap.keySet()){
                                Quizzes.add(new Manage(w,weekMap.get(w)));
                            }
                            ManageQuizAdapter myAdapter=new ManageQuizAdapter(Quizzes,room_id,ManageQuiz.this);
                            listView.setAdapter(myAdapter);
                        }catch (JSONException e){
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                    }
                });
        requestQueue.add(stringRequest);
    }
}



