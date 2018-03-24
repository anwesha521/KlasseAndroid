package com.example.asus.klasseandroid;

/**
 * Created by ASUS on 09-03-2018.
 */

public class FeedbackLayout {
    private String feedback;
    private String pdfFileName;
    //private int pgNumber;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    private int id;




    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    public String getPdfFileName() {
        return pdfFileName;
    }

    public void setPdfFileName(String pdfFileName) {
        this.pdfFileName = pdfFileName;
    }


    public FeedbackLayout(String feeedback, String pdfFileName,int i)
    {       this.feedback = feedback;
        this.pdfFileName = getPdfFileName();
        id=i;
    }
}