package com.udacity.noter.common.dbandprovider;


import android.content.ContentValues;
import android.content.Context;
import android.content.CursorLoader;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;


import com.udacity.noter.model.ToDo;

import java.util.ArrayList;

/**
 * Created by Eslam.Mahmoud on 12/02/2017.
 */

public class ToDosDBController {
    private Context mContext;
    private ArrayList<ToDo> listOfToDos = new ArrayList<>();
    private static ToDoDBHelper todoHelper;

    public ToDosDBController(Context context) {
        this.mContext = context;
        if (todoHelper == null)
            this.todoHelper = new ToDoDBHelper(context);
    }

    public long addNewToDo(ToDo todo) {
        SQLiteDatabase db = todoHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(todoHelper.TODO_TITLE, todo.getTitle());
        contentValues.put(todoHelper.TODO_CREATED_AT, todo.getCreatedAt());

        Uri returnUri = null;
        if (mContext != null && mContext.getContentResolver() != null)
            returnUri = mContext.getContentResolver().insert(ToDoDBHelper.BASE_CONTENT, contentValues);
        return returnUri == null ? -1 : 1;
    }

    public int deleteToDo(int movieId) {
        String select = ToDoDBHelper.TODO_ID + " = " + movieId;
        if (mContext != null && mContext.getContentResolver() != null)
            return mContext.getContentResolver().delete(ToDoDBHelper.BASE_CONTENT, select, null);
        return -1;
    }

    public CursorLoader getMyToDos() {
        CursorLoader loader = null;
        String select = "Select * FROM " + todoHelper.TABLE_NAME;
   /*     if (mContext != null && mContext.getContentResolver() != null)
            c = mContext.getContentResolver().query(ToDoDBHelper.BASE_CONTENT, null, select, null, null);
*/
        if (mContext != null && mContext.getContentResolver() != null)
            loader = new CursorLoader(mContext,
                    ToDoDBHelper.BASE_CONTENT, null, select, null, null);

        return loader;
    }

    public ArrayList<ToDo> getMyNotesForWidget() {
        Cursor cursor = null;
        ArrayList<ToDo> listToDos = new ArrayList<>();
        String select = "Select * FROM " + todoHelper.TABLE_NAME;
        if (mContext != null && mContext.getContentResolver() != null)
            cursor = mContext.getContentResolver().query(ToDoDBHelper.BASE_CONTENT, null, select, null, null);


        if (cursor != null) {
            ToDo tempToDo;
            ArrayList<ToDo> tempListToDos = new ArrayList<>();
            while (cursor.moveToNext()) {
                tempToDo = new ToDo();
                tempToDo.setId(cursor.getString(0));
                tempToDo.setTitle(cursor.getString(1));
                tempToDo.setCreatedAt(cursor.getString(2));
                tempListToDos.add(tempToDo);
            }
            cursor.close();
            listToDos.addAll(tempListToDos);
        }

        return listToDos;
    }
}
