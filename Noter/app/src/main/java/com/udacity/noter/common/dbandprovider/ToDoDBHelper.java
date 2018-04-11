package com.udacity.noter.common.dbandprovider;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;


/**
 * Created by Eslam.Mahmoud on 12/06/2017.
 */
//This file to define Database contract (Table name and column names)
class ToDoDBHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "todoList.db";

    //Table name and column names
    static final String TABLE_NAME = "todos_table";
    static final String TODO_ID = "todo_id";
    static final String TODO_TITLE = "todo_title";
    static final String TODO_CREATED_AT = "todo_created_at";

    static final String AUTHORITY = "com.udacity.noter";

    static final Uri BASE_CONTENT_URI = Uri.parse("content://" + AUTHORITY);

    static final Uri BASE_CONTENT = BASE_CONTENT_URI.buildUpon().appendPath(TABLE_NAME).build();

    private static final int DATABASE_VERSION = 2;

    private static final String DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;

    ToDoDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }

    //To create table
    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            String CREATE_TABLE = "CREATE TABLE " +
                    TABLE_NAME + " ("
                    + TODO_ID + " INTEGER PRIMARY KEY, "
                    + TODO_TITLE + " VARCHAR(255) , "
                    + TODO_CREATED_AT + " VARCHAR(255));";
            db.execSQL(CREATE_TABLE);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        try {
            db.execSQL(DROP_TABLE);
            onCreate(db);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}