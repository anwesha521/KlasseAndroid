package general;

import android.content.Intent;
import android.content.SharedPreferences;
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
import com.example.asus.klasseandroid.R;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
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
    NavigationView navigationView;
    String HTTPUrlClasses;
    final HashMap<String,Integer> classes=new HashMap<>();
    @Override
    protected void onStart() {

        super.onStart();
        request();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instructor_main);
        navigationView = (NavigationView) findViewById(R.id.nav_view);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        pref=getApplicationContext().getSharedPreferences("UserDetails",MODE_PRIVATE);
        ed=pref.edit();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();


        navigationView.setNavigationItemSelectedListener(this);
        View header = navigationView.getHeaderView(0);


        HTTPUrl = "http://"+getResources().getString(R.string.ip)+"/Klasse/instructor_get_grades.php?instructor_id=";
        HTTPUrlClasses= "http://"+getResources().getString(R.string.ip)+"/Klasse/get_classes.php?user_id=";


        String id=pref.getString("id","1000000");
        HTTPUrl=HTTPUrl+id;
        Log.i("anwesha",HTTPUrlClasses+pref.getString("id","0")+"url");

        mChart = (BarChart) findViewById(R.id.barchartinstruct);
        mChart.setDescription("");
        requestClass();

        request();




        TextView name=(TextView) header.findViewById(R.id.header_name);
        name.setText(pref.getString("name","Anonymous"));

    }

    //displays only the classes that the instructor teaches in the navigation drawer
    public void requestClass()
    {

        String url=HTTPUrlClasses+pref.getString("id","0");
        RequestQueue requestQueue = Volley.newRequestQueue(instructorMain.this);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET,
                url
                ,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.i("anwesha", "reached");
                        try {


                            JSONObject ann = response.getJSONObject(0);

                            Log.i("anwesha", ann.getString("software_construction") + "esc");
                            classes.put("software", Integer.valueOf(ann.getString("software_construction")));

                            classes.put("computer", Integer.valueOf(ann.getString("computer_engineering")));
                            Log.i("anwesha", ann.getString("computer_engineering") + "cse");
                            classes.put("probability", Integer.valueOf(ann.getString("probability")));
                            Log.i("anwesha", ann.getString("probability") + "pns");


                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.i("anwesha", e.getMessage().toString());
                        }
                        Menu nav_Menu = navigationView.getMenu();
                        if(classes.get("software")==0)
                            nav_Menu.findItem(R.id.nav_1).setVisible(false);
                        if(classes.get("computer")==0)
                            nav_Menu.findItem(R.id.nav_2).setVisible(false);
                        if(classes.get("probability")==0)
                            nav_Menu.findItem(R.id.nav_3).setVisible(false);


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

    //queries database for all the grades of students enrolled in the instructors class/classes
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

                            for(int i=0;i<response.length();i++)
                            {
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

    //populates chart with average grades every week of all students enrolled in a class

    public void setDataChart()
    {
        int count=0;
        BARENTRY = new ArrayList<>();
        BarEntryLabels = new ArrayList<String>();

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
