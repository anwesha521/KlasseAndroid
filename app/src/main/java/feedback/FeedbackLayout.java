package feedback;

/**
 * Created by harleen on 28/3/18.
 * This works together with the display adapter
 * to fill up the elements in each
 * recyclerView list item!
 */

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