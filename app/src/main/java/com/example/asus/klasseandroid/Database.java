package com.example.asus.klasseandroid;

import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

/**
 * Created by harleen on 19/2/18.
 * Following singleton design pattern when initiating
 * A database object becayse as each RoomDatabase instance
 * is fairly expensive and you rarely need access
 * to multiple instances!
 */
//@Database(entities = {Student.class}, version = 1)
public abstract class Database extends RoomDatabase {
    private static Database INSTANCE;

    public abstract StudentDao studentDao();

    public static Database getDatabase(Context context) {
        if (INSTANCE == null) {
            INSTANCE =
                    Room.databaseBuilder(context.getApplicationContext(),
                            Database.class, "student-database")
                            .allowMainThreadQueries()
                            .build();
        }
        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }
}


//     must perform queries on worker thread, otherwise your application will crash.

//    private static User addUser(final AppDatabase db, User user) {
//        db.userDao().insertAll(user);
//        return user;
//    }
//
//    private static void populateWithTestData(AppDatabase db) {
//        User user = new User();
//        user.setFirstName("Ajay");
//        user.setLastName("Saini");
//        user.setAge(25);
//        addUser(db, user);
//    }
