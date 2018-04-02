package com.example.asus.klasseandroid;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

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
import java.util.HashMap;
import java.util.Map;

public class ManageQuiz extends AppCompatActivity {
    String url="http://10.12.176.11/get_quiz.php?class_id=";
    ListView listView;
    ArrayList<manageQuiz> Quizzes;
    int room_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_quiz);

        Intent intent=getIntent();
        room_id=intent.getIntExtra("id",11);

        Quizzes=new ArrayList<>();

        listView=(ListView)findViewById(R.id.quiz_list);
        ManageReadData();
    }

    public void ManageReadData(){
        final RequestQueue requestQueue= Volley.newRequestQueue(ManageQuiz.this);
        StringRequest stringRequest=new StringRequest(Request.Method.GET, url+room_id,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray array=new JSONArray(response);
                            Map<Integer,Integer> weekMap=new HashMap<>();
                            for(int i=0;i<array.length();i++){
                                JSONObject week=array.getJSONObject(i);
                                int weekNumber=week.getInt("week");
                                if(!weekMap.containsKey(weekNumber)){
                                    weekMap.put(weekNumber,1);
                                }else {
                                    weekMap.put(weekNumber,weekMap.get(weekNumber)+1);
                                }
                            }
                            for(int w:weekMap.keySet()){
                                Quizzes.add(new manageQuiz(w,weekMap.get(w)));
                            }
                            ManageQuizAdapter myAdapter=new ManageQuizAdapter(Quizzes,room_id,ManageQuiz.this);
                            listView.setAdapter(myAdapter);
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

class ManageQuizAdapter extends BaseAdapter {
    ArrayList<manageQuiz> quizzes;
    Context context;
    int id;

    public ManageQuizAdapter(ArrayList<manageQuiz> al,int id,Context context){
        quizzes=al;
        this.context=context;
        this.id=id;
    }

    @Override
    public int getCount() {
        return quizzes.size();
    }

    @Override
    public Object getItem(int i) {
        return quizzes.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view=convertView;
        if(view==null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.one_quiz, null);
        }

        TextView week=(TextView)view.findViewById(R.id.week);
        TextView questions=(TextView)view.findViewById(R.id.questions);

        String weekText="Week: "+quizzes.get(position).week;
        week.setText(weekText);
        String questionsText=quizzes.get(position).questions+" Questions";
        questions.setText(questionsText);

        Button View=(Button)view.findViewById(R.id.view);
        View.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent launch=new Intent(view.getContext(),InstructorViewQuiz.class);
                launch.putExtra("week",quizzes.get(position).week);
                launch.putExtra("id",id);
                view.getContext().startActivity(launch);
            }
        });

        Button Edit=(Button)view.findViewById(R.id.edit);
        Edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent launch=new Intent(view.getContext(),InstructorEditQuiz.class);
                launch.putExtra("week",quizzes.get(position).week);
                launch.putExtra("id",id);
                view.getContext().startActivity(launch);
            }
        });

        return view;
    }
}

class manageQuiz{
    int week;
    int questions;
    public manageQuiz(int week,int questions){
        this.week=week;
        this.questions=questions;
    }
}
