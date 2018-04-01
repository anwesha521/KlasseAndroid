package announcements;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.asus.klasseandroid.R;

public class InstructorAnnounceMain extends AppCompatActivity {
    private int room_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instructor_announce_main);
        Intent intent = getIntent();
        room_id = intent.getIntExtra("id", 11);
        Button make=findViewById(R.id.makeAnnounce);
        Button v=findViewById(R.id.viewAnnounce);
        make.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startMake();
            }
        });
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startView();
            }
        });

    }
    public void startMake()
    {
        Intent launch = new Intent(this, InstructorAnnounce.class);
        launch.putExtra("id",room_id);
        startActivity(launch);

    }

    public void startView()
    {
        Intent launch = new Intent(this, InstructorViewAnnounce.class);
        launch.putExtra("id",room_id);
        startActivity(launch);
    }
}
