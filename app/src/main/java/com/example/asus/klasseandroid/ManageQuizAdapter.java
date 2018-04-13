package com.example.asus.klasseandroid;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by ASUS on 02-04-2018.
 */

class ManageQuizAdapter extends BaseAdapter {
    ArrayList<Manage> quizzes;
    Context context;
    int id;

    public ManageQuizAdapter(ArrayList<Manage> al,int id,Context context){
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
                if(quizzes.get(position).status.equals("inactive")){
                    Intent launch=new Intent(view.getContext(),InstructorEditQuiz.class);
                    launch.putExtra("week",quizzes.get(position).week);
                    launch.putExtra("id",id);
                    view.getContext().startActivity(launch);
                }else if(quizzes.get(position).status.equals("active")){
                    Toast.makeText(view.getContext(),"This Quiz Has Been Started.",Toast.LENGTH_LONG).show();
                }
            }
        });

        return view;
    }
}
