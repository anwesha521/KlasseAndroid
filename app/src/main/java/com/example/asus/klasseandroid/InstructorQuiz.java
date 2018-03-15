package com.example.asus.klasseandroid;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class InstructorQuiz extends AppCompatActivity implements View.OnClickListener{
    InstructorQuizAdapter myAdapter;
    ArrayList<InstructorQuizAdapter.question> ql=new ArrayList<>();
    String url="http://10.12.195.1/upload_quiz.php";
    RequestQueue requestQueue;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instructor_quiz);
        ql.add(new InstructorQuizAdapter.question());

        Button add=(Button)findViewById(R.id.add);
        Button save=(Button)findViewById(R.id.save);
        add.setOnClickListener(this);
        save.setOnClickListener(this);

        myAdapter=new InstructorQuizAdapter(ql,this);
        ListView listView=(ListView)findViewById(R.id.questionList);
        listView.setAdapter(myAdapter);
    }

    public RequestQueue getRequestQueue(){
        if(requestQueue==null){
            requestQueue=Volley.newRequestQueue(getApplicationContext());
        }
        return requestQueue;
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.add:
                myAdapter.add();
                myAdapter.notifyDataSetChanged();
                break;
            case R.id.save:
                ql=myAdapter.getQl();
                //upload the question list to database
                for(int i=0;i<ql.size();i++){
                    ql.get(i).number=(i+1)+"";
                }
                for(final InstructorQuizAdapter.question q:ql){
                    StringRequest stringRequest=new StringRequest(Request.Method.POST, url,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    Toast.makeText(InstructorQuiz.this, response, Toast.LENGTH_SHORT).show();
                                }
                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    Toast.makeText(InstructorQuiz.this,error.toString(),Toast.LENGTH_LONG).show();
                                }
                            }){
                        @Override
                        protected Map<String,String> getParams(){
                            Map<String,String> params=new HashMap<>();
                            params.put("number",q.number);
                            params.put("description",q.description);
                            if(q.type==0){
                                params.put("type","QNA");
                            }else if(q.type==1){
                                params.put("type","MCQ");
                            }
                            params.put("mark",q.point);
                            params.put("a_choice",q.a);
                            params.put("b_choice",q.b);
                            params.put("c_choice",q.c);
                            params.put("d_choice",q.d);

                            return params;
                        }
                    };

                    getRequestQueue().add(stringRequest);
                }

                break;
        }
    }
}
