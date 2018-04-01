package com.example.asus.klasseandroid;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.graphics.Bitmap;

/**
 * Created by harleen on 19/2/18.
 */
//the class name is the database table name
    //so this database table is called Student
    //table names are case insensitive
@Entity
public class Student {
    @PrimaryKey (autoGenerate = true)
    public int id;

    //Room will create a column for each field
    //defined in the entity
    //it must be public OR you need to provide
    //getters and setters based on JavaBeans conventions
    public String userName;
    public String emailID;
    public String password;
    public String [] classes;
    public long studentID;

    @Ignore
    Bitmap picture;
}
