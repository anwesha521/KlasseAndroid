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

import java.util.ArrayList;
import java.util.Arrays;

public class InstructorViewQuiz extends AppCompatActivity implements View.OnClickListener{
    InstructorViewStudentQuizAdapter myAdapter;
    String url2;
    ArrayList<InstructorViewStudentQuizAdapter.question> sql=new ArrayList<>();
    ListView Questions;
    int week;
    int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instructor_view_quiz);

        Intent intent=getIntent();
        week=intent.getIntExtra("week",1);
        id=intent.getIntExtra("id",11);
        url2="http://"+getResources().getString(R.string.ip)+"/Klasse/get_quiz.php?class_id=";
        TextView name=(TextView)findViewById(R.id.quizName);
        String nameText="Week "+week;
        name.setText(nameText);

        Questions=(ListView)findViewById(R.id.question_list);
        getQuestions();
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
                                }
                            }
                            myAdapter=new InstructorViewStudentQuizAdapter(sql,InstructorViewQuiz.this);
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

    public InstructorViewStudentQuizAdapter(ArrayList<question> ql, Context context){
        questions=ql;
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