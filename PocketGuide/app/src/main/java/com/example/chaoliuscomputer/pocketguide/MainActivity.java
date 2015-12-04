package com.example.chaoliuscomputer.pocketguide;

import android.app.Activity;
import android.app.ListActivity;
import android.database.Cursor;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.util.Log;
import android.view.DragEvent;
import android.content.Intent;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends Activity {

    private ArrayList<EventEntity> list = new ArrayList<EventEntity>();
    private ListView EventList;
    private EventDB eventDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        String tag = "Events";
        super.onCreate(savedInstanceState);
        setContentView(R.layout.service);                 //*************
        Log.d(tag, "In the onCreate() event");

        Button button3 = (Button) findViewById(R.id.btnChat);
        button3.setOnClickListener(button_listener3);

        Button button4 = (Button) findViewById(R.id.btnProfile);
        button4.setOnClickListener(button_listener4);

        EventList=(ListView)findViewById(R.id.EventList);

        eventDB = new EventDB(this);

        int RId = R.layout.event_item;
        EventEntity newEvent = new EventEntity("2015.3.2-2015.3.8","Boston",RId);
        list.add(newEvent);
        eventDB.insert(newEvent.getDate(), newEvent.getPlace());

        Cursor mCursor = eventDB.select();

        for(mCursor.moveToFirst();!mCursor.isAfterLast();mCursor.moveToNext())
        {
            String e_date = mCursor.getString(1);
            String e_place = mCursor.getString(2);
            RId = R.layout.event_item;
            EventEntity newItem= new EventEntity(e_date, e_place,RId);
            list.add(newItem);
            EventList.setAdapter(new EventViewAdapter(MainActivity.this, list));
        }
    }

    private Button.OnClickListener button_listener3 = new Button.OnClickListener() {
        public void onClick(View v) {
            Intent intent = new Intent();
            intent.setClass(MainActivity.this, ChatListActivity.class);
            startActivity(intent);
        }
    };

    private Button.OnClickListener button_listener4 = new Button.OnClickListener() {
        public void onClick(View v) {
            Intent intent = new Intent();
            intent.setClass(MainActivity.this, activity_profile.class);
            startActivity(intent);
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
