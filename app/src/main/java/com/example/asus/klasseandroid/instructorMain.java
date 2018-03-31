package com.example.asus.klasseandroid;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class instructorMain extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private BarChart mChart;
    ArrayList<BarEntry> BARENTRY ;
    ArrayList<String> BarEntryLabels ;
    BarDataSet Bardataset ;
    BarData BARDATA ;
    SharedPreferences pref;
    SharedPreferences.Editor ed;
    String HTTPUrl = "http://10.12.195.1/Klasse/instructor_get_grades.php?instructor_id=";
    ArrayList<StudentAnalytics> sa=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instructor_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mChart = (BarChart) findViewById(R.id.barchartinstruct);
        BARENTRY = new ArrayList<>();

        BarEntryLabels = new ArrayList<String>();
        pref=getApplicationContext().getSharedPreferences("UserDetails",MODE_PRIVATE);
        ed=pref.edit();
        String id=pref.getString("id","1000000");
        HTTPUrl=HTTPUrl+id;
        request();

        Bardataset = new BarDataSet(BARENTRY, "Average scores");

        BARDATA = new BarData(BarEntryLabels, Bardataset);

        Bardataset.setColors(ColorTemplate.COLORFUL_COLORS);
        mChart.setData(BARDATA);

        mChart.animateX(4000);
        mChart.setDragEnabled(true);
        mChart.setScaleEnabled(true);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    public void request()
    {
        RequestQueue requestQueue = Volley.newRequestQueue(instructorMain.this);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET,
                HTTPUrl,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {


                        try{

                            for(int i=0;i<response.length();i++){
                                // Get current json object
                                JSONObject ann = response.getJSONObject(i);
                                sa.add(new StudentAnalytics(ann.getInt("week"),ann.getInt("percentage"),ann.getInt("class_id")));

                            }
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


   /* public void AddValuesToBARENTRY(){

        BARENTRY.add(new BarEntry(80f, 0));
        BARENTRY.add(new BarEntry(97f, 1));
        BARENTRY.add(new BarEntry(85f, 2));
        BARENTRY.add(new BarEntry(74f, 3));
        BARENTRY.add(new BarEntry(73f, 4));
        BARENTRY.add(new BarEntry(81f, 5));

    }

    public void AddValuesToBarEntryLabels(){

        BarEntryLabels.add("Week 1");
        BarEntryLabels.add("Week 2");
        BarEntryLabels.add("Week 4");
        BarEntryLabels.add("Week 5");
        BarEntryLabels.add("Week 6");
        BarEntryLabels.add("Week 8");

    }*/
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.instructor_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_1)
        {
            Intent launch = new Intent(this, classInstructor.class);
            launch.putExtra("id",11);
            startActivity(launch);

        } else if (id == R.id.nav_2)
        {
            Intent launch = new Intent(this, classInstructor.class);
            launch.putExtra("id",21);
            startActivity(launch);

        } else if (id == R.id.nav_3)
        {
            Intent launch = new Intent(this, classInstructor.class);
            launch.putExtra("id",31);
            startActivity(launch);

        }   else if (id == R.id.nav_logout)
        {
            Intent launch = new Intent(this, Login.class);
            startActivity(launch);

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true; //test
    }
}
