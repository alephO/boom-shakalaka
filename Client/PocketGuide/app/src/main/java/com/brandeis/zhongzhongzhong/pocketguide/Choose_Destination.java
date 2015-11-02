package com.brandeis.zhongzhongzhong.pocketguide;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;

import java.util.ArrayList;

public class Choose_Destination extends AppCompatActivity {

    /* location list should be retrieved from database, but now just use string stored in the resource file*/
    public String [] location;
    public static final String TAG = "create?";
    DBHelper mydb;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*hide title bar*/
        //this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_choose_destination);
        //location = getResources().getStringArray(R.array.location_list);
        //ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, location);
        mydb = new DBHelper(this);
        ArrayList array_list = mydb.getAllNations();
        //Log.d(TAG, array_list.get(1));
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, array_list);
        AutoCompleteTextView autoCompleteTextView = (AutoCompleteTextView) findViewById(R.id.Chooselocation_autoCompleteTextView);

        autoCompleteTextView.setThreshold(1);
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
                i.putExtra("location_name", location[position]);
                startActivity(i);
            }
        });

        // public void onListItemClick( )


    }



}
