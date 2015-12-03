package com.brandeis.zhongzhongzhong.pocketguide;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
/**
 * Created by zhongzhongzhong on 12/2/15.
 */
public class SearchHistoryAdapter {
    public static final String DATABASE_NAME = "pocket.db";
    public static final String HISTORY_TABLE_NAME = "history";
    public static final String HISTORY_COLUMN_ID = "_id";
    public static final String HISTORY_COLUMN_name = "name";
    public static final String TAG = "create?:";

    private final Context mCtx;

    private DBHelper mDbHelper;
    private SQLiteDatabase mDb;

    public SearchHistoryAdapter(Context ctx){
        this.mCtx = ctx;
    }

    public SearchHistoryAdapter open(){
        mDbHelper = new DBHelper(mCtx);
        mDb = mDbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        if (mDb != null){
            mDbHelper.close();
        }
    }
    // insert a new record to database. Using contentValues to store key-value pair and interact with database.
    public long create_log(String name){
        ContentValues initiativeValue = new ContentValues();
        initiativeValue.put(HISTORY_COLUMN_name, name);
        return mDb.insert(HISTORY_TABLE_NAME, null, initiativeValue);
    }
    // delete one row from database according to it's id.
    public void delete_log(int id){
        //Cursor mCursor= null;
        mDb.execSQL("DELETE FROM 'history' WHERE _id = '" + id +"'");
    }

    // execute a "select * from table" query by using query(), return a cursor which contains the whole database data.
    public Cursor fetch_AllLog(){
        Cursor mCursor = null;
        mCursor = mDb.query(HISTORY_TABLE_NAME, new String[] {
                HISTORY_COLUMN_ID,HISTORY_COLUMN_name}, null, null, null, null, null);
        if(mCursor!= null){
            mCursor.moveToFirst();
        }
        return mCursor;
    }



    public class DBHelper extends SQLiteOpenHelper {


        public DBHelper(Context context){
            super(context, DATABASE_NAME, null, 1);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            // when it is onCreate, create a new table called "expense", there are four columns: _id, note, description and date.
            // user db.execSQL to execute a sql query.
            db.execSQL(
                    "create table history" +
                            "(_id integer primary key autoincrement, name text)"
            );
            Log.d(TAG, "onCreate");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS expense");
            onCreate(db);
        }

    }
}
