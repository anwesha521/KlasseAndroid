package com.example.asus.klasseandroid;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import java.util.*;

public class InstructorQuiz extends AppCompatActivity implements View.OnClickListener{

    private ListView questionList;
    private SimpleAdapter adapter;
    private ArrayList<Map<String,Object>> data=new ArrayList<>();
    private ArrayList<Question> ql=new ArrayList<>();
    private Map<String,Object> map=new HashMap<String,Object>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instructor_quiz);
        final Button addBtn=(Button)findViewById(R.id.add);
        Button saveBtn=(Button)findViewById(R.id.save);
        addBtn.setOnClickListener(this);
        saveBtn.setOnClickListener(this);

        Question question=new Question(1);
        map=new HashMap<String,Object>();
        map.put("title",question.getQuestionTitle());
        data.add(map);
        ql.add(question);

        questionList=(ListView)findViewById(R.id.questionList);
        adapter=new SimpleAdapter(this,data,R.layout.vlist,
                new String[]{"title"},
                new int[]{R.id.title});
        questionList.setAdapter(adapter);
    }


    class Question{
        private int number;
        private String type;
        private String description;

        Question(int n){number=n;}
        void setNumber(int n){number=n;}
        void setType(String t){type=t;}
        void setDescription(String d){description=d;}
        int getNumber(){return number;}
        String getType(){return type;}
        String getDescription(){return description;}
        String getQuestionTitle(){return "Question"+number;}
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.add:
                Question question=new Question(adapter.getCount()+1);
                map=new HashMap<String,Object>();
                map.put("title",question.getQuestionTitle());
                data.add(map);
                ql.add(question);
                adapter.notifyDataSetChanged();
                break;
            case R.id.save:
                break;
        }
    }
}