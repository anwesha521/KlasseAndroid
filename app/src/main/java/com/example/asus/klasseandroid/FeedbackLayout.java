package com.example.asus.klasseandroid;

public class FeedbackLayout {
    private String feedback;
    private String pdfFileName;
    private String pgNumber;
    private String time;

    public FeedbackLayout(String feedback, String pdfFileName, String pgNumber, String time){
        this.feedback = feedback;
        this.pdfFileName = pdfFileName;
        this.pgNumber = pgNumber;
        this.time = time;
    }

    public String getTime() {
        return time;
    }

    public String getPgNumber() {
        return pgNumber;
    }



    public String getFeedback() {

        return feedback;
    }



    public String getPdfFileName() {

        return pdfFileName;
    }





}