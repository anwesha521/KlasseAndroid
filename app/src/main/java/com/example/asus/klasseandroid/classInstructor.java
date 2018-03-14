package com.example.asus.klasseandroid;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class classInstructor extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_instructor);
        Button chat = findViewById(R.id.chatbuttoninstruct);
        chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startChat();
            }
        });
        Button announce = findViewById(R.id.announceinstruct);
        Button quiz = findViewById(R.id.quizbuttoninstruct);
        announce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startAnnounce();
            }
        });
        Button viewFeedback = findViewById(R.id.slidesbuttoninstruct);
        viewFeedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startFeedback();
            }
        });


    }

    public void startChat() {
        Intent launch = new Intent(this, ChatRoomInstructor.class);
        startActivity(launch);
    }

    public void startAnnounce() {
        Intent launch = new Intent(this, InstructorAnnounce.class);
        startActivity(launch);
    }

    public void startFeedback() {
        Intent launch = new Intent(this, ViewFeedback.class);
        startActivity(launch);
    }

    public void startQuiz() {
        Intent launch = new Intent(this, InstructorQuiz.class);

        startActivity(launch);
    }
}
