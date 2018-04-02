package com.example.asus.klasseandroid;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
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
import java.util.Arrays;

public class StudentViewQuiz extends AppCompatActivity {
    ListView listView;
    RequestQueue requestQueue;
    StudentViewAdapter myViewAdapter;
    String url1="http://10.12.176.11/get_quiz.php?class_id=";
    String url2="http://10.12.176.11/get_grades_student.php";
    ArrayList<String> names;
    ArrayList<String> statuses;
    ArrayList<Integer> weeks;

    int room_id;
    SharedPreferences pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_view_quiz);

        Intent intent=getIntent();
        room_id=intent.getIntExtra("id",11);

        requestQueue= Volley.newRequestQueue(StudentViewQuiz.this);
        listView=(ListView)findViewById(R.id.quiz);

        pref=getApplicationContext().getSharedPreferences("UserDetails",MODE_PRIVATE);

        names=new ArrayList<>();
        weeks=new ArrayList<>();

        getQuizName();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String status=myViewAdapter.statuses.get(i);
                if(status.equals("Completed")){
                    Toast.makeText(view.getContext(),"You have already completed this quiz.",Toast.LENGTH_LONG).show();
                }else {
                    Intent launch=new Intent(view.getContext(),StudentQuiz.class);
                    launch.putExtra("name",myViewAdapter.viewQuizzes.get(i));
                    launch.putExtra("id",room_id);
                    startActivity(launch);
                }
            }
        });
    }

    public void getQuizName(){
        StringRequest stringRequest=new StringRequest(Request.Method.GET, url1+room_id,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONArray array=new JSONArray(response);
                            for(int i=0;i<array.length();i++){
                                names.add(array.getJSONObject(i).getString("quiz_name"));
                                weeks.add(array.getJSONObject(i).getInt("week"));
                            }
                        }catch (JSONException e){
                            e.printStackTrace();
                        }
                        getStatus();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                    }
                });
        requestQueue.add(stringRequest);
    }

    public void getStatus(){
        statuses=new ArrayList<String>(Arrays.asList(new String[weeks.size()]));
        StringRequest stringRequest=new StringRequest(Request.Method.GET, url2,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONArray array=new JSONArray(response);
                            for(int i=0;i<array.length();i++){
                                JSONObject one=array.getJSONObject(i);
                                for(int j=0;j<weeks.size();j++){
                                    if(one.getInt("week")==weeks.get(j)&&
                                            one.getString("student_id").equals(pref.getString("id","0"))){
                                        statuses.set(j,"Completed");
                                    }
                                }
                                Log.i("shunqi",names.toString());
                                for(int j=0;j<weeks.size();j++){
                                    if(statuses.get(j)==null){
                                        statuses.set(j,"");
                                    }
                                }
                            }
                            Log.i("shunqi",names.toString());
                            myViewAdapter=new StudentViewAdapter(names,statuses,StudentViewQuiz.this);
                            listView.setAdapter(myViewAdapter);
                        }catch (JSONException e){
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                    }
                });
        requestQueue.add(stringRequest);
    }
}

class StudentViewAdapter extends BaseAdapter{
    ArrayList<String> viewQuizzes;
    ArrayList<String> statuses;
    Context context;

    public StudentViewAdapter(ArrayList<String> quizzes,ArrayList<String> statuses,Context context){
        viewQuizzes=quizzes;
        this.context=context;
        this.statuses=statuses;
    }

    @Override
    public int getCount() {
        return viewQuizzes.size();
    }

    @Override
    public Object getItem(int i) {
        return viewQuizzes.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        View view=convertView;
        if(view==null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.student_view_list, null);
        }

        TextView viewName=(TextView)view.findViewById(R.id.name);
        TextView viewStatus=(TextView)view.findViewById(R.id.status);

        viewName.setText(viewQuizzes.get(i));
        viewStatus.setText(statuses.get(i));

        return view;
    }
}