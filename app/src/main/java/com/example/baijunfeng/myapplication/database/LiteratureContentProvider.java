package com.example.baijunfeng.myapplication.database;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.util.Log;

import com.example.baijunfeng.myapplication.utils.Author;
import com.example.baijunfeng.myapplication.utils.PoetryCardContent;

/**
 * Created by baijunfeng on 17/6/13.
 * Not used currently
 */

public class LiteratureContentProvider extends ContentProvider {
    private static final String TABLE_NAME = "User";
    private static final String TAG = "LiteratureContentProvider";

    public static final int LITERATURE = 1000;
    public static final int PROJECT_MEMBER = 1001;
    public static final int MEMBER = 1002;

    private SQLiteDatabase mSqlDB;
    private LiteratureDatabaseHelper    mDBHelper;

    static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        sUriMatcher.addURI(LiteratureDatabaseHelper.AUTHORITY, LiteratureDatabaseHelper.Tables.LITERATURE, LITERATURE);
    }

    @Override
    public int delete(Uri uri, String whereClause, String[] whereArgs) {
        mSqlDB = mDBHelper.getWritableDatabase();

        Cursor c = null;
        switch (sUriMatcher.match(uri)) {
            case LITERATURE:
                mSqlDB.delete(LiteratureDatabaseHelper.Tables.LITERATURE, whereClause, whereArgs);
                break;
        }

        return 0;
    }

    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues contentvalues) {
        mSqlDB = mDBHelper.getWritableDatabase();
        long rowId = 0;
        switch (sUriMatcher.match(uri)) {
            case LITERATURE:
                rowId = mSqlDB.insert(LiteratureDatabaseHelper.Tables.LITERATURE, "", contentvalues);
                break;
//            case PROJECT_MEMBER:
//                rowId = mSqlDB.insert(DatabaseHelper.Tables.PROJECT_MEMBER, "", contentvalues);
        }

        if (rowId > 0) {
            Uri rowUri = ContentUris.appendId(LiteratureDatabaseHelper.AUTHORITY_URI.buildUpon(), rowId).build();
            getContext().getContentResolver().notifyChange(rowUri, null);
            return rowUri;
        } else {
            throw new SQLException("Failed to insert row into " + uri);
        }
    }

    public void insertLiterature(Author.LiteratureDetail detail) {
        ContentValues cv = new ContentValues();
        cv.put(LiteratureDatabaseHelper.LiteratureColumns.LITERATURE_INDEX, detail.mId);
        cv.put(LiteratureDatabaseHelper.LiteratureColumns.LITERATURE_AUTHOR, detail.mAuthor);
        cv.put(LiteratureDatabaseHelper.LiteratureColumns.LITERATURE_TITLE, detail.mTitle);
        cv.put(LiteratureDatabaseHelper.LiteratureColumns.LITERATURE_FROM, detail.mFrom);
        cv.put(LiteratureDatabaseHelper.LiteratureColumns.LITERATURE_CONTENT, detail.mContent);
        cv.put(LiteratureDatabaseHelper.LiteratureColumns.LITERATURE_ANNOTATION, detail.mAnnotation);
        SQLiteDatabase sqlDB = LiteratureDatabaseHelper.getInstance().getWritableDatabase();
//        sqlDB.delete(LiteratureDatabaseHelper.Tables.LITERATURE, LiteratureDatabaseHelper.LiteratureColumns.LITERATURE_INDEX + "=?", new String[]{detail.mId});
        sqlDB.replace(LiteratureDatabaseHelper.Tables.LITERATURE, "", cv);
    }

    @Override
    public boolean onCreate() {
        //Warning：此处必须传递getContext()获取context，测试发现，在此时MyApplication还没有调用，所以MyApplication.getInstance()为空。
        mDBHelper = LiteratureDatabaseHelper.getInstance(getContext());
        return mDBHelper != null;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        mSqlDB = mDBHelper.getWritableDatabase();

        Cursor c = null;
        switch (sUriMatcher.match(uri)) {
            case LITERATURE:
                c = mSqlDB.query(LiteratureDatabaseHelper.Tables.LITERATURE, projection, selection, selectionArgs, sortOrder, null, null);
        }
//        SQLiteDatabase db = mDBHelper.getReadableDatabase();
//        Cursor c = db.query(uri.toString(), projection, selection, selectionArgs, sortOrder, null, null);
//        qb.setTables(uri.toString());
//        Cursor c = qb.query(db, projection, selection, null, null, null, sortOrder);
//        c.setNotificationUri(getContext().getContentResolver(), uri);
        return c;
    }

    @Override
    public int update(Uri uri, ContentValues contentvalues, String s, String[] as) {
        return 0;
    }
}
