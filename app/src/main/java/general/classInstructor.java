package general;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.asus.klasseandroid.R;
import com.example.asus.klasseandroid.quiz;

import announcements.InstructorAnnounceMain;
import chatroom.ChatRoomInstructor;
import feedback.instructorFeedback.ChooseSlidesActivity;

public  class classInstructor extends AppCompatActivity {
    int room_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        room_id = intent.getIntExtra("id", 11);
        setContentView(R.layout.activity_class_instructor);
        Button chat = findViewById(R.id.chatbuttoninstruct);
        chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startChat();
            }
        });

        Button announce=findViewById(R.id.announceinstruct);
        Button quiz=findViewById(R.id.quizbuttoninstruct);
        quiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startQuiz();
            }
        });

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
        launch.putExtra("id",room_id);
        startActivity(launch);
    }

    public void startAnnounce() {
        Intent launch = new Intent(this, InstructorAnnounceMain.class);
        launch.putExtra("id",room_id);
        startActivity(launch);
    }

    public void startFeedback() {
        Intent launch = new Intent(this, ChooseSlidesActivity.class);
        launch.putExtra("id",room_id);
        startActivity(launch);
    }

    public void startQuiz() {
        Intent launch = new Intent(this, quiz.class);
        launch.putExtra("id",room_id);
        startActivity(launch);
    }
}
