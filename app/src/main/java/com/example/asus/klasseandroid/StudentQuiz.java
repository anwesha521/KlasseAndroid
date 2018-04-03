package com.example.asus.klasseandroid;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class StudentQuiz extends AppCompatActivity implements View.OnClickListener{
    StudentQuizAdapter myAdapter;
    String url2;
    String url3;
    ArrayList<StudentQuizAdapter.question> sql=new ArrayList<>();
    StudentQuizAdapter.quiz quiz;
    ListView Questions;
    String quizName;
    String week;
    SharedPreferences pref;
    int room_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_quiz);

        Intent intent=getIntent();
        quizName=intent.getStringExtra("name");
        room_id=intent.getIntExtra("id",11);
        url2="http://"+getResources().getString(R.string.ip)+"/Klasse/get_quiz.php?class_id=";
        url3="http://"+getResources().getString(R.string.ip)+"/Klasse/submit.php";
        pref = getApplicationContext().getSharedPreferences("UserDetails", MODE_PRIVATE);

        TextView name=(TextView)findViewById(R.id.quizName);
        name.setText(quizName);

        Questions=(ListView)findViewById(R.id.question_list);
        getQuestions();

        Button submit=(Button)findViewById(R.id.submit);
        submit.setOnClickListener(this);
    }

    public void getQuestions(){
        RequestQueue requestQueue=Volley.newRequestQueue(StudentQuiz.this);
        StringRequest stringRequest=new StringRequest(Request.Method.GET, url2+room_id,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONArray array=new JSONArray(response);
                            for(int i=0;i<array.length();i++){
                                JSONObject question=array.getJSONObject(i);
                                if(question.getString("quiz_name").equals(quizName)){
                                    week=question.getString("week");
                                    sql.add(new StudentQuizAdapter.question(
                                            question.getString("description"),
                                            question.getString("type"),
                                            question.getString("mark"),
                                            question.getString("a_choice"),
                                            question.getString("b_choice"),
                                            question.getString("c_choice"),
                                            question.getString("d_choice"),
                                            question.getString("answer")
                                    ));
                                }
                            }
                            quiz=new StudentQuizAdapter.quiz(quizName,sql);
                            myAdapter=new StudentQuizAdapter(quiz,StudentQuiz.this);
                            Questions.setAdapter(myAdapter);
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
                }){
        };


        requestQueue.add(stringRequest);
    }

    @Override
    public void onClick(View view) {
        RequestQueue requestQueue=Volley.newRequestQueue(StudentQuiz.this);

        StringRequest stringRequest=new StringRequest(Request.Method.POST, url3,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Document doc = Jsoup.parse(response);
                        String result = doc.body().text();
                        Toast.makeText(StudentQuiz.this,result,Toast.LENGTH_LONG).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(StudentQuiz.this,error.toString(),Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params=new HashMap<>();
                params.put("quizname",quizName);
                params.put("student_id",pref.getString("id","0"));
                params.put("answers",myAdapter.getAnswer().toString());
                params.put("week",week);
                params.put("correct",myAdapter.getCorrect().toString());
                params.put("marks",myAdapter.getMarks().toString());
                params.put("total",myAdapter.getTotal()+"");
                params.put("type",myAdapter.getType().toString());
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }
}

class StudentQuizAdapter extends BaseAdapter {
    private String quiz_name;
    ArrayList<question> questions=new ArrayList<>();
    private Context context;
    private ArrayList<String> answers;
    private ArrayList<Integer> marks;
    private ArrayList<String> correct;
    private ArrayList<String> type;

    public StudentQuizAdapter(quiz q, Context context){
        quiz_name=q.name;
        questions=q.ql;
        this.context=context;
        int size=questions.size();
        answers=new ArrayList<>(Arrays.asList(new String[size]));
        marks=new ArrayList<>(Arrays.asList(new Integer[size]));
        correct=new ArrayList<>(Arrays.asList(new String[size]));
        type=new ArrayList<>(Arrays.asList(new String[size]));
    }

    @Override
    public int getCount() {
        return questions.size();
    }

    @Override
    public Object getItem(int i) {
        return questions.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        View view=convertView;
        if(view==null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.student_question_list, null);
        }

        TextView question_number=(TextView)view.findViewById(R.id.number);
        question_number.setText("Question "+(position+1));

        TextView description=(TextView)view.findViewById(R.id.description);
        description.setText("("+questions.get(position).point+" points) "+questions.get(position).description);

        final EditText answer=(EditText)view.findViewById(R.id.answer);
        answer.setTag(position);
        answer.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void afterTextChanged(Editable editable) {
                answers.set((Integer)answer.getTag(),answer.getText().toString());
            }
        });

        RadioGroup choices=(RadioGroup)view.findViewById(R.id.choices);
        choices.setTag(position);
        choices.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener(){
            public void onCheckedChanged(RadioGroup radioGroup,@IdRes int id){
                int index=(Integer)radioGroup.getTag();
                switch (id){
                    case R.id.rb1:
                        answers.set(index,"A");
                        break;
                    case R.id.rb2:
                        answers.set(index,"B");
                        break;
                    case R.id.rb3:
                        answers.set(index,"C");
                        break;
                    case R.id.rb4:
                        answers.set(index,"D");
                        break;
                }
            }
        });

        RadioButton ch1=(RadioButton)view.findViewById(R.id.rb1);
        ch1.setText(questions.get(position).a);
        RadioButton ch2=(RadioButton)view.findViewById(R.id.rb2);
        ch2.setText(questions.get(position).b);
        RadioButton ch3=(RadioButton)view.findViewById(R.id.rb3);
        ch3.setText(questions.get(position).c);
        RadioButton ch4=(RadioButton)view.findViewById(R.id.rb4);
        ch4.setText(questions.get(position).d);

        if(questions.get(position).type.equals("QNA")){
            answer.setVisibility(View.VISIBLE);
            choices.setVisibility(View.GONE);
        }else if(questions.get(position).type.equals("MCQ")){
            choices.setVisibility(View.VISIBLE);
            answer.setVisibility(View.GONE);
        }

        return view;
    }

    static class question{
        String description="";
        String type="";
        String point="";
        String a="";
        String b="";
        String c="";
        String d="";
        String correct="";

        public question(String description,String type,String point,String a,String b,String c,String d,String correct){
            this.description=description;
            this.type=type;
            this.point=point;
            this.a=a;
            this.b=b;
            this.c=c;
            this.d=d;
            this.correct=correct;
        }
    }
    static class quiz{
        String name;
        ArrayList<question> ql=new ArrayList<>();

        public quiz(String name,ArrayList<question> ql){
            this.name=name;
            this.ql=ql;
        }
        public quiz(String name){
            this.name=name;
        }

        public void add(question q){
            ql.add(q);
        }
    }

    public ArrayList<String> getAnswer(){
        return answers;
    }

    public ArrayList<Integer> getMarks(){
        for(int i=0;i<questions.size();i++){
            int n=Integer.parseInt(questions.get(i).point);
            marks.set(i,n);
        }
        return marks;
    }

    public int getTotal(){
        int total=0;
        for(question q:questions){
            total+=Integer.parseInt(q.point);
        }
        return total;
    }

    public ArrayList<String> getCorrect(){
        for(int i=0;i<questions.size();i++){
            correct.set(i,questions.get(i).correct);
        }
        return correct;
    }

    public ArrayList<String> getType(){
        for(int i=0;i<questions.size();i++){
            type.set(i,questions.get(i).type);
        }
        return type;
    }
}
