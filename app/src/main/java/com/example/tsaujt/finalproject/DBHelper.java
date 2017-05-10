package com.example.tsaujt.finalproject;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by TsauJT on 2017/5/9.
 */

public class DBHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION=5;
    private static final String DATABASE_NAME="record.db";
    //public static final String TABLE_NAME="record";
    public static final String COLOUMN_ID="_id";
    private static DBHelper instance = null;

    public static DBHelper getInstance(Context ctx){
        if(instance==null)
            instance = new DBHelper(ctx, "record.fb", null, DBHelper.DATABASE_VERSION);
        return instance;
    }

    public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        //新增record表
        String query = "CREATE TABLE record (" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                "type UNSIGNED INTEGER NOT NULL, " +
                "money INTEGER NOT NULL, "+
                "explanation VARCHAR(255), " +
                "account UNSIGNED INTEGER NOT NULL, " +
                "time DATETIME NOT NULL);";

        db.execSQL(query);

        //新增spendtype表
        String type_query = "CREATE TABLE spendtype ("+
                "_id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, "+
                "typename VARCHAR(255) NOT NULL);";

        db.execSQL(type_query);

        ContentValues value = new ContentValues();
        value.put("typename", "早餐");
        value.put("typename", "中餐");
        value.put("typename", "晚餐");
        db.insert("spendtype", null, value);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS record");
        db.execSQL("DROP TABLE IF EXISTS spendtype");
        onCreate(db);
    }


}
