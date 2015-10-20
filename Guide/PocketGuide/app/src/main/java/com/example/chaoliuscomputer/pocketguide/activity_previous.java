package com.example.chaoliuscomputer.pocketguide;

/**
 * Created by ChaoLiu's Computer on 2015/10/18.
 */
import android.app.Activity;
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

public class activity_previous extends Activity{
    @Override
    public void onCreate(Bundle savedInstanceState){
        String tag = "Events";
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_previous);
        Log.d(tag, "In the onCreate() event");

        Button button1=(Button)findViewById(R.id.btnPending);
        button1.setOnClickListener(button_listener1);

        Button button2=(Button)findViewById(R.id.btnUpcoming);
        button2.setOnClickListener(button_listener2);

        Button button3=(Button)findViewById(R.id.btnChat);
        button3.setOnClickListener(button_listener3);

        Button button4=(Button)findViewById(R.id.btnProfile);
        button4.setOnClickListener(button_listener4);
    }

    private Button.OnClickListener button_listener1 = new Button.OnClickListener() {
        public void onClick(View v) {
            Intent intent = new Intent();
            intent.setClass(activity_previous.this, MainActivity.class);
            startActivity(intent);
        }
    };

    private Button.OnClickListener button_listener2 = new Button.OnClickListener() {
        public void onClick(View v) {
            Intent intent = new Intent();
            intent.setClass(activity_previous.this, activity_upcoming.class);
            startActivity(intent);
        }
    };

    private Button.OnClickListener button_listener3 = new Button.OnClickListener() {
        public void onClick(View v) {
            Intent intent = new Intent();
            intent.setClass(activity_previous.this, ChatActivity.class);
            startActivity(intent);
        }
    };

    private Button.OnClickListener button_listener4 = new Button.OnClickListener() {
        public void onClick(View v) {
            Intent intent = new Intent();
            intent.setClass(activity_previous.this, activity_profile.class);
            startActivity(intent);
        }
    };
}

