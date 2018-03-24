package com.example.asus.klasseandroid;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by harleen on 10/3/18.
 */

public class ViewFeedback extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_feedback);
    }



    public void uploadPdf(View view){
        Intent intent = new Intent (this, UploadSlides.class);

        startActivity(intent);

    }
    public void viewFeedback(View view){
        Intent intent = new Intent (this, Comments.class);

        startActivity(intent);

    }
}
