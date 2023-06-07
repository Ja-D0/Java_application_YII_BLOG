package com.example.blog;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.core.database.CursorWindowCompat;

import java.util.ArrayList;
import java.util.Arrays;

class DBController {
    private static final String DATABASE_NAME = "BLOG.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_ADS = "ads";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_TITLE = "title";
    private static final String COLUMN_DESCRIPTION = "description";
    private static final String COLUMN_AUTHOR = "author";
    private static final String COLUMN_CATEGORY = "category";
    private static final String COLUMN_STATUS = "status";
    private static final String COLUMN_PICTURE = "picture";
    private static final String COLUMN_CREATED_AT = "created_at";
    private static final String COLUMN_UPDATED_AT = "updated_at";


    private static final int NUM_COLUMN_ID = 0;
    private static final int NUM_COLUMN_TITLE = 1;
    private static final int NUM_COLUMN_DESCRIPTION = 2;
    private static final int NUM_COLUMN_AUTHOR = 3;
    private static final int NUM_COLUMN_CATEGORY = 4;
    private static final int NUM_COLUMN_STATUS = 5;
    private static final int NUM_COLUMN_PICTURE = 5;
    private static final int NUM_COLUMN_CREATED_AT = 6;
    private static final int NUM_COLUMN_UPDATED_AT = 7;

    private SQLiteDatabase db;

    DBController(Context context) {
        OpenHelper openHelper = new OpenHelper(context);
        db = openHelper.getWritableDatabase();
    }

    protected void insert(Post post) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_TITLE, post.getTitle());
        contentValues.put(COLUMN_DESCRIPTION, post.getDescription());
        contentValues.put(COLUMN_AUTHOR, post.getAuthor());
        contentValues.put(COLUMN_CATEGORY, post.getCategory());
        contentValues.put(COLUMN_STATUS, post.getStatus());
        contentValues.put(COLUMN_CREATED_AT, post.getCreated_at());
        contentValues.put(COLUMN_UPDATED_AT, post.getUpdated_at());
        db.insert(TABLE_ADS, null, contentValues);
    }

    protected void insert(ArrayList<Post> listPosts) {
        ContentValues contentValues = new ContentValues();
        for (Post post: listPosts) {
            contentValues.put(COLUMN_TITLE, post.getTitle());
            contentValues.put(COLUMN_DESCRIPTION, post.getDescription());
            contentValues.put(COLUMN_AUTHOR, post.getAuthor());
            contentValues.put(COLUMN_CATEGORY, post.getCategory());
            contentValues.put(COLUMN_STATUS, post.getStatus());
            contentValues.put(COLUMN_CREATED_AT, post.getCreated_at());
            contentValues.put(COLUMN_UPDATED_AT, post.getUpdated_at());
            db.insert(TABLE_ADS, null, contentValues);
            contentValues.clear();
        }
    }
    protected Post select(int post_id){
        String query = "SELECT * FROM " + TABLE_ADS + " WHERE " + COLUMN_ID + " = " + post_id;
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToNext();
        return new Post(cursor.getInt(NUM_COLUMN_ID), cursor.getString(NUM_COLUMN_TITLE),
                cursor.getString(NUM_COLUMN_DESCRIPTION),
                cursor.getString(NUM_COLUMN_AUTHOR),
                cursor.getString(NUM_COLUMN_CREATED_AT),
                cursor.getString(NUM_COLUMN_UPDATED_AT));
    }
    protected ArrayList<Post> selectAll(){
        ArrayList<Post> listPosts = new ArrayList<Post>();
        String query = "SELECT * FROM " + TABLE_ADS;
        Cursor cursor = db.rawQuery(query, null);
        Log.i("SQL", Arrays.toString(cursor.getColumnNames()), new Throwable());
        while (cursor.moveToNext()){
            Post post = new Post(cursor.getInt(NUM_COLUMN_ID), cursor.getString(NUM_COLUMN_TITLE),
                    cursor.getString(NUM_COLUMN_DESCRIPTION),
                    cursor.getString(NUM_COLUMN_AUTHOR),
                    cursor.getString(NUM_COLUMN_CREATED_AT),
                    cursor.getString(NUM_COLUMN_UPDATED_AT));
            listPosts.add(post);
        }
        cursor.close();
        Log.i("SQL", Arrays.toString(cursor.getColumnNames()), new Throwable());
        return listPosts;
    }

    protected int update(Post post){
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_TITLE, post.getTitle());
        contentValues.put(COLUMN_DESCRIPTION, post.getDescription());
        contentValues.put(COLUMN_AUTHOR, post.getAuthor());
        contentValues.put(COLUMN_UPDATED_AT, post.getUpdated_at());
        return db.update(TABLE_ADS, contentValues, COLUMN_ID + " = ?", new String[] {String.valueOf(post.getId())});
    }

    protected void deleteAll() {
        db.delete(TABLE_ADS, null, null);
    }

    protected void delete(long id) {
        db.delete(TABLE_ADS, COLUMN_ID + " = ?", new String[] { String.valueOf(id) });
    }


    private static class OpenHelper extends SQLiteOpenHelper {
        public OpenHelper(@Nullable Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            String query = "CREATE TABLE " + TABLE_ADS + " ( " +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_TITLE + " TEXT, " +
                    COLUMN_DESCRIPTION + " TEXT, " +
                    COLUMN_AUTHOR + " TEXT, " +
                    COLUMN_CATEGORY + " TEXT, " +
                    COLUMN_STATUS + " TEXT, " +
                    COLUMN_CREATED_AT + " TEXT, " +
                    COLUMN_UPDATED_AT + " TEXT);";
            db.execSQL(query);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_ADS);
            onCreate(db);
        }
    }
}
