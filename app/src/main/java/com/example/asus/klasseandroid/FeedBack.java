package com.example.asus.klasseandroid;

import android.app.Activity;
import android.os.Bundle;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.listener.OnLoadCompleteListener;
import com.github.barteksc.pdfviewer.listener.OnPageChangeListener;
import com.github.barteksc.pdfviewer.scroll.DefaultScrollHandle;
import com.shockwave.pdfium.PdfDocument;

import java.util.List;

/**
 * Created by harleen on 20/2/18.
 * This is where the slides are displayed
 * I guess the name isn't very apt :/
 */

public class FeedBack extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);

    }
    /**Called when the user taps the a pdf file name*/
    public void week1(View view){
        Intent intent = new Intent (this, WeekView.class);
        startActivity(intent);
    }
    public void week2(View view){
        Intent intent = new Intent (this, WeekView.class);
        startActivity(intent);
    }
    public void week3(View view){
        Intent intent = new Intent (this, WeekView.class);
        startActivity(intent);
    }
    public void week4(View view){
        Intent intent = new Intent (this, WeekView.class);
        startActivity(intent);
    }
    public void week5(View view){
        Intent intent = new Intent (this, WeekView.class);
        startActivity(intent);
    }

}
