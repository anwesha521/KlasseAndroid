package com.example.asus.klasseandroid;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

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



import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;


public class Login extends AppCompatActivity {

    EditText email;
    EditText pw;
    String type;
    SharedPreferences pref;
    SharedPreferences.Editor ed;
    Boolean CheckEditText;
    String EmailHolder, PasswordHolder;
    String result="";


   // private static final String HttpURL = "http://192.168.1.185/Klasse/get_login_details.php";
   private static final String HttpURL = "http://10.12.195.1/Klasse/get_login_details.php";
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pref=getApplicationContext().getSharedPreferences("UserDetails",MODE_PRIVATE);
        ed=pref.edit();
        setContentView(R.layout.activity_student_login);
        Intent i = getIntent();
        Bundle b = i.getExtras();
        if (b != null) {
            type = b.getString("type");
        }

        Button login = findViewById(R.id.signin);
        email = findViewById(R.id.userId);
        pw = findViewById(R.id.password);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                CheckEditTextIsEmptyOrNot();

                if (CheckEditText) {

                    UserLoginFunction(EmailHolder, PasswordHolder);

                } else {

                    Toast.makeText(Login.this, "Please fill all form fields.", Toast.LENGTH_LONG).show();

                }

            }
        });
    }

    public void CheckEditTextIsEmptyOrNot() {

        EmailHolder = email.getText().toString();
        PasswordHolder = pw.getText().toString();

        if (TextUtils.isEmpty(EmailHolder) || TextUtils.isEmpty(PasswordHolder)) {
            CheckEditText = false;
        } else {

            CheckEditText = true;
        }
    }

    public void UserLoginFunction(final String email, final String password) {

        RequestQueue MyRequestQueue = Volley.newRequestQueue(Login.this);
        StringRequest MyStringRequest = new StringRequest(Request.Method.POST, HttpURL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Document doc = Jsoup.parse(response);
                result = doc.body().text();
                Log.i("anwesharesult",result);
                postSuccess(result);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //This code is executed if there is an error.
             Log.i("anwesha","error");
            }
        }) {
            protected Map<String, String> getParams() {
                Map<String, String> MyData = new HashMap<String, String>();
                Log.i("anweshalogin",email+password);
                MyData.put("user_id", email);
                MyData.put("password",password);
                return MyData;
            }
        };


        MyRequestQueue.add(MyStringRequest);



    }
    public void postSuccess(String r)
    {

        ed.putString("name",r);
        ed.commit();
        if (r.equalsIgnoreCase("Failure"))
        {

            Toast.makeText(Login.this, "Login failed, try again.", Toast.LENGTH_LONG).show();

        } else {

            Log.i("anwesha","successss");
            if(type.equalsIgnoreCase("student"))
            {
                Intent intent = new Intent(Login.this, studentMain.class);
                Login.this.startActivity(intent);}
            else
            {
                Intent intent = new Intent(Login.this, instructorMain.class);
                Login.this.startActivity(intent);
            }

        }
    }
}
