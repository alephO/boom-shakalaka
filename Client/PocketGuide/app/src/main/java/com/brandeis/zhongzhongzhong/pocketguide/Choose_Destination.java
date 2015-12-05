package com.brandeis.zhongzhongzhong.pocketguide;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

public class Choose_Destination extends Activity {

    /* location list should be retrieved from database, but now just use string stored in the resource file*/
    public String [] location;
    public static final String TAG = "selected";
    ArrayAdapter<String> adapter;
    //DBHelper mydb;
    ListView mylistview;
    private SearchHistoryAdapter dbHelper;
    private SimpleCursorAdapter dataAdapter;
    private Cursor cursor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*hide title bar*/
        //this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_choose_destination);

        /* show history */
        mylistview = (ListView) findViewById(R.id.mylistView);

        dbHelper = new SearchHistoryAdapter(this);
        dbHelper.open();
        displayListView();
        /* show history end*/



        location = getResources().getStringArray(R.array.location_list);
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, location);
        //mydb = new DBHelper(this);
        //ArrayList array_list = mydb.getAllNations();
        //Log.d(TAG, array_list.get(1));
        //ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, array_list);
        AutoCompleteTextView autoCompleteTextView = (AutoCompleteTextView) findViewById(R.id.Chooselocation_autoCompleteTextView);

        autoCompleteTextView.setThreshold(2);
        autoCompleteTextView.setAdapter(adapter);

        /* finish the activity, go back to the index page*/
        Button cancel_btn = (Button) findViewById(R.id.Chooselocation_cancelbtn);
        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override

            public void onClick(View v) {
                finish();
            }
        };
        cancel_btn.setOnClickListener(onClickListener);

        /*item in ListView has been clicked*/
        autoCompleteTextView.setOnItemClickListener( new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent("com.brandeis.Fill_Information");
                /*Bundle extras = new Bundle();*/
                String text = ((TextView) view).getText().toString();
                dbHelper.create_log(text);
                //use fetch_AllLog() method defined in LogDbAdapter class

                //Log.d(TAG, text);
                //int selected = adapter.getPosition(text);
                i.putExtra("location_name", text);
                startActivity(i);
                finish();

            }
        });

        // public void onListItemClick( )

    }
    private void displayListView(){
        // use fetch_AllLog method to get all data in the database back.
        cursor = dbHelper.fetch_AllLog();
        // define column
        String[] columns = new String[] {
                SearchHistoryAdapter.HISTORY_COLUMN_name
        };
        //define int[]
        int[] to = new int[] {
                R.id.history_item
        };
        //define a new simplecursorAdapter class, sent argument to it.
        dataAdapter = new SimpleCursorAdapter(this, R.layout.history_entry, cursor, columns, to, 0){
            @Override
            public View newView(Context context, Cursor cursor, ViewGroup parent) {
                // TODO Auto-generated method stub
                return super.newView(context, cursor, parent);
            }

            @Override
            public void bindView(View view, Context context, Cursor cursor) {
                // TODO Auto-generated method stub
                // override bindView needs bind control to value manually
                String note = cursor.getString(cursor.getColumnIndex(SearchHistoryAdapter.HISTORY_COLUMN_name));
                TextView text1 = (TextView) view.findViewById(R.id.history_item);
                // set text to "note" column
                text1.setText(note);

                // get row_id, to define which row to delete by using "getInt" method
                final int row_id = cursor.getInt(cursor.getColumnIndex("_id"));
                //define "delete" button and bind the button to layout file
                ImageButton button = (ImageButton) view.findViewById(R.id.delete_button);
                button.setBackgroundColor(Color.TRANSPARENT);
                //set onclickListener to button
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View arg0) {
                        // delete single rows by using row_id using "delete_log" method defined in dbHelper class
                        dbHelper.delete_log(row_id);
                        // notify dataAdapter to change the cursor.
                        dataAdapter.changeCursor(dbHelper.fetch_AllLog());
                    }
                });

            }

        };
        // set dataAdapter to mylistview
        mylistview.setAdapter(dataAdapter);
    }
    @Override
    public void onResume()
    {
        super.onResume();
        //use fetch_AllLog() method defined in LogDbAdapter class
        cursor = dbHelper.fetch_AllLog();
        // notyfy dataAdapter to change the cursor.
        dataAdapter.changeCursor(cursor);

    }


}
