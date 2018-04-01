package announcements;

/**
 * Created by ASUS on 09-03-2018.
 */

public class Announcements {
    private String prof;
    private String content;
    private int classnum;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    private int id;

    public int getClassnum() {
        return classnum;
    }

    public void setClassnum(int classnum) {
        this.classnum = classnum;
    }




    public String getProf() {
        return prof;
    }

    public void setProf(String prof) {
        this.prof = prof;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }


    public Announcements(String prof, String content,int c,int i)
    {       this.prof = prof;
        this.content = content;
        this.classnum=c;
        id=i;
    }
}
