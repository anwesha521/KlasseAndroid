package com.example.asus.klasseandroid;

import android.content.Context;
import android.content.Intent;
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
import android.widget.RelativeLayout;
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
import java.util.HashMap;
import java.util.Map;

public class InstructorViewQuiz extends AppCompatActivity implements View.OnClickListener{
    InstructorViewStudentQuizAdapter myAdapter;
    String url2;
    String url_update;
    String url_end;
    ArrayList<InstructorViewStudentQuizAdapter.question> sql=new ArrayList<>();
    ListView Questions;
    int week;
    int id;
    String status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instructor_view_quiz);

        Intent intent=getIntent();
        week=intent.getIntExtra("week",1);
        id=intent.getIntExtra("id",11);
        url2="http://"+getResources().getString(R.string.ip)+"/Klasse/get_quiz.php?class_id=";
        //url2="http://10.12.176.11/get_quiz.php?class_id=";
//        url_update="http://10.12.176.11/update_status.php";
//        url_end="http://10.12.176.11/delete.php";
        url_update="http://"+getResources().getString(R.string.ip)+"/Klasse/update_status.php";
        url_end="http://"+getResources().getString(R.string.ip)+"/Klasse/delete.php";

        TextView name=(TextView)findViewById(R.id.view_quiz_name);
        String nameText="Week "+week;
        name.setText(nameText);

        Questions=(ListView)findViewById(R.id.view_question_list);
        getQuestions();

        Button start=(Button)findViewById(R.id.start);
        Button end=(Button)findViewById(R.id.end);

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startQuiz(week);
            }
        });

        end.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteQuiz(week);
            }
        });
    }

    public void deleteQuiz(int week){
        final int week_num=week;
        if(myAdapter.getStatus().equals("active")){
            Toast.makeText(InstructorViewQuiz.this,"This Quiz Has Been Started!",Toast.LENGTH_LONG).show();
        }else if(myAdapter.getStatus().equals("inactive")){
            RequestQueue requestQueue=Volley.newRequestQueue(InstructorViewQuiz.this);
            StringRequest stringRequest=new StringRequest(Request.Method.POST, url_end,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Toast.makeText(InstructorViewQuiz.this,response,Toast.LENGTH_LONG).show();
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                        }
                    }){
                @Override
                protected Map<String,String> getParams(){
                    Map<String,String> params=new HashMap<>();
                    params.put("week",week_num+"");
                    return params;
                }
            };
            requestQueue.add(stringRequest);
        }
    }

    public void startQuiz(int week){
        final int week_num=week;
        if(myAdapter.getStatus().equals("active")){
            Toast.makeText(InstructorViewQuiz.this,"The quiz has started already!",Toast.LENGTH_LONG).show();
        }else if(myAdapter.getStatus().equals("inactive")){
            RequestQueue requestQueue=Volley.newRequestQueue(InstructorViewQuiz.this);
            StringRequest stringRequest=new StringRequest(Request.Method.POST, url_update,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Toast.makeText(InstructorViewQuiz.this,response,Toast.LENGTH_LONG).show();
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                        }
                    }){
                @Override
                protected Map<String,String> getParams(){
                    Map<String,String> params=new HashMap<>();
                    params.put("week",week_num+"");
                    return params;
                }
            };
            requestQueue.add(stringRequest);
        }
    }

    public void getQuestions(){
        RequestQueue requestQueue=Volley.newRequestQueue(InstructorViewQuiz.this);
        StringRequest stringRequest=new StringRequest(Request.Method.GET, url2+id,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONArray array=new JSONArray(response);
                            for(int i=0;i<array.length();i++){
                                JSONObject question=array.getJSONObject(i);
                                if(question.getInt("week")==week){
                                    sql.add(new InstructorViewStudentQuizAdapter.question(
                                            question.getString("description"),
                                            question.getString("type"),
                                            question.getString("mark"),
                                            question.getString("a_choice"),
                                            question.getString("b_choice"),
                                            question.getString("c_choice"),
                                            question.getString("d_choice")
                                    ));
                                    status=question.getString("status");
                                }
                            }
                            myAdapter=new InstructorViewStudentQuizAdapter(sql,InstructorViewQuiz.this,status);
                            Questions.setAdapter(myAdapter);
                        }catch (JSONException e){
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(InstructorViewQuiz.this,error.toString(),Toast.LENGTH_LONG).show();
                    }
                }){
        };


        requestQueue.add(stringRequest);
    }

    @Override
    public void onClick(View view) {
    }
}

class InstructorViewStudentQuizAdapter extends BaseAdapter {
    private String quiz_name;
    ArrayList<question> questions=new ArrayList<>();
    private Context context;
    private ArrayList<String> answers;
    private ArrayList<Integer> marks;
    private ArrayList<String> correct;
    private ArrayList<String> type;
    private String status;

    public InstructorViewStudentQuizAdapter(ArrayList<question> ql, Context context,String status){
        questions=ql;
        this.context=context;
        int size=questions.size();
        answers=new ArrayList<>(Arrays.asList(new String[size]));
        marks=new ArrayList<>(Arrays.asList(new Integer[size]));
        correct=new ArrayList<>(Arrays.asList(new String[size]));
        type=new ArrayList<>(Arrays.asList(new String[size]));
        this.status=status;
    }

    public String getStatus(){
        return status;
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
            view = inflater.inflate(R.layout.view_list, null);
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

        public question(String description,String type,String point,String a,String b,String c,String d){
            this.description=description;
            this.type=type;
            this.point=point;
            this.a=a;
            this.b=b;
            this.c=c;
            this.d=d;
        }
    }
}
