package com.example.chaoliuscomputer.pocketguide;

import android.app.Activity;
import android.app.ListActivity;
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
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        String tag = "Events";
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);                 //*************
        Log.d(tag, "In the onCreate() event");

        Button button1 = (Button) findViewById(R.id.btnUpcoming);
        button1.setOnClickListener(button_listener1);

        Button button2 = (Button) findViewById(R.id.btnPrevious);
        button2.setOnClickListener(button_listener2);

        Button button3 = (Button) findViewById(R.id.btnChat);
        button3.setOnClickListener(button_listener3);

        Button button4 = (Button) findViewById(R.id.btnProfile);
        button4.setOnClickListener(button_listener4);
    }



    private Button.OnClickListener button_listener1 = new Button.OnClickListener() {
        public void onClick(View v) {
            Intent intent = new Intent();
            intent.setClass(MainActivity.this, activity_upcoming.class);
            startActivity(intent);
        }
    };


    private Button.OnClickListener button_listener2 = new Button.OnClickListener() {
        public void onClick(View v) {
            Intent intent = new Intent();
            intent.setClass(MainActivity.this, activity_previous.class);
            startActivity(intent);
        }
    };

    private Button.OnClickListener button_listener3 = new Button.OnClickListener() {
        public void onClick(View v) {
            Intent intent = new Intent();
            intent.setClass(MainActivity.this, ChatActivity.class);
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
