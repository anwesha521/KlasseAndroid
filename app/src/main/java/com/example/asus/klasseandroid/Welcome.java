package com.example.asus.klasseandroid;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import com.example.asus.klasseandroid.R;

public class Welcome extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        Button instbutton=findViewById(R.id.instruct);
        Button studbutton=findViewById(R.id.student);
        studbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startStudent();
            }
        });
        instbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startInstruct();
            }
        });


    }
    public void startStudent()
    {
        Intent launch = new Intent(this, com.example.asus.klasseandroid.Login.class);
        Bundle b=new Bundle();
        b.putString("type","student");
        launch.putExtras(b);
        startActivity(launch);
    }
    public void startInstruct()
    {
        Intent launch = new Intent(this, com.example.asus.klasseandroid.Login.class);
        Bundle b=new Bundle();
        b.putString("type","instructor");
        launch.putExtras(b);
        startActivity(launch);
    }
}
