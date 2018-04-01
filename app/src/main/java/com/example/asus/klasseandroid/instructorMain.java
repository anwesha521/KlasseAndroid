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
import android.view.View;
import android.widget.TextView;

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
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class instructorMain extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private BarChart mChart;
    ArrayList<BarEntry> BARENTRY ;
    ArrayList<String> BarEntryLabels ;
    BarDataSet Bardataset ;
    BarData BARDATA ;
    SharedPreferences pref;
    SharedPreferences.Editor ed;
    String HTTPUrl;
    ArrayList<StudentAnalytics> sa=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instructor_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        pref=getApplicationContext().getSharedPreferences("UserDetails",MODE_PRIVATE);
        ed=pref.edit();
        HTTPUrl = "http://"+getResources().getString(R.string.ip)+"/Klasse/instructor_get_grades.php?instructor_id=";


        String id=pref.getString("id","1000000");
        HTTPUrl=HTTPUrl+id;

        mChart = (BarChart) findViewById(R.id.barchartinstruct);
        mChart.setDescription("");
        BARENTRY = new ArrayList<>();
        BarEntryLabels = new ArrayList<String>();



        request();



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View header = navigationView.getHeaderView(0);
        TextView name=(TextView) header.findViewById(R.id.header_name);
        name.setText(pref.getString("name","Anonymous"));
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
                        setDataChart();

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

    public void setDataChart()
    {
        int count=0;
        ArrayList<StudentAnalytics> s=sa;
        Map<Integer, StudentAnalytics> map = new HashMap<>();
        Map<Integer, Integer> total=new HashMap<>();

        for (StudentAnalytics p : s) {
            int w = p.getWeek();
            StudentAnalytics sum = map.get(w);
            if (sum == null) {

                sum = new StudentAnalytics(w,0,0);
                map.put(w, sum);
                total.put(w,0);

            }
            sum.setPercentage(sum.getPercentage() + p.getPercentage());
            total.put(w,total.get(w)+1);

        }
        Map<Integer, StudentAnalytics> m = new TreeMap<>(map);
        s=new ArrayList<StudentAnalytics>(m.values());

        for(StudentAnalytics p:s)
        {

            BARENTRY.add(new BarEntry((float)p.getPercentage()/total.get(p.getWeek()),count++));
            BarEntryLabels.add("Week "+p.getWeek());
            Log.i("anwesha",(float)p.getPercentage()/total.get(p.getWeek())+" "+p.getWeek()+"w");

        }
        Bardataset = new BarDataSet(BARENTRY, "Average scores");

        BARDATA = new BarData(BarEntryLabels, Bardataset);
        mChart.setData(BARDATA);

        Bardataset.setColors(ColorTemplate.COLORFUL_COLORS);

        mChart.animateX(4000);
        mChart.setDragEnabled(true);
        mChart.setScaleEnabled(true);

    }

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
