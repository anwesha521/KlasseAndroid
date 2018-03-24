package com.example.asus.klasseandroid;


/**
 * Created by harleen on 28/2/18.
 * This handles the viewing of the pdf
 * As well as the feedback portion
 * there's a popup menu that they can use to
 * post feedback based on the slides that they're on!
 */

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.listener.OnLoadCompleteListener;
import com.github.barteksc.pdfviewer.listener.OnPageChangeListener;
import com.github.barteksc.pdfviewer.scroll.DefaultScrollHandle;
import com.shockwave.pdfium.PdfDocument;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ViewPdf extends Activity implements OnPageChangeListener,OnLoadCompleteListener {

    private static final String TAG = ViewPdf.class.getSimpleName();
    public static String FILE;
    PDFView pdfView;
    Integer pageNumber = 1;
    String pdfFileName;
    String feedback;
    String url = "http://192.168.0.121/feedback.php";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pdf_view);
        Intent intent = getIntent();
        FILE = intent.getStringExtra("EXTRA_MESSAGE");


        pdfView = (PDFView) findViewById(R.id.pdfView);
        displayFromAsset(FILE);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.feedback);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Send FeedBack!", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

                // Build an AlertDialog
                AlertDialog.Builder builder = new AlertDialog.Builder(ViewPdf.this);

                LayoutInflater inflater = getLayoutInflater();
                View dialogView = inflater.inflate(R.layout.send_feedback_popup,null);

                // Specify alert dialog is not cancelable/not ignorable
                builder.setCancelable(false);

                // Set the custom layout as alert dialog view
                builder.setView(dialogView);

                // Get the custom alert dialog view widgets reference
                Button btn_positive = (Button) dialogView.findViewById(R.id.dialog_positive_btn);
                Button btn_negative = (Button) dialogView.findViewById(R.id.dialog_negative_btn);
                final EditText text = (EditText) dialogView.findViewById(R.id.enter_feedback);

                // Create the alert dialog
                final AlertDialog dialog = builder.create();

                // Set positive/yes button click listener
                btn_positive.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Dismiss the alert dialog

                        dialog.cancel();
                        feedback = text.getText().toString();
                        ///////////I NEED TO FIX THE PAGE NUMBER ISSUE!!!///////

                        submitFeedback(feedback, pdfFileName);
                        //Toast.makeText(getApplication(), feedback, Toast.LENGTH_SHORT).show();

                    }
                });

                // Set negative/no button click listener
                btn_negative.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Dismiss/cancel the alert dialog
                        //dialog.cancel();
                        dialog.dismiss();
                        Toast.makeText(getApplication(),
                                "No button clicked", Toast.LENGTH_SHORT).show();
                    }
                });

                // Display the custom alert dialog on interface
                dialog.show();
            }
        });


    }


    private void submitFeedback(final String comments, final String filename) {
        RequestQueue MyRequestQueue = Volley.newRequestQueue(ViewPdf.this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(ViewPdf.this, "SUBMITTED!", Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Failed", Toast.LENGTH_SHORT).show();
                //Log.e("VOLLEY", error.getMessage());
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("feedback", comments);
                params.put("pdfFileName",filename);


                return params;
            }
        };

        Toast.makeText(this, comments + filename, Toast.LENGTH_SHORT).show();
        MyRequestQueue.add(stringRequest);

    }








    private void displayFromAsset(String assetFileName){
        pdfFileName = assetFileName;

        pdfView.fromAsset(FILE)
                .defaultPage(pageNumber)
                .enableSwipe(true)
                .swipeHorizontal(false)
                .onPageChange(this)
                .enableAnnotationRendering(true)
                .onLoad(this)
                .scrollHandle(new DefaultScrollHandle(this))
                .load();

    }

    @Override
    public void onPageChanged(int page, int pageCount){
        setTitle(String.format("%s %s / %s", pdfFileName, page+1, pageCount));
        pageNumber= page;


    }

    @Override
    public void loadComplete(int nbPages){
        PdfDocument.Meta meta = pdfView.getDocumentMeta();
        printBookmarksTree(pdfView.getTableOfContents(), "-");

    }

    public void printBookmarksTree(List<PdfDocument.Bookmark> tree, String sep){
        for(PdfDocument.Bookmark b : tree){
            Log.e(TAG, String.format("%s %s, p %d", sep, b.getTitle(), b.getPageIdx()));
            if (b.hasChildren()){
                printBookmarksTree(b.getChildren(), sep + "-");
            }
        }
    }


}
