package com.brandeis.zhongzhongzhong.pocketguide;

/**
 * Created by ChaoLiu's Computer on 2015/12/2.
 */
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


import android.database.sqlite.SQLiteOpenHelper;
public class ChatDB extends SQLiteOpenHelper {
    private final static String DATABASE_NAME = "Chat.db";
    private final static int DATABASE_VERSION = 1;
    private final static String TABLE_NAME_CHAT_RECORD = "chatting_record_table";
    private final static String TABLE_NAME_CHAT_NAME= "chatting_name_table";
    public final static String CHAT_NAME = "name";
    public final static String CHAT_DATE = "date";
    public final static String CHAT_TEXT = "text";
    public final static String USER_ID = "uid";


    public ChatDB(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    //create table
    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql1 = "CREATE TABLE " + TABLE_NAME_CHAT_RECORD + " (" + "_id"
                + " INTEGER primary key autoincrement, " + CHAT_NAME + " text, "+ CHAT_DATE +" text, "+CHAT_TEXT+" text);";

        String sql2 = "CREATE TABLE " + TABLE_NAME_CHAT_NAME + " (" + "_id"
                + " INTEGER primary key autoincrement, " + CHAT_NAME + " text, "+
                  USER_ID + " INTEGER);";

        db.execSQL(sql1);
        db.execSQL(sql2);
    }

    public void CreateTable(SQLiteDatabase db,String pass_name){
        String sql = "CREATE TABLE if not exists " + pass_name + "  (" + "_id"
                + " INTEGER primary key autoincrement, " + CHAT_NAME + " text, "+ CHAT_DATE +" text, "+CHAT_TEXT+" text);";
        db.execSQL(sql);

    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql = "DROP TABLE IF EXISTS " + TABLE_NAME_CHAT_RECORD;
        db.execSQL(sql);
        onCreate(db);
    }

    public Cursor select1(String pass_name) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(pass_name, null, null, null, null, null, null);
        return cursor;
    }

    public Cursor select2() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME_CHAT_NAME, null, null, null, null, null, null);
        return cursor;
    }
    //insert
    public long insert1(String pass_name,String s_name,String s_date, String s_text )   //table for chatting record
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(CHAT_NAME, s_name);
        cv.put(CHAT_DATE, s_date);
        cv.put(CHAT_TEXT, s_text);
        long row = db.insert(pass_name, null, cv);
        return row;
    }

    public long insert2(String s_name, int uid)                          //table for chatting name
    {
        SQLiteDatabase db = this.getWritableDatabase();
/* ContentValues */
        ContentValues cv = new ContentValues();
        cv.put(CHAT_NAME, s_name);
        cv.put(USER_ID, uid);
        long row = db.insert(TABLE_NAME_CHAT_NAME, null, cv);
        return row;
    }
    //delete
    public void delete(String id, String tablename)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        String where = "_id" + " = ?";
        String[] whereValue ={id};
        db.delete(tablename, where, whereValue);
    }
    //update
    public void update1(int id, String s_name,String s_date, String s_text)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        String where = "_id" + " = ?";
        String[] whereValue = { Integer.toString(id) };

        ContentValues cv = new ContentValues();
        cv.put(CHAT_NAME, s_name);
        cv.put(CHAT_DATE, s_date);
        cv.put(CHAT_TEXT,s_text);
        db.update(TABLE_NAME_CHAT_RECORD, cv, where, whereValue);
    }

    public void update2(int id, String s_name)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        String where = "_id" + " = ?";
        String[] whereValue = { Integer.toString(id) };

        ContentValues cv = new ContentValues();
        cv.put(CHAT_NAME, s_name);
        db.update(TABLE_NAME_CHAT_NAME, cv, where, whereValue);
    }
}
