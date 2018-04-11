package com.udacity.noter.common.dbandprovider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Created by eslam on 12/2/17.
 */

//Save data in a content provider (Like database but shared between apps)

public class ToDosContentProvider extends ContentProvider {

    private ToDoDBHelper mDBHelper;
    public static final int GROUP_TODOS = 1;

    private static final UriMatcher sUriMatcher = buildUriMatcher();

    public static UriMatcher buildUriMatcher() {
        UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

        uriMatcher.addURI(ToDoDBHelper.AUTHORITY, ToDoDBHelper.TABLE_NAME, GROUP_TODOS);

        return uriMatcher;
    }

    @Override
    public boolean onCreate() {
        Context context = getContext();
        mDBHelper = new ToDoDBHelper(context);
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        Cursor cursor = null;
        SQLiteDatabase db = mDBHelper.getReadableDatabase();

        switch (sUriMatcher.match(uri)) {
            case GROUP_TODOS:
                cursor = db.rawQuery(selection, selectionArgs);
                break;
        }

        if (cursor != null && getContext() != null)
            cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        Uri returnedUri = null;
        final SQLiteDatabase db = mDBHelper.getWritableDatabase();
        switch (sUriMatcher.match(uri)) {
            case GROUP_TODOS:
                long id = db.insert(ToDoDBHelper.TABLE_NAME, null, contentValues);
                if (id > 0)
                    returnedUri = ContentUris.withAppendedId(ToDoDBHelper.BASE_CONTENT, id);
                break;
        }

        if (getContext() != null)
            getContext().getContentResolver().notifyChange(uri, null);

        return returnedUri;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        final SQLiteDatabase db = mDBHelper.getWritableDatabase();
        int numOfRowsAffected = 0;
        switch (sUriMatcher.match(uri)) {
            case GROUP_TODOS:
                numOfRowsAffected = db.delete(ToDoDBHelper.TABLE_NAME, selection, null);
                break;
        }

        if (getContext() != null)
            getContext().getContentResolver().notifyChange(uri, null);

        return numOfRowsAffected;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String s, @Nullable String[] strings) {
        return 0;
    }
}
