package com.example.asus.klasseandroid;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

/**
 * Created by harleen on 10/3/18.
 * this is a temp file to showcase directory--
 * will switch over to a more dynamic directory format soon!
 */

public class WeekView  extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.week_view);

    }
    /**Called when the user taps the a pdf file name*/
    public void pset1(View view){
        Intent intent = new Intent (this, ViewPdf.class);
        String fileName = "ps1.pdf";
        intent.putExtra("EXTRA_MESSAGE", fileName);
        startActivity(intent);
    }
    public void pset2(View view){
        Intent intent = new Intent (this, ViewPdf.class);
        String fileName = "ps2.pdf";
        intent.putExtra("EXTRA_MESSAGE", fileName);
        startActivity(intent);
    }
}
