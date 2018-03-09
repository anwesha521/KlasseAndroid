package com.example.asus.klasseandroid;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
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

public class Feedback extends AppCompatActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
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
