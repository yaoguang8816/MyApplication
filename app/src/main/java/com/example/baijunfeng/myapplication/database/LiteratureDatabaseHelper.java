package com.example.baijunfeng.myapplication.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;

import com.example.baijunfeng.myapplication.MyApplication;
import com.example.baijunfeng.myapplication.utils.Author;

/**
 * Created by baijunfeng on 17/6/13.
 */

public class LiteratureDatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "literature.db";
    public static final int DATABASE_VERSION = 1;

    public static final String AUTHORITY = "com.yg.literature";
    public static final Uri AUTHORITY_URI = Uri.parse("content://" + AUTHORITY);

    public interface Tables {
        public static final String LITERATURE = "literature";
    }

    public interface LiteratureColumns {
        public static final String TABLE_NAME = "literature";

        public static final String LITERATURE_INDEX = "id";
        public static final String LITERATURE_AUTHOR = "author";
        public static final String LITERATURE_TITLE = "title";
        public static final String LITERATURE_FROM = "from_title";
        public static final String LITERATURE_CONTENT = "content";
        public static final String LITERATURE_ANNOTATION = "annotation";

        public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/" + TABLE_NAME);
    }

    public static final String LITERATURE = "CREATE TABLE " + LiteratureColumns.TABLE_NAME + " (" +
            LiteratureColumns.LITERATURE_INDEX + " TEXT PRIMARY KEY," +
            LiteratureColumns.LITERATURE_AUTHOR + " TEXT," +
            LiteratureColumns.LITERATURE_TITLE + " TEXT NOT NULL," +
            LiteratureColumns.LITERATURE_FROM + " TEXT," +
            LiteratureColumns.LITERATURE_CONTENT + " TEXT NOT NULL," +
            LiteratureColumns.LITERATURE_ANNOTATION + " TEXT" +
            ");";

    static LiteratureDatabaseHelper sInstance = null;
    public static LiteratureDatabaseHelper getInstance() {
//        if (sInstance == null) {
//            sInstance = new LiteratureDatabaseHelper();
//        }
//        return sInstance;
        return getInstance(MyApplication.getInstance());
    }

    public static LiteratureDatabaseHelper getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new LiteratureDatabaseHelper(context);
        }
        return sInstance;
    }

    private LiteratureDatabaseHelper() {
        super(MyApplication.getInstance(), DATABASE_NAME, null, DATABASE_VERSION);
    }

    private LiteratureDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(LITERATURE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void insertLiterature(Author.LiteratureDetail detail) {
        ContentValues cv = new ContentValues();
        cv.put(LiteratureDatabaseHelper.LiteratureColumns.LITERATURE_INDEX, detail.mId);
        cv.put(LiteratureDatabaseHelper.LiteratureColumns.LITERATURE_AUTHOR, detail.mAuthor);
        cv.put(LiteratureDatabaseHelper.LiteratureColumns.LITERATURE_TITLE, detail.mTitle);
        cv.put(LiteratureDatabaseHelper.LiteratureColumns.LITERATURE_FROM, detail.mFrom);
        cv.put(LiteratureDatabaseHelper.LiteratureColumns.LITERATURE_CONTENT, detail.mContent);
        cv.put(LiteratureDatabaseHelper.LiteratureColumns.LITERATURE_ANNOTATION, detail.mAnnotation);
        if (sInstance == null) {
            getInstance();
        }
        SQLiteDatabase sqlDB = sInstance.getWritableDatabase();
//        sqlDB.delete(LiteratureDatabaseHelper.Tables.LITERATURE, LiteratureDatabaseHelper.LiteratureColumns.LITERATURE_INDEX + "=?", new String[]{detail.mId});
        sqlDB.replace(LiteratureDatabaseHelper.Tables.LITERATURE, "", cv);
    }
}
