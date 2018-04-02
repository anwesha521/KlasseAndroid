package com.example.asus.klasseandroid;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import announcements.StudentAnnounce;
import chatroom.ChatRoom;
import feedback.FeedBack;

public class classStudent extends AppCompatActivity {
    
    Button chat;
    Button announce;
    Button slides;
    int room_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        room_id = intent.getIntExtra("id", 11);
        setContentView(R.layout.activity_class_student);
        chat= findViewById(R.id.chatbutton);

        Button announce=findViewById(R.id.announcebutton);
        announce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startAnnounce();
            }
        });

        Button quiz = findViewById(R.id.quizbutton);
        quiz.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                startQuiz();
            }
        });

        Button feedback = findViewById(R.id.slidebutton);
        feedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startFeedback();
            }
        });


        chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startChat();
            }
        });
        announce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startAnnounce();
            }
        });

        //Here's the feedback/slides portion!
        slides = findViewById(R.id.slidebutton);
        slides.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startSlides();
            }
        });




    }
    public void startChat()
    {
        Intent launch = new Intent(this, ChatRoom.class);
        launch.putExtra("id",room_id);
        startActivity(launch);
    }

    public void startSlides(){
        Intent launch = new Intent(this, FeedBack.class);
        launch.putExtra("id",room_id);
        startActivity(launch);
    }
    public void startAnnounce()
    {

        Intent launch = new Intent(this, StudentAnnounce.class);
        launch.putExtra("id",room_id);
        startActivity(launch);


    }
    
    public void startQuiz()
    {
        Intent launch = new Intent(this,StudentViewQuiz.class);
        launch.putExtra("id",room_id);
        startActivity(launch);
    }
    
    public void startFeedback()
    {
        Intent launch = new Intent(this, FeedBack.class);
        launch.putExtra("id",room_id);
        startActivity(launch);
    }
    }

