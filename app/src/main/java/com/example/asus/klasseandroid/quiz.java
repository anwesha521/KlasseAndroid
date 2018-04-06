package com.example.asus.klasseandroid;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class quiz extends AppCompatActivity {
    int room_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        Intent intent=getIntent();
        room_id=intent.getIntExtra("id",0);

        Button manage=(Button)findViewById(R.id.manage);
        Button create=(Button)findViewById(R.id.create);

        manage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(view.getContext(),ManageQuiz.class);
                intent.putExtra("id",room_id);
                startActivity(intent);
            }
        });
        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(view.getContext(),InstructorQuiz.class);
                intent.putExtra("id",room_id);
                startActivity(intent);
            }
        });
    }
}
