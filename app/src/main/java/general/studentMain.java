package general;

import android.content.SharedPreferences;
import android.graphics.Color;

import android.content.Intent;
import android.os.Bundle;
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
import com.example.asus.klasseandroid.R;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;


public class studentMain extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private LineChart mChart;
    SharedPreferences pref;
    SharedPreferences.Editor ed;
    String HTTPUrl;
    String HTTPUrlGetGrade;
    TextView esc;
    TextView cse;
    TextView ps;
   ArrayList<StudentAnalytics> sa=new ArrayList<>();



   protected void onRestart() {

       super.onRestart();
       request();
   }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        HTTPUrl = "http://"+getResources().getString(R.string.ip)+"/Klasse/student_get_grades.php?student_id=";
        HTTPUrlGetGrade="http://"+getResources().getString(R.string.ip)+"/Klasse/get_max_grades.php";
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

    //queries database for current student's grades
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

                            }
                        }catch (JSONException e){
                            e.printStackTrace();
                            Log.i("anwesha",e.getMessage().toString());
                        }
                        request1();

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
//displays average grades of student for every single class they are enrolled in/have taken quizzes for
    private void setClasses()
    {
        ArrayList<StudentAnalytics> s=sa;
        Map<Integer, StudentAnalytics> map = new HashMap<>();
        Map<Integer, Integer> total=new HashMap<>();

        for (int i=0;i<s.size();i++) {
            int c = s.get(i).getClassId();
            StudentAnalytics sum = map.get(c);
            if (sum == null) {

                sum = new StudentAnalytics(1,0,c);
                map.put(c, sum);
                total.put(c,0);

            }
            sum.setPercentage(sum.getPercentage() + s.get(i).getPercentage());
            total.put(c,total.get(c)+1);


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
//queries database for maximum grades
    private void request1()
    {  final ArrayList<weeklyGrade> wg=new ArrayList<>();

        RequestQueue requestQueue = Volley.newRequestQueue(studentMain.this);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET,
                HTTPUrlGetGrade,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {


                        try{

                            for(int i=0;i<response.length();i++){
                                // Get current json object
                                JSONObject ann = response.getJSONObject(i);
                                wg.add(new weeklyGrade(ann.getInt("week"),ann.getInt("percentage")));


                            }

                        }catch (JSONException e){
                            e.printStackTrace();
                            Log.i("anwesha",e.getMessage().toString());
                        }
                        setData1(wg);

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
    //populates graph with overall average weekly grades of the student as well as the maximum weekly grades to show a comparison
    private void setData1(ArrayList<weeklyGrade> wg) {

        ArrayList<StudentAnalytics> s=sa;
        ArrayList<String> xVals = new ArrayList<String>();
        ArrayList<Entry> yVals = new ArrayList<Entry>();
        ArrayList<Entry> yVals1 = new ArrayList<Entry>();


        Map<Integer, StudentAnalytics> map = new HashMap<>();
        int count=1;
        int i=0;


        if(wg.size()>0) {
            for (StudentAnalytics p:s)
            {

                int week = p.getWeek();

                StudentAnalytics sum = map.get(week);
                if (sum == null) {

                    sum = new StudentAnalytics(week, 0, 0);

                    map.put(week, sum);
                }
                sum.setPercentage((sum.getPercentage() + p.getPercentage()) / sum.getAndSet());


            }

            Map<Integer, StudentAnalytics> m = new TreeMap<>(map);
            s = new ArrayList<StudentAnalytics>(m.values());


            for (weeklyGrade w : wg) {
                yVals1.add(new Entry(w.getPercentage(), i));
                xVals.add("Week " + w.getWeek());
                boolean flag=true;
                for(int j=0;j<s.size();j++)
                {
                    if(s.get(j)!=null)
                    if(s.get(j).getWeek()==w.getWeek()) {

                        flag=false;
                        yVals.add(new Entry(s.get(j).getPercentage(), i));
                        break;
                    }

                }
                if(flag)
                    yVals.add(new Entry(0, i));


                i++;
            }
        }

        LineDataSet set1; //set1 displays current student's grades
        LineDataSet set2; //set2 displays maximum performance

        set1 = new LineDataSet(yVals, "Your Performance");
        set2=new LineDataSet(yVals1, "Maximum Performance");
        set1.setFillAlpha(110);
        set1.setColor(Color.BLACK);
        set1.setCircleColor(Color.BLACK);
        set1.setLineWidth(1f);
        set1.setCircleRadius(3f);
        set1.setDrawCircleHole(false);
        set1.setValueTextSize(9f);

        set2.setFillAlpha(110);
        set2.setColor(Color.RED);
        set2.setCircleColor(Color.RED);
        set2.setLineWidth(1f);
        set2.setCircleRadius(3f);
        set2.setDrawCircleHole(false);
        set2.setValueTextSize(9f);

        ArrayList<ILineDataSet> dataSets = new ArrayList<ILineDataSet>();
        dataSets.add(set1); // add the datasets
        dataSets.add(set2);
        LineData data = new LineData(xVals, dataSets);

        mChart.setData(data);
        setClasses();

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

    class weeklyGrade
    {
        private int weeks;
        private int percentage;

        public weeklyGrade(int w, int p)
        {
            weeks=w;
            percentage=p;
        }

        public int getPercentage()
        {
            return percentage;
        }

        public int getWeek()
        {
            return weeks;
        }
    }
}
