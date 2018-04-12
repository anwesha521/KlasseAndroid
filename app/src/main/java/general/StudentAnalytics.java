package general;

/**
 * Created by ASUS on 22-03-2018.
 */

public class StudentAnalytics {
    private int week;
    private int percentage;
     private int classId;

    public int getCount() {
        return count;
    }

    private int count;

    public StudentAnalytics(int w,int p, int c)
    {
        week=w;
        percentage=p;
        classId=c;
        count=0;
    }



    public int getAndSet()
    {
        count+=1;
        return count;
    }

    public int getWeek() {
        return week;
    }

    public void setWeek(int week) {
        this.week = week;
    }

    public int getPercentage() {
        return percentage;
    }

    public void setPercentage(int percentage) {
        this.percentage = percentage;
    }

    public int getClassId() {
        return classId;
    }

    public void setClassId(int classId) {
        this.classId = classId;
    }



}
