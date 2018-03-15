package com.example.asus.klasseandroid;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

public class InstructorQuizAdapter extends BaseAdapter{
    private ArrayList<Integer> il=new ArrayList<>();
    private ArrayList<question> ql=new ArrayList<>();
    private Context context;

    public InstructorQuizAdapter(ArrayList<question> input, Context context){
        ql=input;
        this.context=context;
    }

    @Override
    public int getCount() {
        return ql.size();
    }

    @Override
    public Object getItem(int i) {
        return ql.get(i);
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
            view = inflater.inflate(R.layout.vlist, null);
        }

        TextView title=(TextView)view.findViewById(R.id.title);
        title.setText("Question "+(position+1));


        final TextView a=(TextView)view.findViewById(R.id.a);
        final TextView b=(TextView)view.findViewById(R.id.b);
        final TextView c=(TextView)view.findViewById(R.id.c);
        final TextView d=(TextView)view.findViewById(R.id.d);

        final EditText a_description=(EditText)view.findViewById(R.id.a_description);
        a_description.setText(ql.get(position).a);
        a_description.setTag(position);

        final EditText b_description=(EditText)view.findViewById(R.id.b_description);
        b_description.setText(ql.get(position).b);
        b_description.setTag(position);

        final EditText c_description=(EditText)view.findViewById(R.id.c_description);
        c_description.setText(ql.get(position).c);
        c_description.setTag(position);

        final EditText d_description=(EditText)view.findViewById(R.id.d_description);
        d_description.setText(ql.get(position).d);
        d_description.setTag(position);

        final EditText point=(EditText)view.findViewById(R.id.point);
        point.setText(ql.get(position).point);
        point.setTag(position);

        final Spinner spinner=(Spinner)view.findViewById(R.id.type);
        spinner.setSelection(ql.get(position).type);
        spinner.setTag(position);

        final EditText editText=(EditText)view.findViewById(R.id.description);
        editText.setText(ql.get(position).description);
        editText.setTag(position);

        Button add_next=(Button)view.findViewById(R.id.addNext);
        Button delete=(Button)view.findViewById(R.id.delete);

        add_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //il.add(position+1,position+2);
                question newQuestion=new question();
                ql.add(position+1,newQuestion);
                notifyDataSetChanged();
            }
        });
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //il.remove(position);
                ql.remove(position);
                notifyDataSetChanged();
            }
        });

        a_description.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void afterTextChanged(Editable editable) {
                ql.get((Integer)a_description.getTag()).a=a_description.getText().toString();
            }
        });

        b_description.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void afterTextChanged(Editable editable) {
                ql.get((Integer)b_description.getTag()).b=b_description.getText().toString();
            }
        });

        c_description.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void afterTextChanged(Editable editable) {
                ql.get((Integer)c_description.getTag()).c=c_description.getText().toString();
            }
        });

        d_description.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void afterTextChanged(Editable editable) {
                ql.get((Integer)d_description.getTag()).d=d_description.getText().toString();
            }
        });

        point.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void afterTextChanged(Editable editable) {
                ql.get((Integer)point.getTag()).point=point.getText().toString();
            }
        });

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void afterTextChanged(Editable editable) {
                ql.get((Integer)editText.getTag()).description=editText.getText().toString();
            }
        });

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                ql.get((Integer)spinner.getTag()).type=spinner.getSelectedItemPosition();
                if(spinner.getSelectedItemPosition()==1){
                    a.setVisibility(View.VISIBLE);
                    b.setVisibility(View.VISIBLE);
                    c.setVisibility(View.VISIBLE);
                    d.setVisibility(View.VISIBLE);
                    a_description.setVisibility(View.VISIBLE);
                    b_description.setVisibility(View.VISIBLE);
                    c_description.setVisibility(View.VISIBLE);
                    d_description.setVisibility(View.VISIBLE);
                }else if(spinner.getSelectedItemPosition()==0){
                    a.setVisibility(View.GONE);
                    b.setVisibility(View.GONE);
                    c.setVisibility(View.GONE);
                    d.setVisibility(View.GONE);
                    a_description.setVisibility(View.GONE);
                    b_description.setVisibility(View.GONE);
                    c_description.setVisibility(View.GONE);
                    d_description.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {}
        });

        return view;
    }
    public void add(){
        ql.add(new question());
    }

    static class question{
        String number="";
        String description="";
        int type=0;
        String point="";
        String a="";
        String b="";
        String c="";
        String d="";
    }
    public ArrayList<question> getQl(){
        return ql;
    }
}
