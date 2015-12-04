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
    private final static String TABLE_NAME = "chatting_record_table";
    public final static String CHAT_NAME = "name";
    public final static String CHAT_DATE = "date";
    public final static String CHAT_TEXT = "text";

    public ChatDB(Context context) {
// TODO Auto-generated constructor stub
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    //create table
    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE " + TABLE_NAME + " (" + "ls" +
                "_id"
                + " INTEGER primary key autoincrement, " + CHAT_NAME + " text, "+ CHAT_DATE +" text, "+CHAT_TEXT+" text);";
        db.execSQL(sql);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql = "DROP TABLE IF EXISTS " + TABLE_NAME;
        db.execSQL(sql);
        onCreate(db);
    }

    public Cursor select() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, null, null, null, null, null, null);
        return cursor;
    }
    //insert
    public long insert(String s_name,String s_date, String s_text )
    {
        SQLiteDatabase db = this.getWritableDatabase();
/* ContentValues */
        ContentValues cv = new ContentValues();
        cv.put(CHAT_NAME, s_name);
        cv.put(CHAT_DATE, s_date);
        cv.put(CHAT_TEXT,s_text);
        long row = db.insert(TABLE_NAME, null, cv);
        return row;
    }
    //delete
    public void delete(String id)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        String where = "chat_id" + " = ?";
        String[] whereValue ={id};
        db.delete(TABLE_NAME, where, whereValue);
    }
    //update
    public void update(int id, String s_name,String s_date, String s_text)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        String where = "chat_id" + " = ?";
        String[] whereValue = { Integer.toString(id) };

        ContentValues cv = new ContentValues();
        cv.put(CHAT_NAME, s_name);
        cv.put(CHAT_DATE, s_date);
        cv.put(CHAT_TEXT,s_text);
        db.update(TABLE_NAME, cv, where, whereValue);
    }
}
