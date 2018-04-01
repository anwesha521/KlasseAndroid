package com.example.asus.klasseandroid;

/**
 * Created by ASUS on 22-03-2018.
 */

public class StudentAnalytics {
    private int week;
    private int percentage;
     private int classId;

    public StudentAnalytics(int w,int p, int c)
    {
        week=w;
        percentage=p;
        classId=c;
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
