package announcements;

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
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.asus.klasseandroid.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class InstructorAnnounce extends AppCompatActivity {
    private int room_id;
    private static String HttpURL;
    private static String HttpURLsendnotif ;
    private static String HttpURLgetID;
    final ArrayList<String> ids=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instructor_announce);

        Intent intent = getIntent();
        room_id = intent.getIntExtra("id", 11);
        HttpURLgetID = "http://"+getResources().getString(R.string.ip)+"/Klasse/get_user_ids.php?class_id=";
        HttpURL="http://"+getResources().getString(R.string.ip)+"/Klasse/post_announcement.php";
        HttpURLsendnotif ="http://"+getResources().getString(R.string.ip)+"/Klasse/send_single_push.php";


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

    public void sentNotif(final String a)
    {


        RequestQueue requestQueue = Volley.newRequestQueue(InstructorAnnounce.this);

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET, HttpURLgetID+room_id,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

                        try{

                            for(int i=0;i<response.length();i++){
                                // Get current json object

                                JSONObject ann = response.getJSONObject(i);
                                ids.add(ann.getString("user_id"));


                            }

                            RequestQueue MyRequestQueue = Volley.newRequestQueue(InstructorAnnounce.this);
                            for(String i:ids) { //this iterates through all the ids
                                final String id=i; //sets current id = current element
                                //loops through everything

                                StringRequest stringRequest = new StringRequest(Request.Method.POST, HttpURLsendnotif, new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        Log.i("anwesha", "succesfully sent");
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
                                        params.put("title", "Announcement Posted");
                                        params.put("message", a);
                                        params.put("user_id", id);


                                        return params;
                                    }
                                };

                                MyRequestQueue.add(stringRequest);
                            }


                            ids.clear();
                        }catch (JSONException e){
                            e.printStackTrace();
                            Log.i("anwesha",e.getMessage().toString());
                        }

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
    public void postAnnouncement(final String name, final EditText e, final String a)
    {
        RequestQueue MyRequestQueue = Volley.newRequestQueue(InstructorAnnounce.this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, HttpURL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.contains("success")) {
                    Toast.makeText(getApplicationContext(), "Successfully posted!", Toast.LENGTH_SHORT).show();
                    e.setText("");
                    sentNotif(a);
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
