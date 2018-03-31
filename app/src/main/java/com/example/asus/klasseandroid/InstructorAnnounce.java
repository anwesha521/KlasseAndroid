package com.example.asus.klasseandroid;

import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

import static java.security.AccessController.getContext;

public class InstructorAnnounce extends AppCompatActivity {
    private int room_id;
    private static final String HttpURL = "http://192.168.1.185/Klasse/post_announcement.php";
    //private static final String HttpURL = "http://10.12.195.1/Klasse/post_announcement.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instructor_announce);

        Intent intent = getIntent();
        room_id = intent.getIntExtra("id", 11);
        SharedPreferences prefName = getApplicationContext().getSharedPreferences("UserDetails", MODE_PRIVATE);
        SharedPreferences.Editor editorName=prefName.edit();
        final String name=prefName.getString("name","Anonymous");
        final EditText e=findViewById(R.id.announcetext);

        Button b=findViewById(R.id.postannounce);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String a = e.getText().toString();
                if (TextUtils.isEmpty(a)) {
                    Toast.makeText(InstructorAnnounce.this, "Please enter message.", Toast.LENGTH_LONG).show();
                } else {
                    postAnnouncement(name, e, a);
                }
            }
        });


    }

    public void postAnnouncement(final String name, final EditText e, final String a)
    {
        RequestQueue MyRequestQueue = Volley.newRequestQueue(InstructorAnnounce.this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, HttpURL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.contains("success")) {
                    Toast.makeText(getApplicationContext(), "Successfully posted!", Toast.LENGTH_SHORT).show();
                    e.setText("");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Failed", Toast.LENGTH_SHORT).show();
                Log.i("anwesha", error.getMessage().toString());
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("professor", name);
                params.put("content",a);
                params.put("class", room_id + "");


                return params;
            }
        };

        MyRequestQueue.add(stringRequest);
    }
}
