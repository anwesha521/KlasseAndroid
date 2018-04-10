package feedback;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.asus.klasseandroid.R;

/**
 * Created by harleen on 10/3/18.
 * This page lets the instructor choose
 * if he wants to upload slides or
 * view the feedback that the students have written
 */

public class ChooseSlidesActivity extends AppCompatActivity {
    int room_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent=getIntent();
        room_id=intent.getIntExtra("id",11);
        setContentView(R.layout.activity_choose_slides_activity);
    }



    public void uploadPdf(View view){
        Intent intent = new Intent (this, UploadSlides.class);
        intent.putExtra("id",room_id);

        startActivity(intent);

    }
    public void viewFeedback(View view){
        Intent intent = new Intent (this, Comments.class);
        intent.putExtra("id",room_id);

        startActivity(intent);

    }
}
