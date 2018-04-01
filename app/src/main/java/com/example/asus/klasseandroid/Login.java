package com.example.asus.klasseandroid;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.StrictMode;
import android.provider.DocumentsContract;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
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

import org.json.JSONException;
import org.json.JSONObject;
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


    private static String HttpURL;
    private static String URL_REGISTER_DEVICE;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pref=getApplicationContext().getSharedPreferences("UserDetails",MODE_PRIVATE);
        ed=pref.edit();
        setContentView(R.layout.activity_student_login);


        HttpURL = "http://"+getResources().getString(R.string.ip)+"/Klasse/get_login_details.php";
        URL_REGISTER_DEVICE = "http://"+getResources().getString(R.string.ip)+"/Klasse/register_device.php";


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

    public void register()
    {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_REGISTER_DEVICE,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Document doc = Jsoup.parse(response);
                        result = doc.body().text();
                        Toast.makeText(Login.this, result, Toast.LENGTH_LONG).show();

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Login.this, error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }) {

            @Override
            protected Map<String, String> getParams()  {
                Map<String, String> params = new HashMap<>();
                params.put("token", pref.getString("token","0"));
                params.put("user_id", pref.getString("id","0"));
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
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
                ed.putString("id",email);
                ed.commit();
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


        if (r.equalsIgnoreCase("Failure"))
        {

            Toast.makeText(Login.this, "Login failed, try again.", Toast.LENGTH_LONG).show();

        } else {
            String[] vals=r.split(",");

            ed.putString("name",vals[0]);
            ed.commit();
            register();
            Log.i("anwesha",vals[0]+","+vals[1]+" TESTTYPE ");
            if(type.equalsIgnoreCase("student")&& vals[1].equalsIgnoreCase("student"))
            {
                Intent intent = new Intent(Login.this, studentMain.class);
                Login.this.startActivity(intent);}
            else  if(type.equalsIgnoreCase("instructor")&& vals[1].equalsIgnoreCase("instructor"))
            {
                Intent intent = new Intent(Login.this, instructorMain.class);
                Login.this.startActivity(intent);
            }
            else
                Toast.makeText(Login.this, "Go back and select valid user type.", Toast.LENGTH_LONG).show();

        }
    }
}