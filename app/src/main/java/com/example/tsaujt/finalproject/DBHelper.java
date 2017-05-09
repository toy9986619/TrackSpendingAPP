package com.example.tsaujt.finalproject;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by TsauJT on 2017/5/9.
 */

public class DBHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION=1;
    private static final String DATABASE_NAME="record.db";
    public static final String TABLE_NAME="record";
    public static final String COLOUMN_ID="_id";


    public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String query = "CREATE TABLE record (" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT  NOT NULL, " +
                "type UNSIGNED INTEGER NOT NULL, " +
                "money INTEGER NOT NULL, "+
                "explanation VARCHAR(255), " +
                "account UNSIGNED INTEGER NOT NULL, " +
                "time DATETIME NOT NULL);";

        db.execSQL(query);

        /*query="ALTER TABLE `record`" +
                " ADD PRIMARY KEY (`id`);";
        db.execSQL(query);

        query="ALTER TABLE `record`" +
                "MODIFY `id` int(10) unsigned NOT NULL AUTO_INCREMENT;";
        db.execSQL(query);*/
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(db);
    }
}
