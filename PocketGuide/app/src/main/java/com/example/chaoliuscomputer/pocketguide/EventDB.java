package com.example.chaoliuscomputer.pocketguide;

/**
 * Created by ChaoLiu's Computer on 2015/12/3.
 */
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
public class EventDB extends SQLiteOpenHelper {
    private final static String DATABASE_NAME = "Event.db";
    private final static int DATABASE_VERSION = 1;
    private final static String TABLE_NAME = "event_record_table";
    public final static String EVENT_DATE = "date";
    public final static String EVENT_PLACE = "place";

    public EventDB(Context context) {
// TODO Auto-generated constructor stub
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    //create table
    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE " + TABLE_NAME + " ("  +
                "_id"
                + " INTEGER primary key autoincrement, " + EVENT_DATE + " text, "+ EVENT_PLACE +" text);";
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
    public long insert(String e_date,String e_place)
    {
        SQLiteDatabase db = this.getWritableDatabase();
/* ContentValues */
        ContentValues cv = new ContentValues();
        cv.put(EVENT_DATE, e_date);
        cv.put(EVENT_PLACE, e_place);
        long row = db.insert(TABLE_NAME, null, cv);
        return row;
    }
    //delete
    public void delete(String id)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        String where = "_id" + " = ?";
        String[] whereValue ={id};
        db.delete(TABLE_NAME, where, whereValue);
    }
    //update
    public void update(int id, String e_date,String e_place)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        String where = "_id" + " = ?";
        String[] whereValue = { Integer.toString(id) };

        ContentValues cv = new ContentValues();
        cv.put(EVENT_DATE, e_date);
        cv.put(EVENT_PLACE, e_place);
        db.update(TABLE_NAME, cv, where, whereValue);
    }
}