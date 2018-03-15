package com.example.asus.klasseandroid;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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

public class StudentQuiz extends AppCompatActivity implements View.OnClickListener{
    StudentQuizAdapter myAdapter;
    ArrayList<String> Answer=new ArrayList<>();
    String url="http://10.12.195.1/get_quiz.php";
    ArrayList<StudentQuizAdapter.question> sql=new ArrayList<>();
    String info;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_quiz);

        TextView name=(TextView)findViewById(R.id.quizName);

        getQuestions();
        StudentQuizAdapter.quiz q=new StudentQuizAdapter.quiz("Quiz 1",sql);
        name.setText(q.name);

        myAdapter=new StudentQuizAdapter(q,this);
        ListView Questions=(ListView)findViewById(R.id.question_list);
        Questions.setAdapter(myAdapter);

        Button submit=(Button)findViewById(R.id.submit);
        submit.setOnClickListener(this);
    }

    public void getQuestions(){
        RequestQueue requestQueue = Volley.newRequestQueue(StudentQuiz.this);
        StringRequest stringRequest=new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            //Log.i("shunqi",response);
                            JSONArray array=new JSONArray(response);
                            for(int i=0;i<array.length();i++){
                                JSONObject question=array.getJSONObject(i);
                                sql.add(new StudentQuizAdapter.question(
                                        question.getString("description"),
                                        question.getString("type"),
                                        question.getString("mark"),
                                        question.getString("a_choice"),
                                        question.getString("b_choice"),
                                        question.getString("c_choice"),
                                        question.getString("d_choice")
                                ));
                            }
                            Log.i("shunqi",sql.toString());
                        }catch (JSONException e){
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(StudentQuiz.this,error.toString(),Toast.LENGTH_LONG).show();
                    }
                });


        requestQueue.add(stringRequest);
    }

    @Override
    public void onClick(View view) {
        Answer=myAdapter.getAnswer();
        //upload the answer to database
        Toast.makeText(this,info,Toast.LENGTH_LONG).show();
    }
}
