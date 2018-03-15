package com.example.asus.klasseandroid;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.listener.OnLoadCompleteListener;
import com.github.barteksc.pdfviewer.listener.OnPageChangeListener;
import com.github.barteksc.pdfviewer.scroll.DefaultScrollHandle;
import com.shockwave.pdfium.PdfDocument;

import java.util.List;

/**
 * Created by harleen on 20/2/18.
 */

public class Feedback extends Activity implements OnPageChangeListener,OnLoadCompleteListener {
    private static final String TAG = Feedback.class.getSimpleName();
    public static final String SAMPLE_FILE = "SolutionToPS1.pdf";
    PDFView pdfView;
    Integer pageNumber = 0;
    String pdfFileName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);

        pdfView = (PDFView) findViewById(R.id.pdfView);
        displayFromAsset(SAMPLE_FILE);
    }

    private void displayFromAsset(String assetFileName){
        pdfFileName = assetFileName;

        pdfView.fromAsset(SAMPLE_FILE)
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
        pageNumber= page;
        setTitle(String.format("%s %s / %s", pdfFileName, page+1, pageCount));

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
