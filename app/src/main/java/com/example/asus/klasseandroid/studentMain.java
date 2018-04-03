package com.example.asus.klasseandroid;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v4.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.github.mikephil.charting.charts.LineChart;
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
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.stream.Collectors;


public class studentMain extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private LineChart mChart;
    SharedPreferences pref;
    SharedPreferences.Editor ed;
    String HTTPUrl;
    TextView esc;
    TextView cse;
    TextView ps;
   ArrayList<StudentAnalytics> sa=new ArrayList<>();

   @Override
   protected void onStart() {

       super.onStart();
       request();
   }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        HTTPUrl = "http://"+getResources().getString(R.string.ip)+"/Klasse/student_get_grades.php?student_id=";

        pref=getApplicationContext().getSharedPreferences("UserDetails",MODE_PRIVATE);
        ed=pref.edit();
        String id=pref.getString("id","1000000");
        HTTPUrl=HTTPUrl+id;



        esc=(TextView) findViewById(R.id.esc_txt);
        cse=(TextView) findViewById(R.id.cse_txt);
        ps=(TextView) findViewById(R.id.ps_txt);

        request();
        mChart = (LineChart) findViewById(R.id.linechart);
        mChart.setDragEnabled(true);
        mChart.setScaleEnabled(true);
        mChart.animateX(3000);
        mChart.setDescription("");


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
        RequestQueue requestQueue = Volley.newRequestQueue(studentMain.this);
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
                                Log.i("anwesha",ann.getInt("percentage")+" ");
                            }
                        }catch (JSONException e){
                            e.printStackTrace();
                            Log.i("anwesha",e.getMessage().toString());
                        }
                        setData();
                        setClasses();
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

    private void setClasses()
    {
        ArrayList<StudentAnalytics> s=sa;
        Map<Integer, StudentAnalytics> map = new HashMap<>();
        Map<Integer, Integer> total=new HashMap<>();

        for (StudentAnalytics p : s) {
            int c = p.getClassId();
            StudentAnalytics sum = map.get(c);
            if (sum == null) {

                sum = new StudentAnalytics(1,0,c);
                map.put(c, sum);
                total.put(c,0);

            }
            sum.setPercentage(sum.getPercentage() + p.getPercentage());
            total.put(c,total.get(c)+1);
            Log.i("anwesha",total.get(c)+" ");

        }
        Map<Integer, StudentAnalytics> m = new TreeMap<>(map);
        s=new ArrayList<StudentAnalytics>(m.values());

        for(StudentAnalytics stest:s)
        {
            switch(stest.getClassId())
            {
                case 11:
                    esc.setText("Elements of Software construction: "+stest.getPercentage()/total.get(stest.getClassId())+"%");
                    break;
                case 21:
                    cse.setText("Computer Systems Engineering: "+stest.getPercentage()/total.get(stest.getClassId())+"%");
                    break;
                case 31:
                    ps.setText("Probability and Statistics: "+stest.getPercentage()/total.get(stest.getClassId())+"%");
                    break;
                default:
                    Log.i("anwesha",stest.getClassId()+"test");

            }
        }


    }
    private void setData() {

        ArrayList<StudentAnalytics> s=sa;
        ArrayList<String> xVals = new ArrayList<String>();
        ArrayList<Entry> yVals = new ArrayList<Entry>();

        Map<Integer, StudentAnalytics> map = new HashMap<>();
        int count=1;

        for (StudentAnalytics p : s)
        {
            int week = p.getWeek();
            StudentAnalytics sum = map.get(week);
            if (sum == null) {
                count=1;
                sum = new StudentAnalytics(week, 0,0);
                map.put(week, sum);
            }
            sum.setPercentage((sum.getPercentage() + p.getPercentage())/count++);

        }
        Map<Integer, StudentAnalytics> m = new TreeMap<>(map);
        s=new ArrayList<StudentAnalytics>(m.values());

        int i=0;
        for(StudentAnalytics stest:s) {

          xVals.add("Week "+stest.getWeek());
          yVals.add(new Entry(stest.getPercentage(),i));
          i++;

        }

        LineDataSet set1;

        set1 = new LineDataSet(yVals, "Your Performance");
        set1.setFillAlpha(110);

        set1.setColor(Color.BLACK);
        set1.setCircleColor(Color.BLACK);
        set1.setLineWidth(1f);
        set1.setCircleRadius(3f);
        set1.setDrawCircleHole(false);
        set1.setValueTextSize(9f);
        //set1.setDrawFilled(true);
        //set1.setFillColor(R.color.colorPrimaryDark);

        ArrayList<ILineDataSet> dataSets = new ArrayList<ILineDataSet>();
        dataSets.add(set1); // add the datasets

        // create a data object with the datasets
        LineData data = new LineData(xVals, dataSets);
        //set1.setColors(ColorTemplate);
        // set data
        mChart.setData(data);

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
        getMenuInflater().inflate(R.menu.student_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();


        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_1)
        {
            Intent launch = new Intent(this, classStudent.class);
            launch.putExtra("id",11);
            startActivity(launch);

        } else if (id == R.id.nav_2)
        {
            Intent launch = new Intent(this, classStudent.class);
            launch.putExtra("id",21);
            startActivity(launch);

        } else if (id == R.id.nav_3)
        {
            Intent launch = new Intent(this, classStudent.class);
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
