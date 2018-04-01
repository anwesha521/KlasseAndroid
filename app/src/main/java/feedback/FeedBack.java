package feedback;

import android.os.Bundle;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;

import android.view.View;

import com.example.asus.klasseandroid.R;

import feedback.WeekView;

/**
 * Created by harleen on 20/2/18.
 * This is where the slides are displayed
 * I guess the name isn't very apt :/
 */

public class FeedBack extends AppCompatActivity {
    private int room_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        Intent intent = getIntent();
        room_id = intent.getIntExtra("id", 11);

    }
    /**Called when the user taps the a pdf file name*/
    public void week1(View view){
        Intent intent = new Intent (this, WeekView.class);
        intent.putExtra("week",1);
        intent.putExtra("id",room_id);
        startActivity(intent);
    }
    public void week2(View view){
        Intent intent = new Intent (this, WeekView.class);
        intent.putExtra("week",2);
        intent.putExtra("id",room_id);
        startActivity(intent);
    }
    public void week3(View view){
        Intent intent = new Intent (this, WeekView.class);
        intent.putExtra("week",3);
        intent.putExtra("id",room_id);
        startActivity(intent);
    }


}
