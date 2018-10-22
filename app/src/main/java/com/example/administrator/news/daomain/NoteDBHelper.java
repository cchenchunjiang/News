package com.example.administrator.news.daomain;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


import com.example.administrator.news.dao.Article;
import com.example.administrator.news.dao.Collect;

import java.io.Serializable;

public class NoteDBHelper extends SQLiteOpenHelper implements Serializable {
    private final static String DATABASE_NAME = "notedb";
    private final static int DATABASE_VERSION = 1;
    private final static String SQL_CREATE_DATABASE_Article = "CREATE TABLE " + Article.Articles.TABLE_NAME+"("
            +Article.Articles.COLUMN_NAME_id+"  INTEGER PRIMARY KEY AUTOINCREMENT ,"
            +Article.Articles.COLUMN_NAME_title+" Text ,"
            +Article.Articles.COLUMN_NAME_time+" Text ,"
            +Article.Articles.COLUMN_NAME_tags+" Text ,"
            +Article.Articles.COLUMN_NAME_text+" Text )";
    private final static String SQL_CREATE_DATABASE_Collect = "CREATE TABLE " + Collect.Collects.TABLE_NAME+"("
            +Collect.Collects.COLUMN_NAME_id+"  INTEGER PRIMARY KEY AUTOINCREMENT ,"
            +Collect.Collects.COLUMN_NAME_querys+"  Text ,"
            +Collect.Collects.COLUMN_NAME_explains+" Text )";




    private final static String SQL_DELETE_DATABASE_Article= "DROP TABLE IF EXISTS " + Article.Articles.TABLE_NAME;
    private final static String SQL_DELETE_DATABASE_Collect= "DROP TABLE IF EXISTS " + Collect.Collects.TABLE_NAME;

    public NoteDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_DATABASE_Article);
        db.execSQL(SQL_CREATE_DATABASE_Collect);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_DATABASE_Article);
        db.execSQL(SQL_DELETE_DATABASE_Collect);
        onCreate(db);
    }
}
