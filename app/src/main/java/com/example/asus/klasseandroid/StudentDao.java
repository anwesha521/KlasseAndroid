package com.example.asus.klasseandroid;

import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;
import android.arch.persistence.room.Dao;

import java.util.List;

//ROOM DOESN'T ALLOW DATABASE ACCESS ON THE MAIN THREAD
// BECAUSE IT MIGHT LOCK THE UI FOR A LONG PERIOD OF TIME
//call allowMainThreadQueries() on the builder to change this

/**
 * Created by harleen on 20/2/18.
 * This file is a data access object
 * it's the main component of Room
 * each DAO includes methods that offer abstract access
 * to the dataase
 */

// using a DAO class instead of query builders or
    //direct queries allows you to
    //separate different components of your
    //database architecture
    //+ they allow you to easily mock
    //database access as you test your app

@Dao
public interface StudentDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insertStudent(Student ... student);

   @Update //returns num rows updated in db
    public int updateStudent(Student ... student);

   @Delete //returns num rows deleted in db
    public int deleteStudent(Student ... student);

   //main annotation used in DAO classes
    //can perform read/write operations on a database
    //each methos is verified at compile time so
    //issues: compilation error>runtime failure

    //warning if field in return obj does not match
    //corrsp column names
   @Query("SELECT * FROM student")
    public Student[] loadAllStudents();

   //when the query is processed at compile time,
    //ROom matches the :username bind paramter with the
    //userName method parameter--> names must match!
   @Query("SELECT password FROM student WHERE username = :userName")
    public String passwordChecker(String userName);

   @Query("SELECT classes FROM student WHERE userName = :userName")
    public String[] obtainClasses(String userName);
}
