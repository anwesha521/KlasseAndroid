package com.example.asus.klasseandroid;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

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


public class WeekView  extends AppCompatActivity {
    public static String PDF_FETCH_URL;

    ArrayList<PDF> pdfList= new ArrayList<PDF>();

    //pdf adapter

    PdfAdapter pdfAdapter;
    private ListView listView;
    private int week;
    private int room_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.week_view);
        Intent intent = getIntent();
        week = intent.getIntExtra("week", 1);
        room_id=intent.getIntExtra("id", 11);
        listView = (ListView) findViewById(R.id.listViewPDF);

        PDF_FETCH_URL="http://"+getResources().getString(R.string.ip)+"/Klasse/getpdfs.php?week=";

        getPdfs();



    }

    private void getPdfs() {


        StringRequest stringRequest = new StringRequest(Request.Method.GET, PDF_FETCH_URL+week+"&class="+room_id,

                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject obj = new JSONObject(response);
                            Toast.makeText(WeekView.this,obj.getString("message"), Toast.LENGTH_SHORT).show();

                            JSONArray jsonArray = obj.getJSONArray("pdfs");

                            for(int i=0;i<jsonArray.length();i++){

                                //Declaring a json object corresponding to every pdf object in our json Array
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                //Declaring a Pdf object to add it to the ArrayList  pdfList
                                PDF pdf  = new PDF();
                                String pdfName = jsonObject.getString("name");
                                String pdfUrl = jsonObject.getString("url");
                                pdf.setName(pdfName);
                                pdf.setUrl(pdfUrl);

                                pdfList.add(pdf);

                            }

                            pdfAdapter=new PdfAdapter(WeekView.this,R.layout.pdf_list_layout, pdfList);

                            listView.setAdapter(pdfAdapter);

                            pdfAdapter.notifyDataSetChanged();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }
        );

        RequestQueue request = Volley.newRequestQueue(this);
        request.add(stringRequest);

    }

}
