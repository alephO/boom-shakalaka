//package com.brandeis.zhongzhongzhong.pocketguide;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.Hashtable;
//import android.content.ContentValues;
//import android.content.Context;
//import android.database.Cursor;
//import android.database.DatabaseUtils;
//import android.database.sqlite.SQLiteOpenHelper;
//import android.database.sqlite.SQLiteDatabase;
//import android.util.Log;
//
//public class DBHelper extends SQLiteOpenHelper{
//    public static final String DATABASE_NAME = "boom.db";
//    public static final String NATION_TABLE_NAME = "nation";
//    public static final String NATION_COLUMN_ID = "nation_id";
//    public static final String NATION_COLUMN_NAME = "nation_name";
//    public static final String TAG = "create?";
//
//    public DBHelper(Context context){
//        super(context, DATABASE_NAME, null, 1);
//    }
//
//    @Override
//    public void onCreate(SQLiteDatabase db) {
//        db.execSQL(
//                "create table nation" +
//                        "(nation_id integer primary key autoincrement, nation_name text)"
//        );
//        Log.d(TAG, "onCreate ");
//    }
//
//    @Override
//    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//        db.execSQL("DROP TABLE IF EXISTS nation");
//        onCreate(db);
//    }
//
//    public Cursor getNation(){
//        SQLiteDatabase db = this.getReadableDatabase();
//        Cursor res = db.rawQuery("select nation_name from nation",null);
//        return res;
//    }
//    public int numberOfNations(){
//        SQLiteDatabase db = this.getReadableDatabase();
//        int numRows = (int) DatabaseUtils.queryNumEntries(db, NATION_TABLE_NAME);
//        return numRows;
//    }
//
//    public ArrayList<String> getAllNations(){
//        ArrayList<String> array_list = new ArrayList<String>();
//
//        SQLiteDatabase db = this.getReadableDatabase();
//        //Cursor res = this.getNation();
//        Cursor res = db.rawQuery("select nation_name from nation", null);
//        res.moveToFirst();
//        int numRows = (int) DatabaseUtils.queryNumEntries(db, NATION_TABLE_NAME);
//        Log.d(TAG, "nation count: " + numRows);
//
//        while(res.isAfterLast() == false){
//            array_list.add(res.getString(res.getColumnIndex(NATION_COLUMN_NAME)));
//            Log.d(TAG, "Value: " + res.getString(res.getColumnIndex(NATION_COLUMN_NAME)));
//            res.moveToNext();
//        }
//        Log.d(TAG, "get all nations ");
//        return array_list;
//    }
//
//}
