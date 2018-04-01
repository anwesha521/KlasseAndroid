package com.example.asus.klasseandroid;

import android.content.Context;
import android.support.annotation.IdRes;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;

public class StudentQuizAdapter extends BaseAdapter{
    private String quiz_name;
    private ArrayList<question> questions=new ArrayList<>();
    private Context context;
    private ArrayList<String> answers;

    public StudentQuizAdapter(quiz q, Context context){
        quiz_name=q.name;
        questions=q.ql;
        this.context=context;
        answers=new ArrayList<>(Arrays.asList(new String[questions.size()]));
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
        description.setText(questions.get(position).description+" ("+questions.get(position).point+" points)");

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
}
